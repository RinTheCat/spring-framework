package ru.orm.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.orm.otus.domain.Author;
import ru.orm.otus.repository.AuthorDaoJpa;
import ru.orm.otus.service.ConsoleIOService;
import ru.orm.otus.service.ShellCrudService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Import(AuthorDaoJpa.class)
@DataJpaTest
class AuthorDaoJpaTest {

    @Autowired
    private AuthorDaoJpa authorDaoJpa;
    @Autowired
    private TestEntityManager em;

    @MockBean
    ConsoleIOService consoleIOService;
    @MockBean
    ShellCrudService shellCrudService;

    private static final int EXPECTED_AUTHOR_COUNT = 1;
    private static final Author EXISTING_AUTHOR = new Author(1, "Эдгар Аллан По", null);
    private static final Author NEW_AUTHOR = new Author(2, "Артур Конан Дойл", null);

    @DisplayName("Сосчитать кол-во авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        Long actualAuthorCount = authorDaoJpa.count();
        assertThat(actualAuthorCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @DisplayName("Добавить автора в БД")
    @Test
    void shouldInsertAuthor() {
        authorDaoJpa.insert(NEW_AUTHOR);
        Author actualAuthor = authorDaoJpa.getById(NEW_AUTHOR.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(NEW_AUTHOR);
    }

    @DisplayName("Найти автора по id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author actualAuthor = authorDaoJpa.getById(EXISTING_AUTHOR.getId());
        assertThat(actualAuthor.getFullName()).isEqualTo(EXISTING_AUTHOR.getFullName());
    }

    @DisplayName("Удалить автора по id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        assertThat(authorDaoJpa.getById(EXISTING_AUTHOR.getId()).getFullName())
                .isEqualTo(EXISTING_AUTHOR.getFullName());

        authorDaoJpa.deleteById(EXISTING_AUTHOR.getId());

        assertThat(authorDaoJpa.getById(EXISTING_AUTHOR.getId())).isEqualTo(null);
    }

    @DisplayName("Вернуть список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> actualAuthorList = authorDaoJpa.getAll();
        assertThat(actualAuthorList.get(0)).isInstanceOf(Author.class);
    }
}
