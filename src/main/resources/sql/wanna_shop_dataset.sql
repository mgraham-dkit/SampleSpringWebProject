USE wanna_shop;

INSERT INTO users (username, password, firstName, lastName, isAdmin, email)
VALUES ('MGraham', 'PassWord1', 'Michelle', 'Graham', 0, 'mgraham@dkit.ie'),
       ('Michelle', 'password', 'Michelle', 'Graham', 0, 'michelle@dkit.ie');

INSERT INTO products (productCode, productName, productDescription, quantityInStock, costPrice, retailPrice)
VALUES ('B1212', 'Chocolate', 'Delicious substance.', 1000, 0.5, 2.95),
       ('B8129', 'Cheerios', 'Crunchy hoop-like cereal.', 100, 1.9, 6.3);
