CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- TABLE 'address' schema
-- -addressId: UUID (PK)
-- -number: String
-- -ward: String
-- -district: String
-- -city: String
-- -country: String

CREATE TABLE address(
    address_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    address_number int,
    address_ward varchar(50),
    address_district varchar(50),
    address_city varchar(50),
    address_country varchar(50)
);