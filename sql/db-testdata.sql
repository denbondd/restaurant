USE restaurant;

INSERT INTO category (name) VALUES ('tests');

INSERT INTO product (name, category_id, price) VALUES ('android', 1, 100);
INSERT INTO product (name, category_id, price) VALUES ('ps5', 1, 500);

SELECT * FROM product