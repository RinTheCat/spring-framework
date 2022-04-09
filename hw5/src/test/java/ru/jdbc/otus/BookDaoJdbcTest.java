package ru.jdbc.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.jdbc.otus.dao.BookDaoJdbc;
import ru.jdbc.otus.domain.Author;
import ru.jdbc.otus.domain.Book;
import ru.jdbc.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc dao;

    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final Book NEW_BOOK = new Book(2, "Чёрный кот", new Author(1, "Эдгар Аллан По"), new Genre(1, "детектив"));
    private static final Book EXISTING_BOOK = new Book(1, "Убийство на улице Морг", new Author(1, "Эдгар Аллан По"), new Genre(1, "детектив"));

    @DisplayName("Сосчитать кол-во книг в БД")
    @Test
    void shouldReturnExpectedBookCount() {
        int actualBookCount = dao.count();
        assertThat(actualBookCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("Добавить книгу в БД")
    @Test
    void shouldInsertBook() {
        dao.insert(NEW_BOOK);
        Book actualBook = dao.getById(NEW_BOOK.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(NEW_BOOK);
    }

    @DisplayName("Найти книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        Book actualBook = dao.getById(EXISTING_BOOK.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(EXISTING_BOOK);
    }

    @DisplayName("Удалить книгу по id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> dao.getById(EXISTING_BOOK.getId()))
                .doesNotThrowAnyException();

        dao.deleteById(EXISTING_BOOK.getId());

        assertThatThrownBy(() -> dao.getById(EXISTING_BOOK.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Вернуть список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> actualBookList = dao.getAll();
        System.out.println(actualBookList);
        assertThat(actualBookList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(EXISTING_BOOK);
    }
}
