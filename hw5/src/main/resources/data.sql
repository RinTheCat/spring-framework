insert into AUTHORS (id, fullName) values (1, 'Эдгар Аллан По');
insert into AUTHORS (id, fullName) values (2, 'Артур Конан Дойл');
insert into AUTHORS (id, fullName) values (3, 'Маргарет Митчелл');
insert into AUTHORS (id, fullName) values (4, 'Лев Толстой');
insert into AUTHORS (id, fullName) values (5, 'Франц Кафка');
insert into AUTHORS (id, fullName) values (6, 'Джордж Оруэлл');
insert into AUTHORS (id, fullName) values (7, 'Фёдор Достоевский');

insert into GENRES (id, name) values (1, 'детектив');
insert into GENRES (id, name) values (2, 'роман-эпопея');
insert into GENRES (id, name) values (3, 'антиутопия');
insert into GENRES (id, name) values (4, 'психология');

insert into BOOKS (id, title, authorId, genreId) values (1, 'Унесённые ветром', 3, 2);
insert into BOOKS (id, title, authorId, genreId) values (2, 'Война и мир', 4, 2);
insert into BOOKS (id, title, authorId, genreId) values (3, 'Шерлок Холмс', 2, 1);
insert into BOOKS (id, title, authorId, genreId) values (4, 'Убийство на улице Морг', 1, 1);
insert into BOOKS (id, title, authorId, genreId) values (5, 'Превращение', 5, 4);
insert into BOOKS (id, title, authorId, genreId) values (6, 'Процесс', 5, 3);
insert into BOOKS (id, title, authorId, genreId) values (7, '1984', 6, 3);
insert into BOOKS (id, title, authorId, genreId) values (8, 'Преступление и наказание', 7, 4);

