package ru.orm.otus.repository;

import ru.orm.otus.domain.Author;

import java.util.List;

public interface AuthorDao {

    Long count();

    Author insert(Author author);

    Author getById(long id);

    void deleteById(long id);

    List<Author> getByFullName(String fullName);

    List<Author> getAll();
}
