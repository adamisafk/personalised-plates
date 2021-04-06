CREATE TABLE customers (
    id INT AUTO_INCREMENT
    ,first_name varchar(255) NOT NULL
    ,last_name varchar(255) NOT NULL
    ,email varchar(255) NOT NULL
    ,password varchar(255) NOT NULL
    ,PRIMARY KEY (id)
);

CREATE TABLE plates (
    id INT AUTO_INCREMENT
    ,reg varchar(8) NOT NULL
    ,allocated BOOLEAN default(false) NOT NULL
    ,price DECIMAL(13,2) NOT NULL
    ,style INT default(1) NOT NULL
    ,PRIMARY KEY (id)
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT
    ,customer_id INT NOT NULL
    ,plate_id INT NOT NULL
    ,order_date DATETIME(6) NOT NULL
    ,status INT default(0) NOT NULL
    ,PRIMARY KEY (id)
    ,FOREIGN KEY(customer_id) REFERENCES customers(id)
    ,FOREIGN KEY(plate_id) REFERENCES plates(id)
);

