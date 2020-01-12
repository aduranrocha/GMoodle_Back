INSERT INTO `users` (username, name, lastName, email, password, isEnabled, gender, address, degree, phoneNumber, birthDate, photo, createAt,isDemoUser) VALUES ('JohnWick', 'John', 'Wick', 'mydog@email.com', '$2a$10$Y.aOM5A6u6wIwUXuAOLFzOTiQGmAud8s/8ZGm6FZSZMjvKYHnhEcu', true, false, 'Guadalajara Jalisco','Industrial Designer', '+52 3333961248', '1990-01-01', 'image', '2020-01-01',true);
INSERT INTO `users` (username, name, lastName, email, password, isEnabled, gender, address, degree, phoneNumber, birthDate, photo, createAt,isDemoUser) VALUES ('admin', 'Admin', 'None', 'admin@email.com', '$2a$10$Y.aOM5A6u6wIwUXuAOLFzOTiQGmAud8s/8ZGm6FZSZMjvKYHnhEcu', true, false, 'Guadalajara Jalisco','Clinical Doctor', '+52 3333121547', '1990-01-01', 'image', '2020-01-01',true);
INSERT INTO `users` (username, name, lastName, email, password, isEnabled, gender, address, degree, phoneNumber, birthDate, photo, createAt,isDemoUser) VALUES ('oscarin', 'Oscar', 'Santana', 'osantana@gmail.com', '$2a$10$Y.aOM5A6u6wIwUXuAOLFzOTiQGmAud8s/8ZGm6FZSZMjvKYHnhEcu', true, false, 'Guadalajara Jalisco','Actuaries', '+52 3337174261', '1990-01-01', 'image', '2020-01-01',true);
INSERT INTO `users` (username, name, lastName, email, password, isEnabled, gender, address, degree, phoneNumber, birthDate, photo, createAt,isDemoUser) VALUES ('aduran', 'Alejandra', 'Duran', 'aduran@gmail.com', '$2a$10$Y.aOM5A6u6wIwUXuAOLFzOTiQGmAud8s/8ZGm6FZSZMjvKYHnhEcu', true, false, 'Guadalajara Jalisco','Aerospace Engineers', '+52 3333121261', '1990-01-01', 'image', '2020-01-01',true);
INSERT INTO `users` (username, name, lastName, email, password, isEnabled, gender, address, degree, phoneNumber, birthDate, photo, createAt,isDemoUser) VALUES ('luisSenior', 'Luis', 'Gutierrez', 'lgutierrez@gmail.com', '$2a$10$Y.aOM5A6u6wIwUXuAOLFzOTiQGmAud8s/8ZGm6FZSZMjvKYHnhEcu', true, true, 'Guadalajara Jalisco','Anesthesiologists', '+52 3383141221', '1990-01-01', 'image', '2020-01-01',true);
INSERT INTO `users` (username, name, lastName, email, password, isEnabled, gender, address, degree, phoneNumber, birthDate, photo, createAt,isDemoUser) VALUES ('purpuleLove', 'Olga', 'Valencia', 'ovalencia@gmail.com', '$2a$10$Y.aOM5A6u6wIwUXuAOLFzOTiQGmAud8s/8ZGm6FZSZMjvKYHnhEcu', true, true, 'Guadalajara Jalisco',' Art Directors', '+52 3333121261', '1990-01-01', 'image', '2020-01-01',true);
INSERT INTO `users` (username, name, lastName, email, password, isEnabled, gender, address, degree, phoneNumber, birthDate, photo, createAt,isDemoUser) VALUES ('marijoGonzalez', 'Marijo', 'Gonzales', 'mgonzalez@gmail.com', '$2a$10$Y.aOM5A6u6wIwUXuAOLFzOTiQGmAud8s/8ZGm6FZSZMjvKYHnhEcu', false, false, 'Guadalajara Jalisco','Astronomers', '+52 3335431261', '1990-01-01', 'image', '2020-01-01',true);
INSERT INTO `users` (username, name, lastName, email, password, isEnabled, gender, address, degree, phoneNumber, birthDate, photo, createAt,isDemoUser) VALUES ('AryBlackwood', 'Aranza', 'Navarrete', 'anavarretee@gmail.com', '$2a$10$Y.aOM5A6u6wIwUXuAOLFzOTiQGmAud8s/8ZGm6FZSZMjvKYHnhEcu', true, false, 'Guadalajara Jalisco','IComputer Systems Analysts', '+52 3334711261', '1990-01-01', 'image', '2020-01-01',true);

INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');
INSERT INTO `roles` (name) VALUES ('ROLE_TEACHER');
INSERT INTO `roles` (name) VALUES ('ROLE_STUDENT');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 1); 
INSERT INTO `users_roles` (user_id, role_id) VALUES (3, 2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (4, 3);
INSERT INTO `users_roles` (user_id, role_id) VALUES (5, 3);
INSERT INTO `users_roles` (user_id, role_id) VALUES (6, 2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (7, 2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (8, 3);



INSERT INTO course(nameCourse,summaryCourse,isEnableCourse,createAt,createById,idUser) VALUES ('JavaFullstack','Desarrollo de conociemiento en tecnologias web mediante JAVA',true,'2019-08-30',1,3);
INSERT INTO course(nameCourse,summaryCourse,isEnableCourse,createAt,createById,idUser) VALUES ('English','Mejorar en el habla del idioma Ingles en un ambiente laboral',true,'2019-08-30',1,1);
INSERT INTO course(nameCourse,summaryCourse,isEnableCourse,createAt,createById,idUser) VALUES ('Mentoria','Mejorar las softskills del alumno',true,'2019-08-30',1,1);

INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,numberMax,isEnableGroup, summaryGroup,updateAt,startDateGroup,endDateGroup,countNumber,isStartGroup) VALUES('Cohort 12','2018-09-14','PapaMacho',30,false,'Grupo para convertirse en desarollador fullstack','2018-10-30','2019-09-30','2019-12-30',1,true);
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,numberMax,isEnableGroup, summaryGroup,updateAt,startDateGroup,endDateGroup,countNumber,isStartGroup) VALUES('Cohort 13','2018-11-27','LeoRocks',30,false,'Grupo para convertirse en desarollador fullstack','2018-12-15','2019-09-30','2019-12-30',1,true);
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,numberMax,isEnableGroup, summaryGroup,updateAt,startDateGroup,endDateGroup,countNumber,isStartGroup) VALUES('Cohort 14','2019-01-30','Ivone',30,false,'Grupo para convertirse en desarollador fullstack','2019-03-30','2019-09-30','2019-12-30',1,true);
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,numberMax,isEnableGroup, summaryGroup,updateAt,startDateGroup,endDateGroup,countNumber,isStartGroup) VALUES('Cohort 15','2019-03-05','Cohort15',30,true,'Grupo para convertirse en desarollador fullstack','2019-05-05','2019-09-30','2019-12-30',1,true);
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,numberMax,isEnableGroup, summaryGroup,updateAt,startDateGroup,endDateGroup,countNumber,isStartGroup) VALUES('Cohort 16','2019-05-22','CuidadCreativa',30,true,'Grupo para convertirse en desarollador fullstack','2019-07-11','2019-09-30','2019-12-30',1,true);
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,numberMax,isEnableGroup, summaryGroup,updateAt,startDateGroup,endDateGroup,countNumber,isStartGroup) VALUES('Cohort 17','2019-07-02','GrupoChido',30,true,'Grupo para convertirse en desarollador fullstack','2019-10-25','2019-09-30','2019-12-30',1,true);
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,numberMax,isEnableGroup, summaryGroup,updateAt,startDateGroup,endDateGroup,countNumber,isStartGroup) VALUES('Cohort 18','2019-09-30','PapaMacho',30,true,'Grupo para convertirse en desarollador fullstack','2019-11-09','2019-09-30','2019-12-30',1,true);

INSERT INTO activity(titleActivity,instructions,idCourse,idUser) VALUES ('Series de Fiboncci','Create a code that generates the Fibonnaci series util de the number the user ask',1,1);
INSERT INTO activity(titleActivity,instructions,idCourse,idUser) VALUES ('Email solution of problems','Write an email to solve a conflict',1,1);
INSERT INTO activity(titleActivity,instructions,idCourse,idUser) VALUES ('Interview questions','Make a list of interview questions',1,1);

INSERT INTO document(pathDoucument,isCheck,isEnableDocument,idActivity,idUser) VALUES ('kndkjsndkjdsndkj',true,true,1,1);
INSERT INTO document(pathDoucument,isCheck,isEnableDocument,idActivity,idUser) VALUES ('kndkjsndkjdsndkj',true,true,1,2);
