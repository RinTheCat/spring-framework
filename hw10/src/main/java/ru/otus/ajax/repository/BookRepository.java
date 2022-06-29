package ru.otus.ajax.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.ajax.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "book", attributePaths = {"author", "genre"})
    List<Book> getBooksByTitle(String title);

    @EntityGraph(value = "book", attributePaths = {"author", "genre"})
    List<Book> findAll();

    @EntityGraph(value = "book", attributePaths = {"author", "genre"})
    Optional<Book> findById(Long id);

}
