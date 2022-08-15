package ru.otus.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.docker.domain.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> getGenresByName(String name);
}
