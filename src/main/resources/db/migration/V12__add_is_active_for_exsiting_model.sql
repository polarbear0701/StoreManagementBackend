alter table users
add is_active BOOLEAN DEFAULT TRUE;

alter table store
add is_active BOOLEAN DEFAULT TRUE;

alter table address
add is_active BOOLEAN DEFAULT TRUE;

alter table store_info
add is_active BOOLEAN DEFAULT TRUE;