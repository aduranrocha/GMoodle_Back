INSERT INTO `users` (username, name, last_name, email, password, enabled, gender, address, phone_number, birth_date, photo, create_at) VALUES ('JohnWick', 'John', 'Wick', 'mydog@email.com', '$2a$10$Y.aOM5A6u6wIwUXuAOLFzOTiQGmAud8s/8ZGm6FZSZMjvKYHnhEcu', true, false, 'Guadalajara Jalisco', '+52 3333121261', '1990-01-01', 'image', '2020-01-01');

INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');
INSERT INTO `roles` (name) VALUES ('ROLE_TEACHER');
INSERT INTO `roles` (name) VALUES ('ROLE_STUDENT');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1);
