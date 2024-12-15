DROP DATABASE IF EXISTS wanna_shop;
CREATE DATABASE IF NOT EXISTS wanna_shop;

USE wanna_shop;

DROP TABLE IF EXISTS users;
CREATE TABLE users(
      username varchar(50) NOT NULL,
      password varchar(60) NOT NULL,
      firstName varchar(50),
      lastName varchar(50),
      isAdmin boolean NOT NULL DEFAULT false,
      email varchar(100) UNIQUE NOT NULL,
      PRIMARY KEY(username)
);

/*Table structure for table products */
DROP TABLE IF EXISTS products;
CREATE TABLE products (
      productCode varchar(15) NOT NULL,
      productName varchar(70) NOT NULL,
      productDescription text NOT NULL,
      quantityInStock smallint(6) NOT NULL,
      costPrice double NOT NULL,
      retailPrice double NOT NULL,
      PRIMARY KEY  (productCode)
);

/*Table structure for table orders */
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    orderNumber int(11) AUTO_INCREMENT NOT NULL,
    orderDate datetime NOT NULL,
    username varchar(50) NOT NULL,
    PRIMARY KEY  (orderNumber),
    FOREIGN KEY (username) REFERENCES users(username)
);

/*Table structure for table orderlines */
DROP TABLE IF EXISTS orderlines;
CREATE TABLE orderlines (
      orderNumber int(11) NOT NULL,
      productCode varchar(15) NOT NULL,
      quantityOrdered int(11) NOT NULL,
      priceEach double NOT NULL,
      orderLineNumber smallint(6) NOT NULL,
      PRIMARY KEY  (orderNumber,productCode),
      FOREIGN KEY (orderNumber) REFERENCES orders(orderNumber),
      FOREIGN KEY (productCode) REFERENCES products(productCode)
);