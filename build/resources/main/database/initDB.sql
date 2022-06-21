CREATE TABLE IF NOT EXISTS "role"
(
    role_id VARCHAR PRIMARY KEY NOT NULL,
    role_name VARCHAR NOT NULL UNIQUE,
    role_description VARCHAR
);

CREATE TABLE IF NOT EXISTS usertable
(
    id VARCHAR PRIMARY KEY NOT NULL,
    login VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    role VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS file_descriptor
(
    file_id VARCHAR PRIMARY KEY NOT NULL,
    created_datetime TIMESTAMP NOT NULL,
    modified_datetime TIMESTAMP,
    deleted_datetime TIMESTAMP,
    file_size INT NOT NULL,
    file_type VARCHAR NOT NULL,
    file_name VARCHAR NOT NULL,
    file_fullname VARCHAR NOT NULL,
    owner_id VARCHAR NOT NULL,
    file_status VARCHAR NOT NULL
);