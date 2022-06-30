package ru.otus.nosql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.nosql.domain.Author;
import ru.otus.nosql.domain.Book;
import ru.otus.nosql.domain.Comment;
import ru.otus.nosql.domain.Genre;
import ru.otus.nosql.repository.BookRepository;
import ru.otus.nosql.repository.CommentRepository;
import ru.otus.nosql.service.AuthorBusinessService;
import ru.otus.nosql.service.BookBusinessService;
import ru.otus.nosql.service.CommentBusinessService;
import ru.otus.nosql.service.ConsoleIOService;
import ru.otus.nosql.service.GenreBusinessService;
import ru.otus.nosql.service.ShellCrudService;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DataMongoTest
@Import({ShellCrudService.class, BookBusinessService.class, CommentBusinessService.class,
        AuthorBusinessService.class, GenreBusinessService.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ShellCrudServiceTest {

    @MockBean
    private Shell shell;
    @MockBean
    private ConsoleIOService consoleIOService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ShellCrudService shellCrudService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final Book NEW_BOOK = new Book("2", "Чёрный кот", null, null, new ArrayList<>());
    private static final Author NOT_EXISTING_AUTHOR = new Author("34", "Тест Тест");
    private static final Genre NOT_EXISTING_GENRE = new Genre("77", "тест");
    private static final Book NOT_EXISTING_BOOK = new Book("88", "НИСЫ", null, null, new ArrayList<>());
    private static final Book EXISTING_BOOK = new Book("1", "Убийство на улице Морг", null, null, new ArrayList<>());
    private static final Comment EXISTING_COMMENT = new Comment("1", "страшный", null);
    private static final Comment NEW_COMMENT = new Comment("2", "круто", null);

    @Test
    @DisplayName("Должен посчитать кол-во книг")
    void shouldCountBooks() {
        assertThat(shellCrudService.handleBookOperation("count")).isEqualTo("Книг в БД: " + EXPECTED_BOOK_COUNT);
    }

    @Test
    @DisplayName("Неудачный insert книги")
    void shouldNotInsertBook() {
        given(consoleIOService.getBook()).willReturn(NEW_BOOK);
        given(consoleIOService.getAuthorId()).willReturn(NOT_EXISTING_AUTHOR.getId());
        given(consoleIOService.getGenreId()).willReturn(NOT_EXISTING_GENRE.getId());
        assertThat(shellCrudService.handleBookOperation("createNewBook")).isEqualTo("Автора или жанра не существует!");
    }

    @Test
    @DisplayName("Неудачный поиск книги")
    void shouldNotFindBook() {
        given(consoleIOService.getBookId()).willReturn(NOT_EXISTING_BOOK.getId());
        assertThat(shellCrudService.handleBookOperation("getById")).isEqualTo("Книги не существует!");
    }

    @Test
    @DisplayName("Удачное обновление книги")
    void shouldUpdateBook() {
        given(consoleIOService.getBookId()).willReturn(EXISTING_BOOK.getId());
        given(consoleIOService.getNewInfo()).willReturn(NEW_BOOK.getTitle());
        shellCrudService.handleBookOperation("updateTitleById");
        assertThat(bookRepository.findById(EXISTING_BOOK.getId()).get().getTitle()).isEqualTo(NEW_BOOK.getTitle());
    }

    @Test
    @DisplayName("Неудачный insert коммента")
    void shouldNotInsertComment() {
        given(consoleIOService.getBookId()).willReturn(NOT_EXISTING_BOOK.getId());
        assertThat(shellCrudService.handleCommentOperation("createNewComment")).isEqualTo("Книги не существует!");
    }

    @Test
    @DisplayName("Неудачный поиск коммента")
    void shouldNotFindComment() {
        given(consoleIOService.getCommentId()).willReturn("88");
        assertThat(shellCrudService.handleCommentOperation("getById")).isEqualTo("Комментария не существует!");
    }

    @Test
    @DisplayName("Удачное обновление коммента")
    void shouldUpdateComment() {
        given(consoleIOService.getCommentId()).willReturn(EXISTING_COMMENT.getId());
        given(consoleIOService.getNewInfo()).willReturn(NEW_COMMENT.getText());
        shellCrudService.handleCommentOperation("updateTextById");
        assertThat(commentRepository.findById(EXISTING_COMMENT.getId()).get().getText()).isEqualTo(NEW_COMMENT.getText());
    }
}

