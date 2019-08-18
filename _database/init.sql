CREATE TABLE users (
    id int NOT NULL,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO users VALUES (0, 'administrator', 'admin1', 'administrator')
