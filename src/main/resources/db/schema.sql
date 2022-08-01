CREATE TABLE if not exists users
(
    id       serial primary key,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled  boolean default true,
    role     VARCHAR(255) NOT NULL
);

create table if not exists bodies
(
    id   serial primary key,
    name varchar(255)
);

create table if not exists engines
(
    id   serial primary key,
    name varchar(255)
);

create table if not exists marks
(
    id   serial primary key,
    name varchar(255)
);

create table if not exists transmission
(
    id   serial primary key,
    name varchar(255)
);

create table if not exists posts
(
    id              serial primary key,
    created         timestamp,
    description     varchar(255),
    mileage         integer,
    name            varchar(255),
    price           integer,
    sale            boolean,
    user_id         INTEGER REFERENCES users (id),
    body_id         INTEGER REFERENCES bodies (id),
    engine_id       INTEGER REFERENCES engines (id),
    mark_id         INTEGER REFERENCES marks (id),
    transmission_id INTEGER REFERENCES users (id)
);

create table if not exists cars
(
    id              serial primary key,
    name            varchar(255),
    year            varchar(255),
    body_id         INTEGER REFERENCES bodies (id),
    engine_id       INTEGER REFERENCES engines (id),
    mark_id         INTEGER REFERENCES marks (id),
    transmission_id INTEGER REFERENCES users (id)
);

