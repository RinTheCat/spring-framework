package ru.otus.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.nosql.domain.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findGenresByName(String name);
}
