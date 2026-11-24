-- Dane startowe: po każdym uruchomieniu bazy jest na czym pracować, bez klikania w Bruno.
-- Skrypt wykonuje się przy każdym starcie, więc książkę o istniejącym ISBN pomijamy.
INSERT INTO patients (email, password, idCardNo, firstName, lastName, phoneNumber, birthday)
VALUES  ('anna.nowak@example.com', 'Tajne#2026',      "ABC123456", "Anna",  "Nowak", "600100200", "1990-05-12"),
        ('jan.chrzan@tuta.mail', 'siemaEniu',      "CBA654321", "Rychu",  "Peja", "211521152", "1976-09-17")

    ON CONFLICT (isbn) DO NOTHING;
