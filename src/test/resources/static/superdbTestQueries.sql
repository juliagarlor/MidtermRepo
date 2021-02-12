INSERT INTO `superdb_test`.`user` (`id`, `age`, `first_name`, `last_name`, `salutation`, `password`) VALUES ('1', '40', 'maria', 'de todos los santos', 'Mrs', '$2a$10$DfHDRvoio1Km7RwWTTZGR.fHNF0NuiJMlYwz8T6Un2GirK.fk6PE.');
INSERT INTO `superdb_test`.`user` (`id`, `age`, `first_name`, `last_name`, `salutation`, `password`) VALUES ('2', '20', 'cayetano', 'de todos los santos', 'Mr', '$2a$10$DfHDRvoio1Km7RwWTTZGR.fHNF0NuiJMlYwz8T6Un2GirK.fk6PE.');

INSERT INTO `superdb_test`.`account_holder` (`date_of_birth`, `mailing_address_city`, `mailing_address_country`, `mailing_address_number`, `mailing_address_street`, `primary_address_city`, `primary_address_country`, `primary_address_number`, `primary_address_street`, `id`) VALUES ('1998-12-12 00:00:00.000000', 'city1', 'country1', '1', 'street1', 'city2', 'country2', '2', 'street2', '1');
INSERT INTO `superdb_test`.`account_holder` (`date_of_birth`, `mailing_address_city`, `mailing_address_country`, `mailing_address_number`, `mailing_address_street`, `primary_address_city`, `primary_address_country`, `primary_address_number`, `primary_address_street`, `id`) VALUES ('1980-08-09', 'city1', 'country1', '1', 'street1', 'city2', 'country2', '2', 'street2', '2');

INSERT INTO `superdb_test`.`role` (`id`, `name`, `user_id`) VALUES ('1', 'ACCOUNT_HOLDER', '1');
INSERT INTO `superdb_test`.`role` (`id`, `name`, `user_id`) VALUES ('2', 'ACCOUNT_HOLDER', '2');

INSERT INTO `superdb_test`.`account` (`id`, `amount`, `currency`, `balance`, `status`, `primary_owner_id`) VALUES ('1', '0.00', 'USD', '1000.00', 'ACTIVE', '1');
INSERT INTO `superdb_test`.`account` (`id`, `amount`, `currency`, `balance`, `status`, `primary_owner_id`) VALUES ('2', '0.00', 'USD', '1500.00', 'ACTIVE', '2');

INSERT INTO `superdb_test`.`transactions` (`amount`, `currency`, `moment`, `emisor_id`, `receptor_id`) VALUES ('50', '0', '2021-02-11-12-12-01', '1', '2');
INSERT INTO `superdb_test`.`transactions` (`amount`, `currency`, `moment`, `emisor_id`, `receptor_id`) VALUES ('20', '0', '2021-02-11-12-12-01', '1', '2');

INSERT INTO `superdb_test`.`checking_account` (`amount`, `currency`, `secret_key`, `id`) VALUES ('0.00', 'USD', '1234', '1');
INSERT INTO `superdb_test`.`checking_account` (`amount`, `currency`, `secret_key`, `id`) VALUES ('0.00', 'USD', '1234', '2');