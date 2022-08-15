package ru.otus.docker.dto;

import ru.otus.docker.domain.Author;

public class AuthorDto {
    private long id;

    private String fullName;

    public AuthorDto(String fullName) {
        this.fullName = fullName;
    }

    public AuthorDto(long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Author toDomainObject() {
        return new Author(0, this.fullName, null);
    }

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }
}
