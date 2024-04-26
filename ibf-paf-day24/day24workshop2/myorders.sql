DROP DATABASE IF EXISTS myorders;

CREATE DATABASE myorders;

USE myorders;

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT,
    order_date DATE NOT NULL,
    customer_name VARCHAR(128) NOT NULL,
    ship_address VARCHAR(128) NOT NULL,
    notes TEXT,
    tax DECIMAL(6, 2) DEFAULT 0.05,
    PRIMARY KEY (order_id)
);

CREATE TABLE order_details (
    id INT AUTO_INCREMENT,
    product VARCHAR(64) NOT NULL,
    unit_price DECIMAL(6,2) NOT NULL, 
    discount DECIMAL(4,2) DEFAULT 1.0, 
    quantity INT NOT NULL,
    order_id INT NOT NULL, 
    PRIMARY KEY (id),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

grant all privileges on myorders.* to 'abcde'@'%';
flush privileges;