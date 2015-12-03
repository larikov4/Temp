DROP TABLE IF EXISTS `events`;
CREATE TABLE IF NOT EXISTS `events` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`title` varchar(50) NOT NULL,
`date` date NOT NULL,
`price` double NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name` varchar(50) NOT NULL,
`email` varchar(50) NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`userId` bigint(20) NOT NULL DEFAULT '0',
`balance` double NOT NULL DEFAULT '0',
PRIMARY KEY (`id`),
KEY `FK_accounts_users` (`userId`),
CONSTRAINT `FK_accounts_users` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `tickets`;
CREATE TABLE IF NOT EXISTS `tickets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `eventId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `category` varchar(20) NOT NULL,
  `place` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tickets_events` (`eventId`),
  KEY `FK_tickets_users` (`userId`),
  CONSTRAINT `FK_tickets_events` FOREIGN KEY (`eventId`) REFERENCES `events` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_tickets_users` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;
