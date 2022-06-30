package ru.otus.nosql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.nosql.domain.Author;
import ru.otus.nosql.domain.Book;
import ru.otus.nosql.domain.Genre;
import ru.otus.nosql.service.AuthorBusinessService;
import ru.otus.nosql.service.BookBusinessService;
import ru.otus.nosql.service.ConsoleIOService;
import ru.otus.nosql.service.ShellCrudService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import({AuthorBusinessService.class, BookBusinessService.class})
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthorBusinessServiceTest {

    @Autowired
    private AuthorBusinessService authorBusinessService;
    @Autowired
    private BookBusinessService bookBusinessService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @MockBean
    ConsoleIOService consoleIOService;
    @MockBean
    ShellCrudService shellCrudService;

    private static final int EXPECTED_AUTHOR_COUNT = 1;
    private static final Author EXISTING_AUTHOR = new Author("1", "Эдгар Аллан По");
    private static final Author NEW_AUTHOR = new Author("2", "Артур Конан Дойл");
    private static final Genre EXISTING_GENRE = new Genre("1", "детектив");
    private static final Book EXISTING_BOOK = new Book("1", "Убийство на улице Морг", EXISTING_AUTHOR, EXISTING_GENRE);

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
        Optional<Author> actualAuthor = authorBusinessService.getById(NEW_AUTHOR.getId());
        assertThat(actualAuthor.get()).usingRecursiveComparison().isEqualTo(NEW_AUTHOR);
    }

    @DisplayName("Найти автора по id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Optional<Author> actualAuthor = authorBusinessService.getById(EXISTING_AUTHOR.getId());
        assertThat(actualAuthor.get().getFullName()).isEqualTo(EXISTING_AUTHOR.getFullName());
    }

    @DisplayName("Удалить автора по id")
    @Test
    void shouldCorrectlyDeleteAuthorById() {
        Author dbAuthor = authorBusinessService.getById(EXISTING_AUTHOR.getId()).get();
        assertThat(dbAuthor.getFullName())
                .isEqualTo(EXISTING_AUTHOR.getFullName());

        authorBusinessService.deleteById(EXISTING_AUTHOR.getId());
        assertThat(authorBusinessService.getById(EXISTING_AUTHOR.getId())).isEqualTo(Optional.empty());
    }

    @DisplayName("Вернуть список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> actualAuthorList = authorBusinessService.getAll();
        assertThat(actualAuthorList.get(0)).isInstanceOf(Author.class);
    }

    @DisplayName("Переименовать автора, изменения видны в книге")
    @Test
    void shouldRenameAuthor() {
        assertThat(authorBusinessService.getById(EXISTING_AUTHOR.getId()).get().getFullName()).isEqualTo(EXISTING_AUTHOR.getFullName());

        authorBusinessService.updateNameById(EXISTING_AUTHOR.getId(), NEW_AUTHOR.getFullName());

        assertThat(authorBusinessService.getById(EXISTING_AUTHOR.getId()).get().getFullName()).isEqualTo(NEW_AUTHOR.getFullName());
        assertThat(bookBusinessService.getById(EXISTING_BOOK.getId()).get().getAuthor().getFullName()).isEqualTo(NEW_AUTHOR.getFullName());
    }
}
