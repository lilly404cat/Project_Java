USE hospital_stocks;

select *  from medicines;
DROP TABLE stocks;
DROP table consumption;
DROP table purchases;
DROP table departments;
DROP table medicines;
DROP TABLE users;
DROP table suppliers;

CREATE TABLE IF NOT EXISTS departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    UNIQUE(name)
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
    price_per_unit DECIMAL(10, 2),
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_info TEXT,
    UNIQUE(name)
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

INSERT INTO departments (name) VALUES
    ('Gynecology'),
    ('Orthopedics'),
    ('Ophthalmology'),
    ('Urology');

INSERT INTO medicines (name, description, unit, price_per_unit) VALUES
    ('Atorvastatin', 'Reduces levels of "bad" cholesterol and triglycerides in the blood.', 'Tablet', 0.20),
    ('Ramipril', 'Treats high blood pressure and heart failure.', 'Tablet', 0.15),
    ('Lisinopril', 'Treats high blood pressure and heart failure.', 'Tablet', 0.15),
    ('Levothyroxine', 'Replaces or provides more thyroid hormone, which is normally produced by the thyroid gland.', 'Tablet', 0.30),
    ('Metformin', 'Treats type 2 diabetes.', 'Tablet', 0.20),
    ('Ezetimibe', 'Lowers blood cholesterol for patients who are at risk of heart disease or a stroke.', 'Tablet', 0.35),
    ('Warfarin', 'Prevents blood clots from forming or growing larger in your blood vessels or heart.', 'Tablet', 0.40),
    ('Furosemide', 'Treats fluid retention (edema) in people with congestive heart failure, liver disease, or a kidney disorder.', 'Tablet', 0.25);

INSERT INTO suppliers (name, contact_info) VALUES
    ('Roche', '1234 Main St, City, Country, 12345, +1234567890'),
    ('Sanofi', '5678 First Ave, City, Country, 67890, +0987654321');

INSERT INTO stocks (medicine_id, quantity, department_id) VALUES 
    (6, 150, 1), 
    (7, 200, 2), 
    (8, 100, 3),
    (5, 250, 4),
    (4, 300, 1),
    (3, 200, 2),
    (2, 150, 3),
    (1, 100, 4);

INSERT INTO purchases (medicine_id, supplier_id, quantity, purchase_date, price) VALUES
    (1, 1, 200, '2024-06-01', 50.00),
    (2, 2, 300, '2024-06-01', 40.00),
    (3, 1, 150, '2024-06-02', 60.00),
    (4, 2, 250, '2024-06-02', 30.00),
    (5, 1, 400, '2024-05-31', 25.00),
    (6, 2, 200, '2024-05-31', 20.00),
    (7, 1, 250, '2024-05-30', 35.00),
    (8, 2, 150, '2024-05-28', 45.00);

INSERT INTO consumption (medicine_id, quantity, consumption_date, department_id) VALUES
    (2, 100, '2024-06-01', 1),
    (1, 50, '2024-06-02', 2),
    (4, 30, '2024-06-01', 3),
    (3, 80, '2024-06-02', 4),
    (6, 150, '2024-06-01', 1),
    (5, 100, '2024-05-31', 2),
    (2, 70, '2024-05-30', 3),
    (4, 100, '2024-05-31', 2),
    (8, 70, '2024-05-30', 3),
    (7, 50, '2024-05-29', 4);
