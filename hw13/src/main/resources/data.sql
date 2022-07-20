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

insert into authorities (authority)
    values ('ROLE_ADMIN');
insert into authorities (authority)
    values ('ROLE_LIBRARIAN');
insert into authorities (authority)
    values ('ROLE_USER');

insert into users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
    values ('admin_01', '12345', true, true, true, true);
insert into users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
    values ('librarian_01', '1234', true, true, true, true);
insert into users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
    values ('guest_01', '123', true, true, true, true);
insert into users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
    values ('not_enabled_user', '123', true, true, true, false);

insert into user_authorities (user_id, authority_id)
    values (1, 1);
insert into user_authorities (user_id, authority_id)
    values (2, 2);
insert into user_authorities (user_id, authority_id)
    values (1, 3);
insert into user_authorities (user_id, authority_id)
    values (2, 3);
insert into user_authorities (user_id, authority_id)
    values (3, 3);
insert into user_authorities (user_id, authority_id)
    values (4, 1);
insert into user_authorities (user_id, authority_id)
    values (4, 3);

