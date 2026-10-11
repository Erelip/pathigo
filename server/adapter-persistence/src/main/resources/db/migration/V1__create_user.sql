CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(100) DEFAULT NULL UNIQUE,
    password VARCHAR(255),
    nickname VARCHAR(100),
    phone_number VARCHAR(30),
    provider VARCHAR(50),
    provider_id VARCHAR(255)
);