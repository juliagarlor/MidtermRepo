DROP SCHEMA IF EXISTS superdb;
CREATE SCHEMA superdb;
USE superdb;

CREATE TABLE `user`(
id BIGINT AUTO_INCREMENT NOT NULL,
`name` VARCHAR(255),
PRIMARY KEY(id)
);

CREATE TABLE account_holder(
id BIGINT AUTO_INCREMENT NOT NULL,
date_of_birth DATE,
`number` VARCHAR(60),
street VARCHAR(60),
city VARCHAR(60),
country VARCHAR(60),
mailing_address VARCHAR(255),
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES `user`(id)
);

CREATE TABLE `admin`(
id BIGINT AUTO_INCREMENT NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES `user`(id)
);

CREATE TABLE third_party(
id BIGINT AUTO_INCREMENT NOT NULL,
hashed_key VARCHAR(255),
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES `user`(id)
);

CREATE TABLE `account`(
id BIGINT AUTO_INCREMENT NOT NULL,
balance DECIMAL(10, 4),
currency VARCHAR(255),
primary_owner_id BIGINT,
PRIMARY KEY(id),
FOREIGN KEY(primary_owner_id) REFERENCES `user`(id)
);

-- voy a probar a ver si acepta que no incluya las constantes
CREATE TABLE checking_account(
id BIGINT AUTO_INCREMENT NOT NULL,
amount DECIMAL(10, 4),
currency VARCHAR(255),
secret_key VARCHAR(255),
secondary_owner_id BIGINT,
`status` VARCHAR(255),
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES `account`(id),
FOREIGN KEY(secondary_owner_id) REFERENCES `user`(id)
); 

-- aquí voy a probar a ver si me acepta el optional 
CREATE TABLE credit_card_account(
id BIGINT AUTO_INCREMENT NOT NULL,
currency VARCHAR(255),
secondary_owner_id BIGINT,
monthly_maintenance_fee DECIMAL(10, 4),
interest_rate DECIMAL(10, 4),
credit_limit DECIMAL(10, 4),
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES `account`(id),
FOREIGN KEY(secondary_owner_id) REFERENCES `user`(id)
); 

CREATE TABLE savings_account(
id BIGINT AUTO_INCREMENT NOT NULL,
amount DECIMAL(10, 4),
currency VARCHAR(255),
secret_key VARCHAR(255),
secondary_owner_id BIGINT,
`status` VARCHAR(255),
minimum_balance DECIMAL(10, 4),
interest_rate DECIMAL(10, 4),
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES `account`(id),
FOREIGN KEY(secondary_owner_id) REFERENCES `user`(id)
); 

CREATE TABLE student_checking_account(
id BIGINT AUTO_INCREMENT NOT NULL,
amount DECIMAL(10, 4),
currency VARCHAR(255),
secret_key VARCHAR(255),
secondary_owner_id BIGINT,
`status` VARCHAR(255),
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES `account`(id),
FOREIGN KEY(secondary_owner_id) REFERENCES `user`(id)
); 