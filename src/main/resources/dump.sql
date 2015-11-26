-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.5.23 - MySQL Community Server (GPL)
-- ОС Сервера:                   Win32
-- HeidiSQL Версия:              8.3.0.4694
-- --------------------------------------------------------




-- Дамп структуры для таблица springdb.events
DROP TABLE IF EXISTS `events`;
CREATE TABLE IF NOT EXISTS `events` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11;

-- Дамп данных таблицы springdb.events: ~0 rows (приблизительно)
DELETE FROM `events`;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` (`id`, `title`, `date`, `price`) VALUES
	(1, 'title1', '2015-11-24 23:10:56', 10),
	(2, 'title2', '2015-11-24 23:10:56', 10),
	(3, 'title3', '2015-11-24 23:10:56', 10),
	(4, 'title4', '2015-11-24 23:10:56', 10),
	(5, 'title5', '2015-11-24 23:10:56', 10),
	(6, 'title6', '2015-11-24 23:10:56', 10),
	(7, 'title7', '2015-11-24 23:10:56', 10),
	(8, 'title8', '2015-11-24 23:10:56', 10),
	(9, 'title9', '2015-11-24 23:10:56', 10),
	(10, 'title10', '2015-11-24 23:10:56', 10);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;




-- Дамп структуры для таблица springdb.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11;

-- Дамп данных таблицы springdb.users: ~0 rows (приблизительно)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `name`, `email`) VALUES
	(1, 'Ivan1', 'ivan1@email.com'),
	(2, 'Ivan2', 'ivan2@email.com'),
	(3, 'Ivan3', 'ivan3@email.com'),
	(4, 'Ivan4', 'ivan4@email.com'),
	(5, 'Ivan5', 'ivan5@email.com'),
	(6, 'Ivan6', 'ivan6@email.com'),
	(7, 'Ivan7', 'ivan7@email.com'),
	(8, 'Ivan8', 'ivan8@email.com'),
	(9, 'Ivan9', 'ivan9@email.com'),
	(10, 'Ivan10', 'ivan10@email.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

-- Дамп структуры для таблица springdb.accounts
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL DEFAULT '0',
  `balance` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_accounts_users` (`userId`),
  CONSTRAINT `FK_accounts_users` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11;

-- Дамп данных таблицы springdb.accounts: ~0 rows (приблизительно)
DELETE FROM `accounts`;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` (`id`, `userId`, `balance`) VALUES
	(1, 1, 100),
	(2, 2, 100),
	(3, 3, 100),
	(4, 4, 100),
	(5, 5, 100),
	(6, 6, 100),
	(7, 7, 100),
	(8, 8, 100),
	(9, 9, 100),
	(10, 10, 100);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;


-- Дамп структуры для таблица springdb.tickets
DROP TABLE IF EXISTS `tickets`;
CREATE TABLE IF NOT EXISTS `tickets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `eventId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `category` tinyint(4) DEFAULT NULL,
  `place` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tickets_events` (`eventId`),
  KEY `FK_tickets_users` (`userId`),
  CONSTRAINT `FK_tickets_events` FOREIGN KEY (`eventId`) REFERENCES `events` (`id`),
  CONSTRAINT `FK_tickets_users` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11;

-- Дамп данных таблицы springdb.tickets: ~0 rows (приблизительно)
DELETE FROM `tickets`;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` (`id`, `eventId`, `userId`, `category`, `place`) VALUES
	(1, 1, 1, 1, 10),
	(2, 2, 2, 2, 10),
	(3, 3, 3, 1, 10),
	(4, 4, 4, 2, 10),
	(5, 5, 5, 1, 10),
	(6, 6, 6, 2, 10),
	(7, 7, 7, 1, 10),
	(8, 8, 8, 2, 10),
	(9, 9, 9, 1, 10),
	(10, 10, 10, 2, 10);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
