INSERT INTO patients (id, email, password, id_card_no, first_name, last_name, phone_number, birthday)
VALUES (gen_random_uuid(),'anna.nowak@example.com', 'Tajne#2026', 'ABC123456', 'Anna', 'Nowak', '600100200', '1990-05-12'),
       (gen_random_uuid(),'jan.chrzan@tuta.mail', 'siemaEniuuu', 'CBA654321', 'Rychu', 'Peja', '211521152',
        '1976-09-17') ON CONFLICT DO NOTHING;
