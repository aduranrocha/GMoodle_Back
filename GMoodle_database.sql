CREATE DATABASE gmoodle;
USE gmoodle;
Drop table groupClass;
select * from userGeneral;
/* ---- USER TABLE ---- */
CREATE TABLE userGeneral(
	idUser bigint NOT NULL primary key auto_increment,
    firstName varchar(30),
    lastName varchar(30),
    gender boolean,
    address varchar(30),
    phone varchar(30),
    birthDate date,
    email varchar(30),
    isEnableUser boolean,
    pass varchar(50),
    roleUser tinyint,
    photo tinytext,
    createAt Date,
    updateAt Date,
    isEmailConfirm boolean
);

CREATE TABLE course(
	idCourse int not null primary key auto_increment,
    idUser bigint not null,
    nameCourse varchar(45),
    summaryCourse longtext,
    startDateCourse Date,
    endDateCourse Date,
    isEnableCourse boolean, 
    timeCreate Date,
    timeModified Date,
    foreign key (idUser) references userGeneral(idUser)
);

CREATE TABLE groupClass(
	idGroup bigint not null primary key auto_increment,
    nameGroup varchar(30),
    summaryGroup longtext,
    enrolmentKey varchar(45),
    timeCreated Date,
    timeModified Date,
    idNumberMax tinyint,
    isEnableGroup boolean
);

CREATE TABLE activity(
	idActivity int not null primary key auto_increment,
    idUser bigint not null,
    idCourse int not null,
    titleActivity varchar(45),
    foreign key (idCourse) references course(idCourse),
    foreign key (idUser) references userGeneral(idUser)
);

CREATE TABLE document(
	idDocument int not null primary key auto_increment,
    idUser bigint not null,
    idActivity int not null,
    titleDocument varchar(45),
    pathDocument tinytext,
    isCheak boolean,
    foreign key (idUser) references userGeneral(idUser),
    foreign key (idActivity) references activity(idActivity)
);

CREATE TABLE group_members(
	idGroup_Member int not null primary key auto_increment,
    idUser bigint not null,
    idGroup bigint not null,
    timeAdded Date,
    foreign key (idUser) references userGeneral(idUser),
    foreign key (idGroup) references groupClass(idGroup)    
);

CREATE TABLE group_course(
	idGroup_Course int not null primary key auto_increment,
	idCourse int not null,
    idGroup bigint not null,
    startAt Date,
    endAt Date,
    dayNumber tinyint,
    foreign key (idCourse) references course(idCourse),
    foreign key (idGroup) references groupClass(idGroup)
);