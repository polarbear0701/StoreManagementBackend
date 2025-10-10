-- "storeInfo" schema
-- -storeName: String
-- -storeAddress: UUID (FK - Address)
-- -storeDescription: String

create table storeInfo(
    store_name varchar(50) not null,
    store_address uuid references address(address_id) on delete set null,
    store_description varchar(300)
);