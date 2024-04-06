CREATE SCHEMA IF NOT EXISTS bank_service;

CREATE SEQUENCE user_id_seq
    INCREMENT 1
    START 1;

CREATE SEQUENCE email_id_seq
    INCREMENT 1
    START 1;

CREATE SEQUENCE account_id_seq
    INCREMENT 1
    START 1;

CREATE SEQUENCE phone_id_seq
    INCREMENT 1
    START 1;

CREATE TABLE bank_service.user (
                      id BIGSERIAL PRIMARY KEY DEFAULT nextval('user_id_seq'),
                      name VARCHAR(500) NOT NULL ,
                      date_of_birth DATE NOT NULL ,
                      password VARCHAR(500) CHECK (LENGTH(PASSWORD) >= 8 AND LENGTH(PASSWORD) <= 500) NOT NULL
);

CREATE TABLE bank_service.account (
                         id BIGSERIAL PRIMARY KEY DEFAULT nextval('account_id_seq'),
                         user_id BIGINT UNIQUE NOT NULL ,
                         balance DECIMAL(15,2) NOT NULL CHECK (balance > 0) ,
                         initial_balance DECIMAL(15,2) NOT NULL CHECK (initial_balance > 0),
                         max_balance BOOLEAN NOT NULL DEFAULT false,
                         FOREIGN KEY (user_id) REFERENCES bank_service.user(id)
);

CREATE TABLE bank_service.email_data (
                            id BIGINT PRIMARY KEY DEFAULT nextval('email_id_seq'),
                            user_id BIGINT NOT NULL ,
                            email VARCHAR(200) UNIQUE NOT NULL,
                            is_login_email BOOLEAN NOT NULL DEFAULT false,
                            FOREIGN KEY (user_id) REFERENCES bank_service.user(id)
);

CREATE TABLE bank_service.phone_data (
                            id BIGSERIAL PRIMARY KEY DEFAULT nextval('phone_id_seq'),
                            user_id BIGINT NOT NULL ,
                            phone VARCHAR(13) UNIQUE NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES bank_service.user(id)
);
