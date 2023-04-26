create table manufacturer if not exists
(
    id        bigint auto_increment
        primary key,
    name      varchar(150)      null,
    country   varchar(50)       null,
    isDeleted tinyint default 0 null
)
    charset = utf8mb3;