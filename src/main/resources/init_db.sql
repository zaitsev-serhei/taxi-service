CREATE TABLE manufacturers
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(150) NOT NULL,
    country   VARCHAR(50) NOT NULL,
    isDeleted TINYINT DEFAULT 0 NOT NULL
);
CREATE TABLE drivers
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR (150) NOT NULL,
    licence VARCHAR (20) NOT NULL,
    login varchar(150) NOT NULL,
    seed varchar(16) NOT NULL,
    password varchar(100) NOT NULL,
    isDeleted TINYINT DEFAULT 0 NOT NULL
);
CREATE TABLE cars
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    model VARCHAR (150) NOT NULL,
    manufacturer_id BIGINT,
    isDeleted TINYINT DEFAULT 0 NOT NULL,
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id)
);
create table cars_drivers
(
    car_id BIGINT NOT NULL ,
    driver_id BIGINT NOT NULL ,
    FOREIGN KEY (car_id) references cars (id),
    FOREIGN KEY (driver_id) references drivers (id)
);
create table roles
(
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        role VARCHAR(100) NOT NULL
);
create table users_roles
(
    user_id BIGINT NOT NULL ,
    role_id BIGINT NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES drivers (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);
INSERT INTO drivers (id, name, licence, login, password, isDeleted)
VALUES (1,'ADMIN','0000','admin','admin',false);

SET GLOBAL time_zone = '+3:00'
