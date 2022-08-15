package ru.otus.docker.dto;

import ru.otus.docker.domain.Genre;

public class GenreDto {
    private long id;

    private String name;

    public GenreDto(String name) {
        this.name = name;
    }

    public GenreDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
