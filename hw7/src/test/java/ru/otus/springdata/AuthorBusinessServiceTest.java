package ru.otus.springdata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.springdata.domain.Author;
import ru.otus.springdata.service.AuthorBusinessService;
import ru.otus.springdata.service.ConsoleIOService;
import ru.otus.springdata.service.ShellCrudService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(AuthorBusinessService.class)
@DataJpaTest
class AuthorBusinessServiceTest {

    @Autowired
    private AuthorBusinessService authorBusinessService;
    @Autowired
    private TestEntityManager em;

    @MockBean
    ConsoleIOService consoleIOService;
    @MockBean
    ShellCrudService shellCrudService;

    private static final int EXPECTED_AUTHOR_COUNT = 1;
    private static final Author EXISTING_AUTHOR = new Author(1L, "Эдгар Аллан По", null);
    private static final Author NEW_AUTHOR = new Author(2L, "Артур Конан Дойл", null);

    @DisplayName("Сосчитать кол-во авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        Long actualAuthorCount = authorBusinessService.count();
        assertThat(actualAuthorCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @DisplayName("Добавить автора в БД")
    @Test
    void shouldInsertAuthor() {
        authorBusinessService.insert(NEW_AUTHOR);
        Author actualAuthor = authorBusinessService.getById(NEW_AUTHOR.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(NEW_AUTHOR);
    }

    @DisplayName("Найти автора по id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author actualAuthor = authorBusinessService.getById(EXISTING_AUTHOR.getId());
        assertThat(actualAuthor.getFullName()).isEqualTo(EXISTING_AUTHOR.getFullName());
    }

    @DisplayName("Удалить автора по id")
    @Test
    void shouldCorrectlyDeleteAuthorById() {
        assertThat(authorBusinessService.getById(EXISTING_AUTHOR.getId()).getFullName())
                .isEqualTo(EXISTING_AUTHOR.getFullName());

        authorBusinessService.deleteById(EXISTING_AUTHOR.getId());

        assertThat(authorBusinessService.getById(EXISTING_AUTHOR.getId())).isEqualTo(null);
    }

    @DisplayName("Вернуть список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> actualAuthorList = authorBusinessService.getAll();
        assertThat(actualAuthorList.get(0)).isInstanceOf(Author.class);
    }
}
