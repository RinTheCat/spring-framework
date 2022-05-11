package ru.otus.springdata.service;

import ru.otus.springdata.domain.Author;
import ru.otus.springdata.domain.Book;
import ru.otus.springdata.domain.Comment;
import ru.otus.springdata.domain.Genre;

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
