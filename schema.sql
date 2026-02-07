CREATE TABLE customer (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    preferred_size VARCHAR(10) NOT NULL,
    points INTEGER DEFAULT 0,
    customer_type VARCHAR(20) NOT NULL, -- 'Regular' or 'VIP'
    join_date VARCHAR(20),              -- For RegularCustomer
    vip_level VARCHAR(20)               -- For VIPCustomer
);