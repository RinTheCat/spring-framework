package ru.otus.actuator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.actuator.domain.Author;
import ru.otus.actuator.domain.Book;
import ru.otus.actuator.domain.Genre;
import ru.otus.actuator.exception.NotFoundException;
import ru.otus.actuator.repository.AuthorRepository;
import ru.otus.actuator.repository.GenreRepository;
import ru.otus.actuator.service.BookBusinessService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Import(BookBusinessService.class)
@DataJpaTest
class BookBusinessServiceTest {

    @Autowired
    private BookBusinessService bookBusinessService;
    @Autowired
    private TestEntityManager em;

    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;

    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final Author EXISTING_AUTHOR = new Author(1L, "Эдгар Аллан По", new ArrayList<>());
    private static final Genre EXISTING_GENRE = new Genre(1L, "детектив", new ArrayList<>());
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
        given(authorRepository.findById(EXISTING_AUTHOR.getId())).willReturn(Optional.of(EXISTING_AUTHOR));
        given(genreRepository.findById(EXISTING_GENRE.getId())).willReturn(Optional.of(EXISTING_GENRE));
        bookBusinessService.createNewBook(NEW_BOOK, EXISTING_AUTHOR.getId(), EXISTING_GENRE.getId());

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
        Book dbBook = bookBusinessService.getById(EXISTING_BOOK.getId());
        assertThat(dbBook.getTitle()).isEqualTo(EXISTING_BOOK.getTitle());

        em.detach(dbBook);

        bookBusinessService.deleteById(EXISTING_BOOK.getId());

        Assertions.assertThrows(NotFoundException.class, () -> {
            bookBusinessService.getById(EXISTING_BOOK.getId());
        });
    }

    @DisplayName("Вернуть список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> actualBookList = bookBusinessService.getAll();
        assertThat(actualBookList.get(0)).isInstanceOf(Book.class);
    }
}
