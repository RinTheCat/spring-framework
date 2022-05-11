insert into authors (full_name) values ('Эдгар Аллан По');
insert into authors (full_name) values ('Артур Конан Дойл');
insert into authors (full_name) values ('Маргарет Митчелл');
insert into authors (full_name) values ('Лев Толстой');
insert into authors (full_name) values ('Франц Кафка');
insert into authors (full_name) values ('Джордж Оруэлл');
insert into authors (full_name) values ('Фёдор Достоевский');

insert into genres (name) values ('детектив');
insert into genres (name) values ('роман-эпопея');
insert into genres (name) values ('антиутопия');
insert into genres (name) values ('психология');

insert into books (title, author_id, genre_id) values ('Унесённые ветром', 3, 2);
insert into books (title, author_id, genre_id) values ('Война и мир', 4, 2);
insert into books (title, author_id, genre_id) values ('Шерлок Холмс', 2, 1);
insert into books (title, author_id, genre_id) values ('Убийство на улице Морг', 1, 1);
insert into books (title, author_id, genre_id) values ('Превращение', 5, 4);
insert into books (title, author_id, genre_id) values ('Процесс', 5, 3);
insert into books (title, author_id, genre_id) values ('1984', 6, 3);
insert into books (title, author_id, genre_id) values ('Преступление и наказание', 7, 4);

insert into comments (book_id, text) values (3, 'сериал понравился больше');
insert into comments (book_id, text) values (4, 'страшный');
insert into comments (book_id, text) values (4, 'так себе');
insert into comments (book_id, text) values (6, 'ничего не понял, 0/10');
insert into comments (book_id, text) values (7, 'не очень');

