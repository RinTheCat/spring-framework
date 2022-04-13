package ru.orm.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.orm.otus.domain.Genre;
import ru.orm.otus.repository.GenreDaoJpa;
import ru.orm.otus.service.ConsoleIOService;
import ru.orm.otus.service.ShellCrudService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(GenreDaoJpa.class)
@DataJpaTest
class GenreDaoJpaTest {

    @Autowired
    private GenreDaoJpa genreDaoJpa;
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
        Long actualGenreCount = genreDaoJpa.count();
        assertThat(actualGenreCount).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @DisplayName("Добавить жанра в БД")
    @Test
    void shouldInsertGenre() {
        genreDaoJpa.insert(NEW_GENRE);
        Genre actualGenre = genreDaoJpa.getById(NEW_GENRE.getId());
        assertThat(actualGenre.getName()).isEqualTo(NEW_GENRE.getName());
    }

    @DisplayName("Найти жанра по id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre actualGenre = genreDaoJpa.getById(EXISTING_GENRE.getId());
        assertThat(actualGenre.getName()).isEqualTo(EXISTING_GENRE.getName());
    }

    @DisplayName("Удалить жанра по id")
    @Test
    void shouldCorrectDeleteGenreById() {
        assertThat(genreDaoJpa.getById(EXISTING_GENRE.getId()).getName()).isEqualTo(EXISTING_GENRE.getName());

        genreDaoJpa.deleteById(EXISTING_GENRE.getId());

        assertThat(genreDaoJpa.getById(EXISTING_GENRE.getId())).isEqualTo(null);
    }

    @DisplayName("Вернуть список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> actualGenreList = genreDaoJpa.getAll();
        assertThat(actualGenreList.get(0)).isInstanceOf(Genre.class);
    }
}
