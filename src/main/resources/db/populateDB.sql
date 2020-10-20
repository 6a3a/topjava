DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2020-10-17 10:00:00', 'Обед', 1000, 100000),
       ('2020-10-17 14:00:00', 'админ поел', 900, 100001),
       ('2020-10-16 07:00:00', 'Завтрак', 900, 100000),
       ('2020-10-18 20:00:00', 'Ужин', 650, 100000);

