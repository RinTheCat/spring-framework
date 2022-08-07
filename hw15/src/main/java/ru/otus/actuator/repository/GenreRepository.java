package ru.otus.actuator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.actuator.domain.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> getGenresByName(String name);
}
