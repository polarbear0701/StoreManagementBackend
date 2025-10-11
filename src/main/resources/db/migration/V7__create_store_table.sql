CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE store (
    store_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    store_owner UUID NOT NULL REFERENCES users(user_id) ON DELETE RESTRICT,
    store_info_id UUID UNIQUE REFERENCES store_info(store_info_id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Auto-update updated_at column
CREATE OR REPLACE FUNCTION update_store_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_store_timestamp
BEFORE UPDATE ON store
FOR EACH ROW
EXECUTE FUNCTION update_store_timestamp();

-- Helpful indexes
CREATE INDEX IF NOT EXISTS idx_store_owner ON store(store_owner);
CREATE INDEX IF NOT EXISTS idx_store_info_id ON store(store_info_id);


alter table stock
    add column store_id UUID REFERENCES store(store_id) on delete cascade;