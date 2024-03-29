package ru.otus.docker.dto;

import ru.otus.docker.domain.Comment;

public class CommentDto {
    private String text;

    private long bookId;

    public CommentDto(String text, long bookId) {
        this.text = text;
        this.bookId = bookId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public Comment toDomainObject() {
        return new Comment(0, this.text, null);
    }
}
