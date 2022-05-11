package ru.otus.springdata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.springdata.domain.Author;
import ru.otus.springdata.domain.Book;
import ru.otus.springdata.domain.Genre;
import ru.otus.springdata.service.ConsoleIOService;
import ru.otus.springdata.service.ShellCrudService;
import ru.otus.springdata.service.BookBusinessService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(BookBusinessService.class)
@DataJpaTest
class BookBusinessServiceTest {

    @Autowired
    private BookBusinessService bookBusinessService;
    @Autowired
    private TestEntityManager em;

    @MockBean
    ConsoleIOService consoleIOService;
    @MockBean
    ShellCrudService shellCrudService;

    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final Author EXISTING_AUTHOR = new Author(1L, "Эдгар Аллан По", null);
    private static final Genre EXISTING_GENRE = new Genre(1L, "детектив", null);
    private static final Book NEW_BOOK = new Book(2L, "Чёрный кот", null, EXISTING_AUTHOR, EXISTING_GENRE);
    private static final Book EXISTING_BOOK = new Book(1L, "Убийство на улице Морг", null, EXISTING_AUTHOR, EXISTING_GENRE);

    @DisplayName("Сосчитать кол-во книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        Long actualBookCount = bookBusinessService.count();
        assertThat(actualBookCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("Добавить книгу в БД")
    @Test
    void shouldInsertBook() {
        bookBusinessService.insert(NEW_BOOK);
        Book actualBook = bookBusinessService.getById(NEW_BOOK.getId());
        assertThat(actualBook.getTitle()).isEqualTo(NEW_BOOK.getTitle());
    }

    @DisplayName("Найти книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        Book actualBook = bookBusinessService.getById(EXISTING_BOOK.getId());
        assertThat(actualBook.getTitle()).isEqualTo(EXISTING_BOOK.getTitle());
    }

    @DisplayName("Удалить книгу по id")
    @Test
    void shouldCorrectlyDeleteBookById() {
        assertThat(bookBusinessService.getById(EXISTING_BOOK.getId()).getTitle()).isEqualTo(EXISTING_BOOK.getTitle());

        bookBusinessService.deleteById(EXISTING_BOOK.getId());

        assertThat(bookBusinessService.getById(EXISTING_BOOK.getId())).isEqualTo(null);
    }

    @DisplayName("Вернуть список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> actualBookList = bookBusinessService.getAll();
        assertThat(actualBookList.get(0)).isInstanceOf(Book.class);
    }
}
