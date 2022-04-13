package ru.orm.otus.repository;

import ru.orm.otus.domain.Genre;

import java.util.List;

public interface GenreDao {

    Long count();

    Genre insert(Genre genre);

    Genre getById(long id);

    void deleteById(long id);

    List<Genre> getByName(String name);

    List<Genre> getAll();
}
