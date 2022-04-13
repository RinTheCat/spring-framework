package ru.orm.otus.service;

import ru.orm.otus.domain.Author;
import ru.orm.otus.domain.Book;
import ru.orm.otus.domain.Comment;
import ru.orm.otus.domain.Genre;

public interface IOService {
    long getAuthorId();
    long getBookId();
    long getGenreId();
    long getCommentId();
    Book getBook();
    Author getAuthor();
    Genre getGenre();
    Comment getComment();
    String getNewInfo();
}
