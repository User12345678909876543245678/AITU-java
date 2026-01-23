CREATE TABLE clothing_item (
    item_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    size VARCHAR(10) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    brand VARCHAR(100) NOT NULL
);

