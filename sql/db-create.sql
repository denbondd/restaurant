CREATE DATABASE IF NOT EXISTS restaurant;

USE restaurant;

DROP TABLE IF EXISTS receipt_has_product;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS receipt;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS category;


CREATE TABLE role (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE user (
	id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL DEFAULT 1,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE category(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE,
    parent_id INT,
    description VARCHAR(255),
    
    FOREIGN KEY (parent_id) REFERENCES category(id)
);

CREATE TABLE product (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE,
    category_id INT NOT NULL,
    price INT NOT NULL,
    
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE status (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE receipt (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    status_id INT NOT NULL DEFAULT 1,
    total INT,
    manager_id INT NOT NULL,
    
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (manager_id) REFERENCES user(id),
    FOREIGN KEY (status_id) REFERENCES status(id)
);

CREATE TABLE receipt_has_product (
	receipt_id INT NOT NULL,
	product_id INT NOT NULL,
    count INT NOT NULL DEFAULT 1,
    price INT NOT NULL,
    
    FOREIGN KEY (receipt_id) REFERENCES receipt(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- =========INSERTING============= 
INSERT INTO role (name) VALUE ('client');
INSERT INTO role (name) VALUE ('manager');

INSERT INTO status (name) VALUE ('new');
INSERT INTO status (name) VALUE ('approved');
INSERT INTO status (name) VALUE ('cooking');
INSERT INTO status (name) VALUE ('delivering');
INSERT INTO status (name) VALUE ('received');
