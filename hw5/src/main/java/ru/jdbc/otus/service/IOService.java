package ru.jdbc.otus.service;

import ru.jdbc.otus.domain.Author;
import ru.jdbc.otus.domain.Book;
import ru.jdbc.otus.domain.Genre;

public interface IOService {
    long getId();
    Book getBook();
    Author getAuthor();
    Genre getGenre();
}
