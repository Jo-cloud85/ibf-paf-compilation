DROP DATABASE IF EXISTS rsvpV2;

CREATE DATABASE rsvpV2;

USE rsvpV2;

CREATE TABLE `rsvp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(60) DEFAULT NULL,
  `confirmation_date` date DEFAULT NULL,
  `comment` mediumtext,
  `food_type` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);

-- When adding this database to Railway, comment this off as we are using Railway root user
-- grant all privileges on rsvpV2.* to 'abcde'@'%';
-- flush privileges;