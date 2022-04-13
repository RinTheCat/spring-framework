package ru.orm.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.orm.otus.domain.Author;
import ru.orm.otus.domain.Book;
import ru.orm.otus.domain.Genre;
import ru.orm.otus.repository.BookDaoJpa;
import ru.orm.otus.service.ConsoleIOService;
import ru.orm.otus.service.ShellCrudService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(BookDaoJpa.class)
@DataJpaTest
class BookDaoJpaTest {

    @Autowired
    private BookDaoJpa bookDaoJpa;
    @Autowired
    private TestEntityManager em;

    @MockBean
    ConsoleIOService consoleIOService;
    @MockBean
    ShellCrudService shellCrudService;

    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final Author EXISTING_AUTHOR = new Author(1, "Эдгар Аллан По", null);
    private static final Genre EXISTING_GENRE = new Genre(1, "детектив", null);
    private static final Book NEW_BOOK = new Book(2, "Чёрный кот", null, EXISTING_AUTHOR, EXISTING_GENRE);
    private static final Book EXISTING_BOOK = new Book(1, "Убийство на улице Морг", null, EXISTING_AUTHOR, EXISTING_GENRE);

    @DisplayName("Сосчитать кол-во книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        Long actualBookCount = bookDaoJpa.count();
        assertThat(actualBookCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("Добавить книгу в БД")
    @Test
    void shouldInsertBook() {
        bookDaoJpa.insert(NEW_BOOK);
        Book actualBook = bookDaoJpa.getById(NEW_BOOK.getId());
        assertThat(actualBook.getTitle()).isEqualTo(NEW_BOOK.getTitle());
    }

    @DisplayName("Найти книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        Book actualBook = bookDaoJpa.getById(EXISTING_BOOK.getId());
        assertThat(actualBook.getTitle()).isEqualTo(EXISTING_BOOK.getTitle());
    }

    @DisplayName("Удалить книгу по id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThat(bookDaoJpa.getById(EXISTING_BOOK.getId()).getTitle()).isEqualTo(EXISTING_BOOK.getTitle());

        bookDaoJpa.deleteById(EXISTING_BOOK.getId());

        assertThat(bookDaoJpa.getById(EXISTING_BOOK.getId())).isEqualTo(null);
    }

    @DisplayName("Вернуть список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> actualBookList = bookDaoJpa.getAll();
        assertThat(actualBookList.get(0)).isInstanceOf(Book.class);
    }
}
