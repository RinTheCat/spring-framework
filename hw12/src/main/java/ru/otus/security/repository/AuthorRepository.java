package ru.otus.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.security.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAuthorsByFullName(String fullName);

}
