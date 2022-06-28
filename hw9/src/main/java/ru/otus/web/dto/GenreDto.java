package ru.otus.web.dto;

import ru.otus.web.domain.Genre;

public class GenreDto {
    private String name;

    public GenreDto() {
    }

    public GenreDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre toDomainObject() {
        return new Genre(0, this.name, null);
    }
}
