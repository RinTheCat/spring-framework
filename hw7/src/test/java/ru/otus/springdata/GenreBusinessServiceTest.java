package ru.otus.springdata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.springdata.domain.Genre;
import ru.otus.springdata.service.ConsoleIOService;
import ru.otus.springdata.service.GenreBusinessService;
import ru.otus.springdata.service.ShellCrudService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(GenreBusinessService.class)
@DataJpaTest
class GenreBusinessServiceTest {

    @Autowired
    private GenreBusinessService genreBusinessService;
    @Autowired
    private TestEntityManager em;

    @MockBean
    ConsoleIOService consoleIOService;
    @MockBean
    ShellCrudService shellCrudService;

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
        assertThat(genreBusinessService.getById(EXISTING_GENRE.getId()).getName()).isEqualTo(EXISTING_GENRE.getName());

        genreBusinessService.deleteById(EXISTING_GENRE.getId());

        assertThat(genreBusinessService.getById(EXISTING_GENRE.getId())).isEqualTo(null);
    }

    @DisplayName("Вернуть список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> actualGenreList = genreBusinessService.getAll();
        assertThat(actualGenreList.get(0)).isInstanceOf(Genre.class);
    }
}
