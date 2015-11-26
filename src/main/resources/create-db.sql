-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.5.23 - MySQL Community Server (GPL)
-- ОС Сервера:                   Win32
-- HeidiSQL Версия:              8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Дамп структуры для таблица springdb.accounts
DROP TABLE IF EXISTS accounts;
CREATE TABLE IF NOT EXISTS accounts (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  userId bigint(20) NOT NULL DEFAULT '0',
  balance double NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  KEY FK_accounts_users (userId),
  CONSTRAINT FK_accounts_users FOREIGN KEY (userId) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Экспортируемые данные не выделены.


-- Дамп структуры для таблица springdb.events
DROP TABLE IF EXISTS events;
CREATE TABLE IF NOT EXISTS events (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  title varchar(50) NOT NULL,
  date date NOT NULL,
  price double NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Экспортируемые данные не выделены.


-- Дамп структуры для таблица springdb.tickets
DROP TABLE IF EXISTS tickets;
CREATE TABLE IF NOT EXISTS tickets (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  eventId bigint(20) NOT NULL,
  userId bigint(20) NOT NULL,
  category enum('STANDARD','PREMIUM','BAR') NOT NULL,
  place int(11) NOT NULL,
  PRIMARY KEY (id),
  KEY FK_tickets_events (eventId),
  KEY FK_tickets_users (userId),
  CONSTRAINT FK_tickets_events FOREIGN KEY (eventId) REFERENCES events (id),
  CONSTRAINT FK_tickets_users FOREIGN KEY (userId) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Экспортируемые данные не выделены.


-- Дамп структуры для таблица springdb.users
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY email (email)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Экспортируемые данные не выделены.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
