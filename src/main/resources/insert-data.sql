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
-- Дамп данных таблицы springdb.accounts: ~10 rows (приблизительно)
DELETE FROM accounts;
/*!40000 ALTER TABLE accounts DISABLE KEYS */;
INSERT INTO accounts (id, userId, balance) VALUES
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
/*!40000 ALTER TABLE accounts ENABLE KEYS */;

-- Дамп данных таблицы springdb.events: ~10 rows (приблизительно)
DELETE FROM events;
/*!40000 ALTER TABLE events DISABLE KEYS */;
INSERT INTO events (id, title, date, price) VALUES
	(1, 'title1', '2015-11-24', 10),
	(2, 'title2', '2015-11-24', 10),
	(3, 'title3', '2015-11-24', 10),
	(4, 'title4', '2015-11-24', 10),
	(5, 'title5', '2015-11-24', 10),
	(6, 'title6', '2015-11-24', 10),
	(7, 'title7', '2015-11-24', 10),
	(8, 'title8', '2015-11-24', 10),
	(9, 'title9', '2015-11-24', 10),
	(10, 'title10', '2015-11-24', 10);
/*!40000 ALTER TABLE events ENABLE KEYS */;

-- Дамп данных таблицы springdb.tickets: ~10 rows (приблизительно)
DELETE FROM tickets;
/*!40000 ALTER TABLE tickets DISABLE KEYS */;
INSERT INTO tickets (id, eventId, userId, category, place) VALUES
	(1, 1, 1, 'STANDARD', 10),
	(2, 2, 2, 'PREMIUM', 10),
	(3, 3, 3, 'STANDARD', 10),
	(4, 4, 4, 'PREMIUM', 10),
	(5, 5, 5, 'STANDARD', 10),
	(6, 6, 6, 'PREMIUM', 10),
	(7, 7, 7, 'STANDARD', 10),
	(8, 8, 8, 'PREMIUM', 10),
	(9, 9, 9, 'STANDARD', 10),
	(10, 10, 10, 'PREMIUM', 10);
/*!40000 ALTER TABLE tickets ENABLE KEYS */;

-- Дамп данных таблицы springdb.users: ~12 rows (приблизительно)
DELETE FROM users;
/*!40000 ALTER TABLE users DISABLE KEYS */;
INSERT INTO users (id, name, email) VALUES
	(1, 'Ivan1', 'ivan1@email.com'),
	(2, 'Ivan2', 'ivan2@email.com'),
	(3, 'Ivan3', 'ivan3@email.com'),
	(4, 'Ivan4', 'ivan4@email.com'),
	(5, 'Ivan5', 'ivan5@email.com'),
	(6, 'Ivan6', 'ivan6@email.com'),
	(7, 'Ivan7', 'ivan7@email.com'),
	(8, 'Ivan8', 'ivan8@email.com'),
	(9, 'Ivan9', 'ivan9@email.com'),
	(10, 'Ivan10', 'ivan10@email.com'),
	(11, 'Ivan11', 'ivan11@email.com'),
	(12, 'Ivan12', 'ivan12@email.com');
/*!40000 ALTER TABLE users ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
