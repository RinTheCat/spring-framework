package ru.otus.actuator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.actuator.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAuthorsByFullName(String fullName);

}
