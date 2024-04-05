INSERT INTO bank_service.user (id, name, date_of_birth, password)
VALUES (1, 'Ivan Ivanov', '1980-01-01', 'password123'),
       (2, 'Petr Petrov', '1985-02-02', 'password234'),
       (3, 'Sidor Sidorov', '1990-03-03', 'password345'),
       (4, 'Alex Alexeev', '1995-04-04', 'password456'),
       (5, 'Dmitry Dmitriev', '2000-05-05', 'password567'),
       (6, 'Sergey Sergeev', '1975-06-06', 'password678'),
       (7, 'Roman Romanov', '1982-07-07', 'password789'),
       (8, 'Vladimir Vladimirov', '1988-08-08', 'password890'),
       (9, 'Nikolay Nikolaev', '1993-09-09', 'password901'),
       (10, 'Mikhail Mikhailov', '1998-10-10', 'password012');

INSERT INTO bank_service.account (id, user_id, balance, initial_balance, max_balance)
VALUES (1, 1, 1000.00, 1000.00, false),
       (2, 2, 2000.00, 2000.00, false),
       (3, 3, 3000.00, 3000.00, false),
       (4, 4, 4000.00, 4000.00, false),
       (5, 5, 5000.00, 5000.00, false),
       (6, 6, 6000.00, 6000.00, false),
       (7, 7, 7000.00, 7000.00, false),
       (8, 8, 8000.00, 8000.00, false),
       (9, 9, 9000.00, 9000.00, false),
       (10, 10, 10000.00, 10000.00, false);

INSERT INTO bank_service.email_data (id, user_id, email, is_login_email)
VALUES (1, 1, 'ivan.ivanov@example.com', true),
       (2, 2, 'petr.petrov@example.com', true),
       (3, 3, 'sidor.sidorov@example.com', true),
       (4, 4, 'alex.alexeev@example.com', true),
       (5, 5, 'dmitry.dmitriev@example.com', true),
       (6, 6, 'sergey.sergeev@example.com', true),
       (7, 7, 'roman.romanov@example.com', true),
       (8, 8, 'vladimir.vladimirov@example.com', true),
       (9, 9, 'nikolay.nikolaev@example.com', true),
       (10, 10, 'mikhail.mikhailov@example.com', true);


INSERT INTO bank_service.phone_data (id, user_id, phone)
VALUES (1, 1, '+79001234501'),
       (2, 2, '+79001234502'),
       (3, 3, '+79001234503'),
       (4, 4, '+79001234504'),
       (5, 5, '+79001234505'),
       (6, 6, '+79001234506'),
       (7, 7, '+79001234507'),
       (8, 8, '+79001234508'),
       (9, 9, '+79001234509'),
       (10, 10, '+79001234510');
