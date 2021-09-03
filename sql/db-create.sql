CREATE DATABASE IF NOT EXISTS restaurant;

USE restaurant;

DROP TABLE IF EXISTS receipt_has_dish;
DROP TABLE IF EXISTS dish;
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

CREATE TABLE dish (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    category_id INT NOT NULL,
    price INT NOT NULL,
    weight INT NOT NULL,
    description VARCHAR(1023),
    
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

CREATE TABLE receipt_has_dish (
	receipt_id INT NOT NULL,
	dish_id INT NOT NULL,
    count INT NOT NULL DEFAULT 1,
    price INT NOT NULL,
    
    FOREIGN KEY (receipt_id) REFERENCES receipt(id),
    FOREIGN KEY (dish_id) REFERENCES dish(id)
);

-- =========INSERTING============= 
INSERT INTO role (name) VALUE ('client');
INSERT INTO role (name) VALUE ('manager');

INSERT INTO status (name) VALUE ('new');
INSERT INTO status (name) VALUE ('approved');
INSERT INTO status (name) VALUE ('cooking');
INSERT INTO status (name) VALUE ('delivering');
INSERT INTO status (name) VALUE ('received');

INSERT INTO category (name) VALUE ('Burgers');

INSERT INTO dish (name, category_id, price, weight, description) 
	VALUE ('Fish burger', 1, 229, 350, 'Delicate bun, Caesar sauce, grilled salmon fillet, fresh iceberg and lots of vegetables!');
INSERT INTO dish (name, category_id, price, weight, description) 
	VALUE ('Cheese burger', 1, 209, 500, '- Where\'s the roll? - And there is no roll, but there is mozzarella in crispy panko breadcrumbs, fresh vegetables and the juiciest cutlet.');
INSERT INTO dish (name, category_id, price, weight, description) 
	VALUE ('Black burger', 1, 189, 400, 'Brutal black bun, BBQ sauce, some more mustard brutality, grilled chopped steak, some onions and vegetables, Voila.');
INSERT INTO dish (name, category_id, price, weight, description) 
	VALUE ('Burger with Veal', 1, 228, 400, 'Delicate white bun, BBQ sauce, some Dijon mustard, grilled veal tenderloin, lots of vegetables: tomatoes, mars onions, iceberg lettuce, and where was the cheese? We\'ve added Cheddar! Are you in love already? The default roast is medium. You can change the roast when confirming the order, notifying the operator.');