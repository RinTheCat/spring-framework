package ru.otus.authorization.dto;

import ru.otus.authorization.domain.Author;

public class AuthorDto {
    private String fullName;

    public AuthorDto() {
    }

    public AuthorDto(String fullName) {
        this.fullName = fullName;
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
}
