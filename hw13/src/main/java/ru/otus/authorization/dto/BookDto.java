package ru.otus.authorization.dto;

import ru.otus.authorization.domain.Book;

public class BookDto {
    private String title;

    private long authorId;

    private long genreId;

    public BookDto() {
    }

    public BookDto(String title, long authorId, long genreId) {
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public Book toDomainObject() {
        return new Book(0, this.title, null, null, null);
    }
}
