alter table storeinfo rename to store_info;

alter table store_info
    add column store_info_id UUID primary key default gen_random_uuid();
    
