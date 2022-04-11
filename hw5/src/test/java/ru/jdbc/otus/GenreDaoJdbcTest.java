package ru.jdbc.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.jdbc.otus.dao.GenreDaoJdbc;
import ru.jdbc.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDaoJdbc.class)
public class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc dao;

    private static final int EXPECTED_GENRE_COUNT = 1;
    private static final Genre NEW_GENRE = new Genre(2, "психология");
    private static final Genre EXISTING_GENRE = new Genre(1, "детектив");

    @DisplayName("Сосчитать кол-во жанров в БД")
    @Test
    void shouldReturnExpectedGenreCount() {
        int actualGenreCount = dao.count();
        assertThat(actualGenreCount).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @DisplayName("Добавить жанр в БД")
    @Test
    void shouldInsertGenre() {
        dao.insert(NEW_GENRE);
        Genre actualGenre = dao.getById(NEW_GENRE.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(NEW_GENRE);
    }

    @DisplayName("Найти жанр по id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre actualGenre = dao.getById(EXISTING_GENRE.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(EXISTING_GENRE);
    }

    @DisplayName("Удалить жанр по id")
    @Test
    void shouldCorrectDeleteGenreById() {
        assertThatCode(() -> dao.getById(EXISTING_GENRE.getId()))
                .doesNotThrowAnyException();

        dao.deleteById(EXISTING_GENRE.getId());

        assertThatThrownBy(() -> dao.getById(EXISTING_GENRE.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Вернуть список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> actualGenreList = dao.getAll();
        assertThat(actualGenreList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(EXISTING_GENRE);
    }
}
