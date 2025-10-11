-- "stock schema"
-- -stockId: UUID (PK)
-- -store: UUID (FK - Store)
-- -stockName: String
-- -stockQuantity: Int
-- -stockDescription: String
-- -updatedDate: Date
-- -updatedBy: UUID

create extension if not exists "pgcrypto";

create table stock(
    stock_id uuid primary key default gen_random_uuid(),
    stock_name varchar(30) not null,
    stock_quantity int not null,
    stock_description varchar(300),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by uuid references users(user_id) on delete set null
);

-- Auto-update updated_at column
CREATE OR REPLACE FUNCTION update_stock_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_stock_timestamp
    BEFORE UPDATE ON stock
    FOR EACH ROW
EXECUTE FUNCTION update_stock_timestamp();