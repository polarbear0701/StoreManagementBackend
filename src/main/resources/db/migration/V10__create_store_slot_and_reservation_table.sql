CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Slots are per day with start/end time and a simple capacity
CREATE TABLE store_slot (
    store_slot_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    store_id      UUID NOT NULL REFERENCES store(store_id) ON DELETE CASCADE,
    slot_date     DATE NOT NULL,
    start_time    TIME NOT NULL,
    end_time      TIME NOT NULL,
    capacity      INT  NOT NULL CHECK (capacity > 0),
    -- One slot per store per start time on a given day
    UNIQUE (store_id, slot_date, start_time),
    CHECK (start_time < end_time)
);

CREATE INDEX IF NOT EXISTS idx_store_slot_store ON store_slot(store_id);
CREATE INDEX IF NOT EXISTS idx_store_slot_date ON store_slot(slot_date);

-- Reservations point to a slot and optionally to a customer (users)
CREATE TABLE reservation (
    reservation_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    store_id       UUID NOT NULL REFERENCES store(store_id) ON DELETE CASCADE,
    store_slot_id  UUID NOT NULL REFERENCES store_slot(store_slot_id) ON DELETE CASCADE,
    customer_id    UUID REFERENCES users(user_id) ON DELETE SET NULL,
    status         VARCHAR(20) NOT NULL DEFAULT 'BOOKED' CHECK (status IN ('BOOKED','CANCELLED')),
    party_size     INT NOT NULL DEFAULT 1 CHECK (party_size > 0),
    notes          VARCHAR(300),
    booked_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_reservation_slot ON reservation(store_slot_id);
CREATE INDEX IF NOT EXISTS idx_reservation_store ON reservation(store_id);

-- Auto-update updated_at column
CREATE OR REPLACE FUNCTION update_reservation_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_reservation_timestamp
BEFORE UPDATE ON reservation
FOR EACH ROW
EXECUTE FUNCTION update_reservation_timestamp();

-- Simple overbooking prevention:
-- Ensures reservation.store_id matches slot.store_id
-- Ensures sum(party_size) of BOOKED reservations for a slot does not exceed slot.capacity
CREATE OR REPLACE FUNCTION prevent_overbooking_and_mismatch()
RETURNS TRIGGER AS $$
DECLARE
    v_slot_store_id UUID;
    v_capacity INT;
    v_booked INT;
BEGIN
    -- Verify slot belongs to the same store
    SELECT store_id, capacity INTO v_slot_store_id, v_capacity
    FROM store_slot
    WHERE store_slot_id = NEW.store_slot_id;

    IF v_slot_store_id IS NULL THEN
        RAISE EXCEPTION 'Store slot % not found', NEW.store_slot_id;
    END IF;

    IF NEW.store_id <> v_slot_store_id THEN
        RAISE EXCEPTION 'Reservation store_id % does not match slot''s store_id %', NEW.store_id, v_slot_store_id;
    END IF;

    -- Only enforce when status is BOOKED
    IF NEW.status = 'BOOKED' THEN
        SELECT COALESCE(SUM(party_size), 0) INTO v_booked
        FROM reservation
        WHERE store_slot_id = NEW.store_slot_id
          AND status = 'BOOKED'
          AND reservation_id <> COALESCE(NEW.reservation_id, '00000000-0000-0000-0000-000000000000'::uuid);

        IF v_booked + COALESCE(NEW.party_size, 1) > v_capacity THEN
            RAISE EXCEPTION 'Slot is full: capacity %, already booked % (+ new %)', v_capacity, v_booked, COALESCE(NEW.party_size, 1);
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_prevent_overbooking_and_mismatch
BEFORE INSERT OR UPDATE ON reservation
FOR EACH ROW
EXECUTE FUNCTION prevent_overbooking_and_mismatch();
