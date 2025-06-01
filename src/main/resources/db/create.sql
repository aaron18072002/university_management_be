CREATE DATABASE `university-management`;

USE `university-management`;

CREATE TABLE USERS (
	id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(255),
    first_name VARCHAR(30),
	last_name VARCHAR(30),
    date_of_birth DATE
);

SELECT * FROM USERS;