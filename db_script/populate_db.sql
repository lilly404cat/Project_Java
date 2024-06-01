USE hospital_stocks;

INSERT INTO medicines (name, description, unit, price_per_unit) VALUES
                                                                    ('Paracetamol', 'Pain reliever and a fever reducer', 'Tablet', 0.10),
                                                                    ('Ibuprofen', 'Nonsteroidal anti-inflammatory drug', 'Tablet', 0.20),
                                                                    ('Amoxicillin', 'Antibiotic', 'Capsule', 0.50),
                                                                    ('Aspirin', 'Pain reliever, fever reducer, and anti-inflammatory', 'Tablet', 0.15);

INSERT INTO suppliers (name, contact_info) VALUES
                                               ('Supplier A', '1234 Main St, City, Country, 12345, +1234567890'),
                                               ('Supplier B', '5678 First Ave, City, Country, 67890, +0987654321');

INSERT INTO stocks (medicine_id, quantity) VALUES
                                               (1, 1000),
                                               (2, 500),
                                               (3, 200),
                                               (4, 800);

INSERT INTO purchases (medicine_id, supplier_id, quantity, purchase_date, price) VALUES
                                                                                     (1, 1, 1000, '2024-01-01', 100.00),
                                                                                     (2, 2, 500, '2024-02-15', 100.00),
                                                                                     (3, 1, 200, '2024-03-10', 100.00),
                                                                                     (4, 2, 800, '2024-04-20', 120.00);

INSERT INTO departments (name) VALUES
                                   ('Emergency'),
                                   ('Pediatrics'),
                                   ('Surgery'),
                                   ('General');

INSERT INTO consumption (medicine_id, quantity, consumption_date, department_id) VALUES
                                                                                     (1, 100, '2024-05-01', 1),
                                                                                     (2, 50, '2024-05-02', 2),
                                                                                     (3, 20, '2024-05-03', 3),
                                                                                     (4, 80, '2024-05-04', 4);
