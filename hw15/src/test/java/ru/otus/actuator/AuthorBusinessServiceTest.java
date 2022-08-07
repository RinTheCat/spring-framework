package ru.otus.actuator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.actuator.domain.Author;
import ru.otus.actuator.exception.NotFoundException;
import ru.otus.actuator.service.AuthorBusinessService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(AuthorBusinessService.class)
@DataJpaTest
class AuthorBusinessServiceTest {

    @Autowired
    private AuthorBusinessService authorBusinessService;
    @Autowired
    private TestEntityManager em;

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
        Author dbAuthor = authorBusinessService.getById(EXISTING_AUTHOR.getId());
        assertThat(dbAuthor.getFullName())
                .isEqualTo(EXISTING_AUTHOR.getFullName());

        em.detach(dbAuthor);

        authorBusinessService.deleteById(EXISTING_AUTHOR.getId());
        Assertions.assertThrows(NotFoundException.class, () -> {
            authorBusinessService.getById(EXISTING_AUTHOR.getId());
        });
    }

    @DisplayName("Вернуть список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> actualAuthorList = authorBusinessService.getAll();
        assertThat(actualAuthorList.get(0)).isInstanceOf(Author.class);
    }
}
