CREATE DATABASE IF NOT EXISTS restaurant;

USE restaurant;

DROP TABLE IF EXISTS receipt_has_dish;
DROP TABLE IF EXISTS cart_has_dish;
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
    
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE category(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE dish (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    category_id INT NOT NULL,
    price INT NOT NULL,
    weight INT NOT NULL,
    description VARCHAR(1023),
    
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE
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
    manager_id INT,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (manager_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (status_id) REFERENCES status(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE receipt_has_dish (
	receipt_id INT NOT NULL,
	dish_id INT NOT NULL,
    count INT NOT NULL DEFAULT 1,
    price INT,
    
    UNIQUE KEY (receipt_id, dish_id),
    FOREIGN KEY (receipt_id) REFERENCES receipt(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (dish_id) REFERENCES dish(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE cart_has_dish (
	user_id INT NOT NULL,
    dish_id INT NOT NULL UNIQUE,
    count INT NOT NULL DEFAULT 1,
    
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (dish_id) REFERENCES dish(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- ==========TRIGGERS==============
DROP TRIGGER IF EXISTS receipt_has_dish_set_price;

DELIMITER //
CREATE DEFINER = CURRENT_USER TRIGGER restaurant.receipt_has_dish_set_price BEFORE INSERT ON receipt_has_dish FOR EACH ROW
BEGIN
    SET NEW.price = (SELECT price FROM dish WHERE dish.id = NEW.dish_id);
    UPDATE receipt
    SET total = NEW.price * NEW.count + ifnull(total, 0) 
	WHERE id = NEW.receipt_id;
END//
DELIMITER ;

-- ==========INSERTING============= 
INSERT INTO role (name) VALUE ('client');
INSERT INTO role (name) VALUE ('manager');

-- login: admin pass: admin role: admin
INSERT INTO user (login, password, role_id) VALUE ('admin', 'C7AD44CBAD762A5DA0A452F9E854FDC1E0E7A52A38015F23F3EAB1D80B931DD472634DFAC71CD34EBC35D16AB7FB8A90C81F975113D6C7538DC69DD8DE9077EC', 2);

INSERT INTO status (name) VALUE ('new');
INSERT INTO status (name) VALUE ('approved');
INSERT INTO status (name) VALUE ('cooking');
INSERT INTO status (name) VALUE ('delivering');
INSERT INTO status (name) VALUE ('received');

INSERT INTO category (name) VALUE ('Burgers');
INSERT INTO category (name) VALUE ('Soups');
INSERT INTO category (name) VALUE ('Salads');
INSERT INTO category (name) VALUE ('Desserts');
INSERT INTO category (name) VALUE ('Meat');

INSERT INTO dish (name, category_id, price, weight, description) 
	VALUE ('Fish burger', 1, 229, 350, 'Delicate bun, Caesar sauce, grilled salmon fillet, fresh iceberg and lots of vegetables!');
INSERT INTO dish (name, category_id, price, weight, description) 
	VALUE ('Cheese burger', 1, 209, 500, '- Where\'s the roll? - And there is no roll, but there is mozzarella in crispy panko breadcrumbs, fresh vegetables and the juiciest cutlet.');
INSERT INTO dish (name, category_id, price, weight, description) 
	VALUE ('Black burger', 1, 189, 400, 'Brutal black bun, BBQ sauce, some more mustard brutality, grilled chopped steak, some onions and vegetables, Voila.');
INSERT INTO dish (name, category_id, price, weight, description) 
	VALUE ('Burger with Veal', 1, 228, 400, 'Delicate white bun, BBQ sauce, some Dijon mustard, grilled veal tenderloin, lots of vegetables: tomatoes, mars onions, iceberg lettuce, and where was the cheese? We\'ve added Cheddar! Are you in love already? The default roast is medium. You can change the roast when confirming the order, notifying the operator.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Borsch', 2, 109, 400, 'Real Ukrainian Borsch with juicy rib and sour cream.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Okroshka', 2, 89, 400, 'Traditional okroshka cooked with love of cooks.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Solyanka', 2, 119, 400, 'Branded solyanka with lemon wedge.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Caesar with chicken', 3, 179, 300, 'Grilled chicken fillet with classic Caesar and iceberg sauce, crispy bacon and aged Parmesan.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Salmon salad', 3, 199, 300, 'Mix of lettuce, slices of the finest salmon, feta cheese, cherry tomatoes and olive dressing.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Salad with shrimp', 3, 219, 300, 'Lettuce mix, juicy tiger shrimp, cherry tomatoes, avocado, pine nuts, orange sauce.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Greek salad', 3, 110, 300, 'Tomato, cucumber, sweet pepper, olives, olives, red onion, mixed salad, feta or tofu (your choice). Dressed with olive oil and Italian herbs.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Caramel donut', 4, 43, 100, 'Donut with salted caramel and nuts.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Churros', 4, 99, 170, 'Mexican donuts served with chocolate.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Steak Premium', 5, 1600, 40, 'Choice Premium Boneless Steak (USA) is a type of steak made from the carcass of selected young Angus bulls. Prime marbling.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Dry-aged rib eye steak', 5, 499, 280, 'Dry aging of meat is the latest culinary trend, which we want to pleasantly surprise our beloved guests with. The advantage of this process is the production of very tender meat with a pronounced aroma.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Caret of a young calf', 5, 499, 300, 'The tenderloin of a dairy calf is tender meat on short rib bones.');
INSERT INTO dish (name, category_id, price, weight, description)
	VALUE ('Fried shrimp in garlic oil', 5, 209, 200, 'Fried shrimp in garlic oil');

