package ru.otus.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.nosql.domain.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {

    List<Author> findAuthorsByFullName(String fullName);
}