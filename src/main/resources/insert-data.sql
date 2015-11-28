DELETE FROM tickets;
DELETE FROM accounts;
DELETE FROM users;
DELETE FROM events;

INSERT INTO events (id, title, date, price) VALUES
(1, 'title1', '2015-11-24', 10),
(2, 'title2', '2015-11-23', 10),
(3, 'title3', '2015-11-24', 10),
(4, 'title4', '2015-11-22', 10),
(5, 'title1', '2015-11-24', 10),
(6, 'title6', '2015-11-21', 10),
(7, 'title7', '2015-11-24', 10),
(8, 'title1', '2015-11-24', 10),
(9, 'title9', '2015-11-23', 10),
(10, 'title1', '2015-11-24', 10);

INSERT INTO users (id, name, email) VALUES
(1, 'Ivan1', 'ivan1@email.com'),
(2, 'Ivan2', 'ivan2@email.com'),
(3, 'Ivan3', 'ivan3@email.com'),
(4, 'Ivan1', 'ivan4@email.com'),
(5, 'Ivan5', 'ivan5@email.com'),
(6, 'Ivan1', 'ivan6@email.com'),
(7, 'Ivan1', 'ivan7@email.com'),
(8, 'Ivan8', 'ivan8@email.com'),
(9, 'Ivan9', 'ivan9@email.com'),
(10, 'Ivan10', 'ivan10@email.com'),
(11, 'Ivan11', 'ivan11@email.com'),
(12, 'Ivan12', 'ivan12@email.com');

INSERT INTO accounts (id, userId, balance) VALUES
(1, 1, 10),
(2, 2, 10),
(3, 3, 10),
(4, 4, 10),
(5, 5, 10),
(6, 6, 10),
(7, 7, 10),
(8, 8, 10),
(9, 9, 10),
(10, 10, 100);

INSERT INTO tickets (id, eventId, userId, category, place) VALUES
	(1, 1, 1, 'STANDARD', 10),
	(2, 2, 2, 'PREMIUM', 10),
	(3, 3, 3, 'STANDARD', 10),
	(4, 4, 4, 'PREMIUM', 10),
	(5, 5, 5, 'STANDARD', 10),
	(6, 1, 6, 'PREMIUM', 10),
	(7, 7, 1, 'STANDARD', 10),
	(8, 8, 8, 'PREMIUM', 10),
	(9, 9, 9, 'STANDARD', 10),
	(10, 10, 10, 'PREMIUM', 10);
