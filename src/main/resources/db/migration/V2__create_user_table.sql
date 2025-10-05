CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- TABLE 'user' schema
-- -email: String
-- -password: String
-- -userId: UUID (PK)
-- -userName: String
-- -userAge: Int
-- -userAddress: UUID (FK - Address)
-- -createdAt: Date
-- -updatedAt: Date
-- -role: Roles
-- -authProvider: Provider

CREATE TABLE users(
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email varchar(100) UNIQUE NOT NULL,
    password varchar(32) NOT NULL,
    user_name varchar(50),
    user_age Int check (user_age >= 0),
    user_address UUID references address(address_id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role varchar(20) NOT NULL DEFAULT 'STAFF',
    auth_provider varchar(20) NOT NULL DEFAULT 'LOCAL'
);

-- Auto-update updated_at column
CREATE OR REPLACE FUNCTION update_user_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_user_timestamp
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION update_user_timestamp();
