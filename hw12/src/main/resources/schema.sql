create table authors (
                         id bigint generated by default as identity,
                         full_name varchar(255),
                         primary key (id)
);
create table genres (
                        id bigint generated by default as identity,
                        name varchar(255),
                        primary key (id)
);
create table books (
                       id bigint generated by default as identity,
                       title varchar(255),
                       genre_id bigint,
                       author_id bigint,
                       primary key (id),
                       foreign key (author_id) references authors (id),
                       foreign key (genre_id) references genres (id)
);
create table comments (
                          id bigint generated by default as identity,
                          text varchar(255),
                          book_id bigint,
                          primary key (id),
                          foreign key (book_id) references books (id)
);
create table users (
                          id bigint generated by default as identity,
                          username varchar(255),
                          password varchar(255),
                          primary key (id)
);