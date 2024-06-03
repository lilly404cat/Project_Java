
USE hospital_stocks;

DROP TABLE stocks;
DROP table consumption;
DROP table purchases;
DROP table departments;
DROP table medicines;
DROP TABLE users;
DROP table suppliers;

CREATE TABLE IF NOT EXISTS departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(50),
    password VARCHAR(255)
);

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
    department_id INT,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (medicine_id) REFERENCES medicines(id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS purchases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    medicine_id INT,
    supplier_id INT,
    quantity INT,
    purchase_date DATE,
    price DECIMAL(10, 2),
    FOREIGN KEY (medicine_id) REFERENCES medicines(id) ON DELETE CASCADE,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS consumption (
    id INT AUTO_INCREMENT PRIMARY KEY,
    medicine_id INT,
    quantity INT,
    consumption_date DATE,
    department_id INT,
    FOREIGN KEY (medicine_id) REFERENCES medicines(id) ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE
);

ALTER TABLE departments AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE medicines AUTO_INCREMENT = 1;
ALTER TABLE suppliers AUTO_INCREMENT = 1;
ALTER TABLE stocks AUTO_INCREMENT = 1;
ALTER TABLE purchases AUTO_INCREMENT = 1;
ALTER TABLE consumption AUTO_INCREMENT = 1;

INSERT INTO medicines (name, description, unit, price_per_unit) VALUES
    ('Paracetamol', 'Pain reliever and a fever reducer', 'Tablet', 0.10),
    ('Ibuprofen', 'Nonsteroidal anti-inflammatory drug', 'Tablet', 0.20),
    ('Amoxicillin', 'Antibiotic', 'Capsule', 0.50),
    ('Aspirin', 'Pain reliever, fever reducer, and anti-inflammatory', 'Tablet', 0.15);

INSERT INTO departments (name) VALUES
    ('Emergency'),
    ('Pediatrics'),
    ('Surgery'),
    ('General');

INSERT INTO suppliers (name, contact_info) VALUES
    ('Supplier A', '1234 Main St, City, Country, 12345, +1234567890'),
    ('Supplier B', '5678 First Ave, City, Country, 67890, +0987654321');


INSERT INTO stocks (medicine_id, quantity, department_id, last_updated) VALUES 
    (1, 100, 1, NOW()), 
    (2, 150, 2, NOW()), 
    (3, 200, 3, NOW());

INSERT INTO purchases (medicine_id, supplier_id, quantity, purchase_date, price) VALUES
    (1, 1, 1000, '2024-01-01', 100.00),
    (2, 2, 500, '2024-02-15', 100.00),
    (3, 1, 200, '2024-03-10', 100.00),
    (4, 2, 800, '2024-04-20', 120.00);

INSERT INTO consumption (medicine_id, quantity, consumption_date, department_id) VALUES
    (1, 100, '2024-05-01', 1),
    (2, 50, '2024-05-02', 2),
    (3, 20, '2024-05-03', 3),
    (4, 80, '2024-05-04', 4);
