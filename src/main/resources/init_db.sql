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
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    car_id BIGINT NOT NULL ,
    driver_id BIGINT NOT NULL ,
    FOREIGN KEY (car_id) references cars (id),
    FOREIGN KEY (driver_id) references drivers (id)
);
SET GLOBAL time_zone = '+3:00'
