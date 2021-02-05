DROP SCHEMA IF EXISTS superdb;
CREATE SCHEMA superdb;
USE superdb;

 CREATE TABLE `account`(
 id BIGINT AUTO_INCREMENT NOT NULL,
 balance DECIMAL(10, 4),
 primary_owner_id BIGINT,
 PRIMARY KEY(id)
 );
 
 -- voy a probar a ver si acepta que no incluya las constantes
 CREATE TABLE checking_account(
 secret_key VARCHAR(255),
 secondary_owner_id BIGINT,
 `status` VARCHAR(255)
 ); 
 
-- aquí voy a probar a ver si me acepta el optional 
CREATE TABLE credit_card_account(
secondary_owner_id BIGINT,
monthly_maintenance_fee DECIMAL(10, 4),
interest_rate DECIMAL(10, 4),
credit_limit DECIMAL(10, 4)
); 

CREATE TABLE savings_account(
secret_key VARCHAR(255),
secondary_owner_id BIGINT,
`status` VARCHAR(255),
minimum_balance DECIMAL(10, 4),
interest_rate DECIMAL(10, 4)
 ); 
 
  CREATE TABLE student_checking_account(
 secret_key VARCHAR(255),
 secondary_owner_id BIGINT,
 `status` VARCHAR(255)
 ); 