/* Populate tabla clientes */
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Pancho", "Perez", "blablabla@email.com", "2018-01-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Juan", "Gonzalez", "blebleble@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Alejandro", "Muñoz", "bliblibli@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Julian", "Ago", "blobloblo@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Pancho", "Perez", "blablabla@email.com", "2018-01-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Juan", "Gonzalez", "blebleble@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Alejandro", "Muñoz", "bliblibli@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Julian", "Ago", "blobloblo@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Pancho", "Perez", "blablabla@email.com", "2018-01-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Juan", "Gonzalez", "blebleble@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Alejandro", "Muñoz", "bliblibli@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Julian", "Ago", "blobloblo@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Pancho", "Perez", "blablabla@email.com", "2018-01-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Juan", "Gonzalez", "blebleble@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Alejandro", "Muñoz", "bliblibli@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Julian", "Ago", "blobloblo@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Pancho", "Perez", "blablabla@email.com", "2018-01-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Juan", "Gonzalez", "blebleble@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Alejandro", "Muñoz", "bliblibli@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Julian", "Ago", "blobloblo@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Pancho", "Perez", "blablabla@email.com", "2018-01-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Juan", "Gonzalez", "blebleble@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Alejandro", "Muñoz", "bliblibli@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Julian", "Ago", "blobloblo@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Pancho", "Perez", "blablabla@email.com", "2018-01-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Juan", "Gonzalez", "blebleble@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Alejandro", "Muñoz", "bliblibli@email.com", "2018-02-01")
INSERT INTO clientes (nombre, apellido, email, create_at) VALUES ("Julian", "Ago", "blobloblo@email.com", "2018-02-01")

/* Se crean algunos usuarios con sus roles `` */
INSERT INTO `users` (username, password, enabled) VALUES ('Luis', '$2a$10$mJpQ4vBfPCGCFBjHMgDFmuyW/qrq2bFhaJzyS7n7Aih4WmW.ilW5G', 1);
INSERT INTO `users` (username, password, enabled) VALUES ('Admin', '$2a$10$.zTGiLSKWWxr8AvAlJ30fe0RYpPXGefuWg5snMjJn6Y1FY4HxetdO', 1);

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 1);