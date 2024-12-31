USE wanna_shop;

INSERT INTO users (username, password, firstName, lastName, isAdmin, email)
VALUES ('MGraham', 'PassWord1', 'Michelle', 'Graham', 0, 'mgraham@dkit.ie'),
       ('Michelle', 'password', 'Michelle', 'Graham', 0, 'michelle@dkit.ie'),
       ('Helen', 'password', 'Michelle', 'Graham', 0, 'helen@graham.com'),
       ('Helen2', 'password', 'Helen', 'Michaels', 0, 'helen.michaels@dkit.ie');

INSERT INTO products (productCode, productName, productDescription, quantityInStock, costPrice, retailPrice)
VALUES ('B1212', 'Chocolate', 'Delicious substance.', 1000, 0.5, 2.95),
       ('B8129', 'Cheerios', 'Crunchy hoop-like cereal.', 100, 1.9, 6.3),
       ('B1423', 'Self raising flour', 'Flour with baking powder and a bit of salt already added to facilitate lift.', '15', '1.80', '3.99'),
       ('B3214', 'Strawberry jam', 'A sweet spread made primarily from crushed strawberries and sugar. It is known for its vibrant red colour, fruity flavour, and slightly chunky texture.', '8', '1.19', '2.45');

INSERT INTO `orders` (`orderDate`, `username`)
VALUES ('2024-12-30 16:54:25.000000', 'Helen'),
       ('2024-12-23 15:54:25', 'Michelle'),
       ('2024-11-23 15:54:25', 'Michelle'),
       ('2024-10-23 15:54:25', 'Michelle');

INSERT INTO `orderlines` (`orderNumber`, `productCode`, `quantityOrdered`, `priceEach`, `orderLineNumber`)
VALUES ('1', 'B1212', '2', '2.95', '1'),
       ('1', 'B1423', '1', '3.99', '2'),
       ('2', 'B1212', '3', '2.95', '1'),
       ('2', 'B8129', '5', '6.30', '2'),
       ('3', 'B1212', '3', '2.95', '1'),
       ('4', 'B8129', '3', '6.30', '1');