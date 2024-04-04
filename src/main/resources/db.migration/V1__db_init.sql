CREATE TABLE bank_service.user (
                      id BIGINT PRIMARY KEY,
                      name VARCHAR(500) NOT NULL ,
                      date_of_birth DATE NOT NULL ,
                      password VARCHAR(500) CHECK (LENGTH(PASSWORD) >= 8 AND LENGTH(PASSWORD) <= 500) NOT NULL
);

CREATE TABLE bank_service.account (
                         id BIGINT PRIMARY KEY,
                         user_id BIGINT UNIQUE NOT NULL ,
                         balance DECIMAL(15,2) NOT NULL ,
                         initial_balance DECIMAL(15,2),
                         max_balance BOOLEAN NOT NULL DEFAULT false,
                         last_update TIMESTAMP NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES bank_service.user(id)
);


CREATE TABLE bank_service.email_data (
                            id BIGINT PRIMARY KEY,
                            user_id BIGINT NOT NULL ,
                            email VARCHAR(200) UNIQUE NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES bank_service.user(id)
);


CREATE TABLE bank_service.phone_data (
                            id BIGINT PRIMARY KEY,
                            user_id BIGINT NOT NULL ,
                            phone VARCHAR(13) UNIQUE NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES bank_service.user(id)
);
