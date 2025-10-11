alter table reservation
    drop column if exists customer_id,
    add column if not exists guest_name varchar(100),
    add column if not exists guest_phone varchar(15);