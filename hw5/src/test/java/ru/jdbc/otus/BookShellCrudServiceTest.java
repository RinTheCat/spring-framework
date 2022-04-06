package ru.jdbc.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.shell.Shell;
import ru.jdbc.otus.dao.*;
import ru.jdbc.otus.domain.Book;
import ru.jdbc.otus.service.ConsoleIOService;
import ru.jdbc.otus.service.ShellCrudService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@JdbcTest
@Import({ShellCrudService.class, BookDaoJdbc.class})
public class BookShellCrudServiceTest {

    @Autowired
    private ShellCrudService shellCrudService;
    @MockBean
    private Shell shell;
    @MockBean
    private ConsoleIOService consoleIOService;
    @Autowired
    private BookDaoJdbc bookDaoJdbc;
    @MockBean
    private AuthorDaoJdbc authorDaoJdbc;
    @MockBean
    private GenreDaoJdbc genreDaoJdbc;

    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final Book NEW_BOOK = new Book(2, "Чёрный кот", 1, 1);
    private static final Book EXISTING_BOOK = new Book(1, "Убийство на улице Морг", 1, 1);

    @Test
    @DisplayName("Должен посчитать кол-во книг")
    void shouldCountBooks() {
        assertThat(shellCrudService.handleBookOperation("count")).isEqualTo("Книг в БД: " + EXPECTED_BOOK_COUNT);
    }

    @Test
    @DisplayName("Должен добавить книгу")
    void shouldInsertBook() {
        given(consoleIOService.getBook()).willReturn(NEW_BOOK);
        shellCrudService.handleBookOperation("insert");
        assertThat(shellCrudService.handleBookOperation("count")).isEqualTo("Книг в БД: " + (EXPECTED_BOOK_COUNT + 1));
    }

    @Test
    @DisplayName("Должен удалить книгу")
    void shouldDeleteBook() {
        given(consoleIOService.getId()).willReturn(EXISTING_BOOK.getId());
        shellCrudService.handleBookOperation("deleteById");
        assertThat(shellCrudService.handleBookOperation("count")).isEqualTo("Книг в БД: " + (EXPECTED_BOOK_COUNT - 1));
    }

    @Test
    @DisplayName("Должен вывести все книги")
    void shouldGetAllBooks() {
        List<Book> expectedList = new ArrayList<>();
        expectedList.add(EXISTING_BOOK);
        assertThat(shellCrudService.handleBookOperation("getAll")).isEqualTo(expectedList.toString());
    }

    @Test
    @DisplayName("Должен найти книгу")
    void shouldFindBook() {
        given(consoleIOService.getId()).willReturn(EXISTING_BOOK.getId());
        assertThat(shellCrudService.handleBookOperation("getById")).isEqualTo(EXISTING_BOOK.toString());
    }
}
