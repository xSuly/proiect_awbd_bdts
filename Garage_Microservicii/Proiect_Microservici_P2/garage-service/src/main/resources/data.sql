--DELETE FROM mechanic;
--DELETE FROM garage;

INSERT INTO garage (id, location, capacity, occupied_spaces)
VALUES (1, 'Balta Alba Garage', 50, 0);
INSERT INTO garage (id, location, capacity, occupied_spaces)
VALUES (2, 'Tgv CarMania', 100, 0);

INSERT INTO mechanic (id, name, specialization, garage_id)
VALUES (1, 'Bob the Builder', 'Engine', 1);
INSERT INTO mechanic (id, name, specialization, garage_id)
VALUES (2, 'Handy Mandy', 'Electronics', 2);

ALTER SEQUENCE garage_id_seq RESTART WITH 10;
ALTER SEQUENCE mechanic_id_seq RESTART WITH 10;
