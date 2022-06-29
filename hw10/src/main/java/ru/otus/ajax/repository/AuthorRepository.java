package ru.otus.ajax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.ajax.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAuthorsByFullName(String fullName);

}
