package ru.jdbc.otus.domain;

import lombok.Data;

@Data
public class Book {
    private final long id;
    private final String title;
    private final long authorId;
    private final long genreId;
}
