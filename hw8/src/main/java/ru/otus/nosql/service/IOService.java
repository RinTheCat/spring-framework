package ru.otus.nosql.service;

import ru.otus.nosql.domain.Author;
import ru.otus.nosql.domain.Comment;
import ru.otus.nosql.domain.Genre;
import ru.otus.nosql.domain.Book;

public interface IOService {
    String getAuthorId();
    String getBookId();
    String getGenreId();
    String getCommentId();
    Book getBook();
    Author getAuthor();
    Genre getGenre();
    Comment getComment();
    String getNewInfo();
}
