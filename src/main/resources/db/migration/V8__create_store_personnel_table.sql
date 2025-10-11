CREATE TABLE store_admins (
    store_id UUID NOT NULL REFERENCES store(store_id) ON DELETE CASCADE,
    user_id  UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    PRIMARY KEY (store_id, user_id)
);

CREATE TABLE store_staff (
    store_id UUID NOT NULL REFERENCES store(store_id) ON DELETE CASCADE,
    user_id  UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    PRIMARY KEY (store_id, user_id)
);

-- Helpful indexes
CREATE INDEX IF NOT EXISTS idx_store_admins_user ON store_admins(user_id);
CREATE INDEX IF NOT EXISTS idx_store_staff_user ON store_staff(user_id);
