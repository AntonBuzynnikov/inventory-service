CREATE TABLE IF NOT EXISTS ingredient (
    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    waste_percentage DECIMAL(5,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS inventory_log(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ingredient_id SMALLINT NOT NULL,
    quantity DECIMAL(5,2),
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS receipt_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    invoice_number VARCHAR(255) NOT NULL,
    ingredient_id SMALLINT NOT NULL,
    amount DECIMAL(7,3),
    date TIMESTAMP
);