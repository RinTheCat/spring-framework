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
import ru.otus.nosql.repository.AuthorRepository;
import ru.otus.nosql.repository.GenreRepository;
import ru.otus.nosql.service.BookBusinessService;
import ru.otus.nosql.service.ConsoleIOService;
import ru.otus.nosql.service.ShellCrudService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Import(BookBusinessService.class)
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookBusinessServiceTest {

    @Autowired
    private BookBusinessService bookBusinessService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @MockBean
    ConsoleIOService consoleIOService;
    @MockBean
    ShellCrudService shellCrudService;
    @MockBean
    AuthorRepository authorRepository;
    @MockBean
    GenreRepository genreRepository;

    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final Author EXISTING_AUTHOR = new Author("1", "Эдгар Аллан По");
    private static final Genre EXISTING_GENRE = new Genre("1", "детектив");
    private static final Book NEW_BOOK = new Book("2", "Чёрный кот", EXISTING_AUTHOR, EXISTING_GENRE);
    private static final Book EXISTING_BOOK = new Book("1", "Убийство на улице Морг", EXISTING_AUTHOR, EXISTING_GENRE);

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

        Optional<Book> actualBook = bookBusinessService.getById(NEW_BOOK.getId());
        assertThat(actualBook.get().getTitle()).isEqualTo(NEW_BOOK.getTitle());
    }

    @DisplayName("Найти книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        Optional<Book> actualBook = bookBusinessService.getById(EXISTING_BOOK.getId());
        assertThat(actualBook.get().getTitle()).isEqualTo(EXISTING_BOOK.getTitle());
    }

    @DisplayName("Удалить книгу по id")
    @Test
    void shouldCorrectlyDeleteBookById() {
        Book dbBook = bookBusinessService.getById(EXISTING_BOOK.getId()).get();
        assertThat(dbBook.getTitle()).isEqualTo(EXISTING_BOOK.getTitle());

        bookBusinessService.deleteById(EXISTING_BOOK.getId());
        assertThat(bookBusinessService.getById(EXISTING_BOOK.getId())).isEqualTo(Optional.empty());
    }

    @DisplayName("Вернуть список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> actualBookList = bookBusinessService.getAll();
        assertThat(actualBookList.get(0)).isInstanceOf(Book.class);
    }
}
