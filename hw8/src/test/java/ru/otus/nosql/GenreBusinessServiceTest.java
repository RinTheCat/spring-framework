package ru.otus.nosql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.nosql.domain.Genre;
import ru.otus.nosql.service.ConsoleIOService;
import ru.otus.nosql.service.GenreBusinessService;
import ru.otus.nosql.service.ShellCrudService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(GenreBusinessService.class)
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GenreBusinessServiceTest {

    @Autowired
    private GenreBusinessService genreBusinessService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @MockBean
    ConsoleIOService consoleIOService;
    @MockBean
    ShellCrudService shellCrudService;

    private static final int EXPECTED_GENRE_COUNT = 1;
    private static final Genre NEW_GENRE = new Genre("2", "психология");
    private static final Genre EXISTING_GENRE = new Genre("1", "детектив");

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
        Optional<Genre> actualGenre = genreBusinessService.getById(NEW_GENRE.getId());
        assertThat(actualGenre.get().getName()).isEqualTo(NEW_GENRE.getName());
    }

    @DisplayName("Найти жанр по id")
    @Test
    void shouldReturnExpectedGenreById() {
        Optional<Genre> actualGenre = genreBusinessService.getById(EXISTING_GENRE.getId());
        assertThat(actualGenre.get().getName()).isEqualTo(EXISTING_GENRE.getName());
    }

    @DisplayName("Удалить жанр по id")
    @Test
    void shouldCorrectDeleteGenreById() {
        Genre dbGenre = genreBusinessService.getById(EXISTING_GENRE.getId()).get();
        assertThat(dbGenre.getName()).isEqualTo(EXISTING_GENRE.getName());

        genreBusinessService.deleteById(EXISTING_GENRE.getId());
        assertThat(genreBusinessService.getById(EXISTING_GENRE.getId())).isEqualTo(Optional.empty());
    }

    @DisplayName("Вернуть список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> actualGenreList = genreBusinessService.getAll();
        assertThat(actualGenreList.get(0)).isInstanceOf(Genre.class);
    }
}