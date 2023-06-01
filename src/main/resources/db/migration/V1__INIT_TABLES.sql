create table hibernate_sequence (
    next_val bigint
);

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL
);

CREATE TABLE files (
    file_id SERIAL PRIMARY KEY,
    filename text NOT NULL,
    original_filename text NOT NULL,
    byte_array oid NOT NULL
);