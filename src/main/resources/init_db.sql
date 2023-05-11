create table manufacturers
(
    id        bigint auto_increment
        primary key,
    name      varchar(150)      not null,
    country   varchar(50)       not null,
    isDeleted tinyint default 0 null
)
    charset = utf8mb3;