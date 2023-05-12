CREATE TABLE manufacturers
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(150) NOT NULL,
    country   VARCHAR(50) NOT NULL,
    isDeleted TINYINT DEFAULT 0 NOT NULL
)
