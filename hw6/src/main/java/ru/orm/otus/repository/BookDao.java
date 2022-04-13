package ru.orm.otus.repository;

import ru.orm.otus.domain.Book;

import java.util.List;

public interface BookDao {

    Long count();

    Book insert(Book book);

    Book getById(long id);

    void deleteById(long id);

    List<Book> getByTitle(String fullName);

    List<Book> getAll();
}
