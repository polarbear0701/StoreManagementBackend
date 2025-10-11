ALTER TABLE address
    ALTER COLUMN address_number TYPE varchar(30) USING address_number::text;
