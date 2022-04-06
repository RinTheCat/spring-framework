package ru.jdbc.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.jdbc.otus.dao.AuthorDaoJdbc;
import ru.jdbc.otus.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorDaoJdbc.class)
public class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc dao;
    
    private static final int EXPECTED_AUTHOR_COUNT = 1;
    private static final Author NEW_AUTHOR = new Author(2, "Артур Конан Дойл");
    private static final Author EXISTING_AUTHOR = new Author(1, "Эдгар Аллан По");

    @DisplayName("Сосчитать кол-во авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        int actualAuthorCount = dao.count();
        assertThat(actualAuthorCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @DisplayName("Добавить автора в БД")
    @Test
    void shouldInsertAuthor() {
        dao.insert(NEW_AUTHOR);
        Author actualAuthor = dao.getById(NEW_AUTHOR.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(NEW_AUTHOR);
    }

    @DisplayName("Найти автора по id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author actualAuthor = dao.getById(EXISTING_AUTHOR.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(EXISTING_AUTHOR);
    }

    @DisplayName("Удалить автора по id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        assertThatCode(() -> dao.getById(EXISTING_AUTHOR.getId()))
                .doesNotThrowAnyException();

        dao.deleteById(EXISTING_AUTHOR.getId());

        assertThatThrownBy(() -> dao.getById(EXISTING_AUTHOR.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Вернуть список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> actualAuthorList = dao.getAll();
        assertThat(actualAuthorList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(EXISTING_AUTHOR);
    }
}
