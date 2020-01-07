INSERT INTO `users` (username, name, lastName, email, password, isEnabled, gender, address, degree, phoneNumber, birthDate, photo, createAt,isDemoUser) VALUES ('JohnWick', 'John', 'Wick', 'mydog@email.com', '$2a$10$Y.aOM5A6u6wIwUXuAOLFzOTiQGmAud8s/8ZGm6FZSZMjvKYHnhEcu', true, false, 'Guadalajara Jalisco','Industrial Designer', '+52 3333121261', '1990-01-01', 'image', '2020-01-01',true);

INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');
INSERT INTO `roles` (name) VALUES ('ROLE_TEACHER');
INSERT INTO `roles` (name) VALUES ('ROLE_STUDENT');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1);


INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,idNumberMax,isEnableGroup, summaryGroup,updateAt) VALUES('Cohort 12','2018-09-14','PapaMacho',30,false,'Grupo para convertirse en desarollador fullstack','2018-10-30');
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,idNumberMax,isEnableGroup, summaryGroup,updateAt) VALUES('Cohort 13','2018-11-27','LeoRocks',30,false,'Grupo para convertirse en desarollador fullstack','2018-12-15');
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,idNumberMax,isEnableGroup, summaryGroup,updateAt) VALUES('Cohort 14','2019-01-30','Ivone',30,false,'Grupo para convertirse en desarollador fullstack','2019-03-30');
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,idNumberMax,isEnableGroup, summaryGroup,updateAt) VALUES('Cohort 15','2019-03-05','Cohort15',30,true,'Grupo para convertirse en desarollador fullstack','2019-05-05');
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,idNumberMax,isEnableGroup, summaryGroup,updateAt) VALUES('Cohort 16','2019-05-22','CuidadCreativa',30,true,'Grupo para convertirse en desarollador fullstack','2019-07-11');
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,idNumberMax,isEnableGroup, summaryGroup,updateAt) VALUES('Cohort 17','2019-07-02','GrupoChido',30,true,'Grupo para convertirse en desarollador fullstack','2019-10-25');
INSERT INTO groupclass (nameGroup, createAt,enrolmentKey,idNumberMax,isEnableGroup, summaryGroup,updateAt) VALUES('Cohort 18','2019-09-30','PapaMacho',30,true,'Grupo para convertirse en desarollador fullstack','2019-11-09');

INSERT INTO course(nameCourse,summaryCourse,startDateCourse,endDateCourse,isEnableCourse,createAt,createById) VALUES ('JavaFullstack','Desarrollo de conociemiento en tecnologias web mediante JAVA Desarrollo de conociemiento en tecnologias web mediante JAVA Desarrollo de conociemiento en tecnologias web mediante JAVA Desarrollo de conociemiento en tecnologias web mediante JAVA Desarrollo de conociemiento en tecnologias web mediante JAVA Desarrollo de conociemiento en tecnologias web mediante JAVA Desarrollo de conociemiento en tecnologias web mediante JAVA Desarrollo de conociemiento en tecnologias web mediante JAVA Desarrollo de conociemiento en tecnologias web mediante JAVA','2019-09-30','2019-12-30',true,'2019-08-30',1);
INSERT INTO course(nameCourse,summaryCourse,startDateCourse,endDateCourse,isEnableCourse,createAt,createById) VALUES ('English','Mejorar en el habla del idioma Ingles en un ambiente laboral','2019-09-30','2019-12-30',true,'2019-08-30',1);
INSERT INTO course(nameCourse,summaryCourse,startDateCourse,endDateCourse,isEnableCourse,createAt,createById) VALUES ('Mentoria','Mejorar las softskills del alumno','2019-09-30','2019-12-30',true,'2019-08-30',1);

INSERT INTO activity(titleActivity,instructions,idCourse,idUser) VALUES ('Series de Fiboncci','Create a code that generates the Fibonnaci series util de the number the user ask',1,1);
INSERT INTO activity(titleActivity,instructions,idCourse,idUser) VALUES ('Email solution of problems','Write an email to solve a conflict',1,1);
INSERT INTO activity(titleActivity,instructions,idCourse,idUser) VALUES ('Interview questions','Make a list of interview questions',1,1);

INSERT INTO document(pathDoucument,isCheck,isEnableDocument) VALUES ('path/path/path',true,true);