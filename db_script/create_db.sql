CREATE DATABASE hospital_stocks;

USE hospital_stocks;

-- DROP TABLE medicines;
-- DROP TABLE suppliers;
-- DROP TABLE stocks;
-- DROP TABLE purchases;
-- DROP TABLE consumption;
-- DROP TABLE departments;

CREATE TABLE IF NOT EXISTS medicines (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL,
    description TEXT,
    unit VARCHAR(50),
    price_per_unit DECIMAL(10, 2)
    );

CREATE TABLE IF NOT EXISTS suppliers (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL,
    contact_info TEXT
    );

CREATE TABLE IF NOT EXISTS stocks (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      medicine_id INT,
                                      quantity INT,
                                      last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      FOREIGN KEY (medicine_id) REFERENCES medicines(id)
    );

CREATE TABLE IF NOT EXISTS purchases (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         medicine_id INT,
                                         supplier_id INT,
                                         quantity INT,
                                         purchase_date DATE,
                                         price DECIMAL(10, 2),
    FOREIGN KEY (medicine_id) REFERENCES medicines(id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
    );

CREATE TABLE IF NOT EXISTS departments (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS consumption (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           medicine_id INT,
                                           quantity INT,
                                           consumption_date DATE,
                                           department_id INT,
                                           FOREIGN KEY (medicine_id) REFERENCES medicines(id),
    FOREIGN KEY (department_id) REFERENCES departments(id)
    );


