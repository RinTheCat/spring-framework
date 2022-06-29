package ru.otus.ajax;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.ajax.domain.Genre;
import ru.otus.ajax.exception.NotFoundException;
import ru.otus.ajax.service.GenreBusinessService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(GenreBusinessService.class)
@DataJpaTest
class GenreBusinessServiceTest {

    @Autowired
    private GenreBusinessService genreBusinessService;
    @Autowired
    private TestEntityManager em;

    private static final int EXPECTED_GENRE_COUNT = 1;
    private static final Genre NEW_GENRE = new Genre(2, "психология", null);
    private static final Genre EXISTING_GENRE = new Genre(1, "детектив", null);

    @DisplayName("Сосчитать кол-во жанров в БД")
    @Test
    void shouldReturnExpectedGenreCount() {
        Long actualGenreCount = genreBusinessService.count();
        assertThat(actualGenreCount).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @DisplayName("Добавить жанр в БД")
    @Test
    void shouldInsertGenre() {
        genreBusinessService.insert(NEW_GENRE);
        Genre actualGenre = genreBusinessService.getById(NEW_GENRE.getId());
        assertThat(actualGenre.getName()).isEqualTo(NEW_GENRE.getName());
    }

    @DisplayName("Найти жанр по id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre actualGenre = genreBusinessService.getById(EXISTING_GENRE.getId());
        assertThat(actualGenre.getName()).isEqualTo(EXISTING_GENRE.getName());
    }

    @DisplayName("Удалить жанр по id")
    @Test
    void shouldCorrectDeleteGenreById() {
        Genre dbGenre = genreBusinessService.getById(EXISTING_GENRE.getId());
        assertThat(dbGenre.getName()).isEqualTo(EXISTING_GENRE.getName());

        em.detach(dbGenre);

        genreBusinessService.deleteById(EXISTING_GENRE.getId());

        Assertions.assertThrows(NotFoundException.class, () -> {
            genreBusinessService.getById(EXISTING_GENRE.getId());
        });
    }

    @DisplayName("Вернуть список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> actualGenreList = genreBusinessService.getAll();
        assertThat(actualGenreList.get(0)).isInstanceOf(Genre.class);
    }
}
