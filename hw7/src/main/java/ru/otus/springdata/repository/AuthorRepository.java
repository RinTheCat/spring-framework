package ru.otus.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.springdata.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAuthorsByFullName(String fullName);

    @Query("select a from Author a where a.id = :id")
    Author getById(@Param("id") long id);
}
