-- PASUL 1: STERGE DATELE VECHI IN ORDINEA CORECTA
DELETE
FROM client;
DELETE
FROM mechanic;
DELETE
FROM maintenance_log;
DELETE
FROM fuel_log;
DELETE
FROM repair_request_spare_part;
DELETE
FROM repair_request;
DELETE
FROM spare_part;
DELETE
FROM vehicle;
DELETE
FROM garage;

-- PASUL 2: INSERAM DATELE NOI

-- Inseram 5 Garaje
INSERT INTO garage (id, location, capacity, occupied_spaces)
VALUES (1, 'Garaj Titan', 50, 0);
INSERT INTO garage (id, location, capacity, occupied_spaces)
VALUES (2, 'Service Dristor', 30, 0);
INSERT INTO garage (id, location, capacity, occupied_spaces)
VALUES (3, 'Auto Vitantesti', 100, 0);
INSERT INTO garage (id, location, capacity, occupied_spaces)
VALUES (4, 'Mecanica Fina Rahova', 20, 0);
INSERT INTO garage (id, location, capacity, occupied_spaces)
VALUES (5, 'Parcare Centrala', 200, 0);

-- Inseram 5 Vehicule
INSERT INTO vehicle (id, brand, model, production_year, type, capacity, status, garage_id)
VALUES (1, 'Dacia', 'Duster', 2022, 'SUV', 5, 'Available', 1);
INSERT INTO vehicle (id, brand, model, production_year, type, capacity, status, garage_id)
VALUES (2, 'BMW', 'Seria 3', 2021, 'Sedan', 5, 'In Use', 2);
INSERT INTO vehicle (id, brand, model, production_year, type, capacity, status, garage_id)
VALUES (3, 'Audi', 'A4', 2023, 'Sedan', 5, 'Service', 3);
INSERT INTO vehicle (id, brand, model, production_year, type, capacity, status, garage_id)
VALUES (4, 'Volkswagen', 'Golf', 2020, 'Hatchback', 5, 'Available', 4);
INSERT INTO vehicle (id, brand, model, production_year, type, capacity, status, garage_id)
VALUES (5, 'Skoda', 'Octavia', 2022, 'Combi', 5, 'Blocked', 5);

-- Inseram 5 Mecanici
INSERT INTO mechanic (id, name, specialization, garage_id)
VALUES (1, 'Liviu Albei', 'Motor', 1);
INSERT INTO mechanic (id, name, specialization, garage_id)
VALUES (2, 'Radu Codreanu', 'Electrica', 2);
INSERT INTO mechanic (id, name, specialization, garage_id)
VALUES (3, 'Cristian Georgescu', 'Tinichigerie', 3);
INSERT INTO mechanic (id, name, specialization, garage_id)
VALUES (4, 'Vasile Dumitru', 'Diagnoza', 4);
INSERT INTO mechanic (id, name, specialization, garage_id)
VALUES (5, 'Ionut Tudor', 'Transmisie', 5);

-- Inseram Clienti
INSERT INTO client (id, name, age, location, vehicle_id)
VALUES (1, 'Liviu Andrei', 28, 'Bucuresti', 2);
INSERT INTO client (id, name, age, location, vehicle_id)
VALUES (2, 'Radu Stefan', 35, 'Cluj', 1);

-- Inseram Piese de Schimb
INSERT INTO spare_part (id, name, type, price, stock_quantity)
VALUES (1, 'Placute Frana Fata ATE', 'Frane', 350.0, 50);
INSERT INTO spare_part (id, name, type, price, stock_quantity)
VALUES (2, 'Filtru Ulei Mann', 'Motor', 65.5, 120);
INSERT INTO spare_part (id, name, type, price, stock_quantity)
VALUES (3, 'Bec H7 Philips', 'Electrice', 40.0, 200);
INSERT INTO spare_part (id, name, type, price, stock_quantity)
VALUES (4, 'Amortizor Fata Sachs', 'Suspensie', 450.0, 30);
INSERT INTO spare_part (id, name, type, price, stock_quantity)
VALUES (5, 'Kit Distributie Conti', 'Motor', 850.0, 15);

-- Inseram Cereri de Reparatie
INSERT INTO repair_request (id, vehicle_id, issue_description, request_date, status, total_cost)
VALUES (1, 3, 'Revizie completa si schimb filtre', '2025-06-10', 'Completed', 750.0);
INSERT INTO repair_request_spare_part (repair_request_id, spare_part_id)
VALUES (1, 2);

-- Inseram Maintenance Log
INSERT INTO maintenance_log (id, vehicle_id, date, description, cost, next_scheduled_date)
VALUES (1, 1, '2025-01-10', 'Schimb ulei si filtru aer', 450.0, '2026-01-10');
INSERT INTO maintenance_log (id, vehicle_id, date, description, cost, next_scheduled_date)
VALUES (2, 2, '2025-02-15', 'Inlocuire placute frana spate', 550.0, null);
INSERT INTO maintenance_log (id, vehicle_id, date, description, cost, next_scheduled_date)
VALUES (3, 3, '2025-03-20', 'Verificare sistem de climatizare', 150.0, null);
INSERT INTO maintenance_log (id, vehicle_id, date, description, cost, next_scheduled_date)
VALUES (4, 4, '2025-04-01', 'Geometrie roti', 200.0, '2025-12-01');
INSERT INTO maintenance_log (id, vehicle_id, date, description, cost, next_scheduled_date)
VALUES (5, 1, '2025-05-05', 'Schimb anvelope iarna-vara', 250.0, null);

-- Inseram Fuel Log
INSERT INTO fuel_log (id, vehicle_id, date, fuel_added_liters, cost_per_liter, total_cost)
VALUES (1, 1, '2025-06-01', 50.5, 7.50, 378.75);
INSERT INTO fuel_log (id, vehicle_id, date, fuel_added_liters, cost_per_liter, total_cost)
VALUES (2, 2, '2025-06-03', 60.0, 8.20, 492.00);
INSERT INTO fuel_log (id, vehicle_id, date, fuel_added_liters, cost_per_liter, total_cost)
VALUES (3, 3, '2025-06-05', 55.2, 8.10, 447.12);
INSERT INTO fuel_log (id, vehicle_id, date, fuel_added_liters, cost_per_liter, total_cost)
VALUES (4, 4, '2025-06-08', 45.0, 7.45, 335.25);
INSERT INTO fuel_log (id, vehicle_id, date, fuel_added_liters, cost_per_liter, total_cost)
VALUES (5, 5, '2025-06-12', 65.0, 7.85, 510.25);

-- PASUL 3: RESETAM TOATE SECVENTELE
ALTER SEQUENCE garage_id_seq RESTART WITH 100;
ALTER SEQUENCE vehicle_id_seq RESTART WITH 100;
ALTER SEQUENCE mechanic_id_seq RESTART WITH 100;
ALTER SEQUENCE client_id_seq RESTART WITH 100;
ALTER SEQUENCE spare_part_id_seq RESTART WITH 100;
ALTER SEQUENCE repair_request_id_seq RESTART WITH 100;
ALTER SEQUENCE maintenance_log_id_seq RESTART WITH 100;
ALTER SEQUENCE fuel_log_id_seq RESTART WITH 100;