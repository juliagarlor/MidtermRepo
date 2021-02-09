DROP SCHEMA IF EXISTS superdb_test;
CREATE SCHEMA superdb_test;
USE superdb_test;

CREATE TABLE `user`(
id BIGINT AUTO_INCREMENT NOT NULL,
age INT,
`password` VARCHAR(255),
last_name VARCHAR(60),
first_name VARCHAR(60),
middle_name VARCHAR(60),
salutation VARCHAR(60),
PRIMARY KEY(id)
);

CREATE TABLE account_holder(
id BIGINT AUTO_INCREMENT NOT NULL,
date_of_birth DATETIME,
mailing_address_number INT,
mailing_address_street VARCHAR(60),
mailing_address_city VARCHAR(60),
mailing_address_country VARCHAR(60),
primary_address_number INT,
primary_address_street VARCHAR(60),
primary_address_city VARCHAR(60),
primary_address_country VARCHAR(60),
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
-- ni puñetera idea de por que me obliga a poner aqui amount
amount DECIMAL(19, 4),
balance DECIMAL(19, 4),
currency VARCHAR(255),
primary_owner_id BIGINT,
secondary_owner_id BIGINT,
PRIMARY KEY(id),
FOREIGN KEY(primary_owner_id) REFERENCES account_holder(id),
FOREIGN KEY(secondary_owner_id) REFERENCES account_holder(id)
);

CREATE TABLE checking_account(
id BIGINT AUTO_INCREMENT NOT NULL,
amount DECIMAL(19, 4),
currency VARCHAR(255),
secret_key VARCHAR(255),
`status` VARCHAR(255),
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES `account`(id)
);

CREATE TABLE credit_card_account(
id BIGINT AUTO_INCREMENT NOT NULL,
currency VARCHAR(255),
monthly_maintenance_fee DECIMAL(19, 4),
interest_rate DECIMAL(19, 4),
credit_limit DECIMAL(19, 4),
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES `account`(id)
);

CREATE TABLE savings_account(
id BIGINT AUTO_INCREMENT NOT NULL,
-- amount DECIMAL(19, 4),
currency VARCHAR(255),
secret_key VARCHAR(255),
`status` VARCHAR(255),
minimum_balance DECIMAL(19, 4),
interest_rate DECIMAL(19, 4),
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES `account`(id)
);

CREATE TABLE student_checking_account(
id BIGINT AUTO_INCREMENT NOT NULL,
-- amount DECIMAL(19, 4),
-- currency VARCHAR(255),
secret_key VARCHAR(255),
`status` VARCHAR(255),
PRIMARY KEY(id),
FOREIGN KEY(id) REFERENCES `account`(id)
);

CREATE TABLE transactions(
id BIGINT AUTO_INCREMENT NOT NULL,
emisor_id BIGINT,
receptor_id BIGINT,
amount DECIMAL(19, 4),
currency VARCHAR(255),
moment DATETIME,
PRIMARY KEY(id),
FOREIGN KEY(emisor_id) REFERENCES `account`(id),
FOREIGN KEY(receptor_id) REFERENCES `account`(id)
);

CREATE TABLE `role` (
  id BIGINT AUTO_INCREMENT NOT NULL,
  `name` VARCHAR(255),
  user_id BIGINT,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES `user`(id)
);