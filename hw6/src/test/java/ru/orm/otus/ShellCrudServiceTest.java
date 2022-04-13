package ru.orm.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.shell.Shell;
import ru.orm.otus.domain.Author;
import ru.orm.otus.domain.Book;
import ru.orm.otus.domain.Comment;
import ru.orm.otus.domain.Genre;
import ru.orm.otus.repository.AuthorDaoJpa;
import ru.orm.otus.repository.BookDaoJpa;
import ru.orm.otus.repository.CommentDaoJpa;
import ru.orm.otus.repository.GenreDaoJpa;
import ru.orm.otus.service.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@Import({ShellCrudService.class, BookDaoJpa.class, CommentDaoJpa.class, AuthorDaoJpa.class, GenreDaoJpa.class,
        ShellCrudService.class, BookBusinessService.class, CommentBusinessService.class, AuthorBusinessService.class, GenreBusinessService.class})
public class ShellCrudServiceTest {

    @MockBean
    private Shell shell;
    @MockBean
    private ConsoleIOService consoleIOService;
    @Autowired
    private TestEntityManager em;
    @Autowired
    private ShellCrudService shellCrudService;
    @Autowired
    private BookDaoJpa bookDaoJpa;
    @Autowired
    private CommentDaoJpa commentDaoJpa;

    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final Book NEW_BOOK = new Book(2, "Чёрный кот", null, null, null);
    private static final Author NOT_EXISTING_AUTHOR = new Author(34, "Тест Тестович", null);
    private static final Genre NOT_EXISTING_GENRE = new Genre(77, "тест", null);
    private static final Book NOT_EXISTING_BOOK = new Book(88, "НИСЫ", List.of(new Comment(4, "1/10", null)), null, null);
    private static final Book EXISTING_BOOK = new Book(1, "Убийство на улице Морг", null, null, null);
    private static final Comment EXISTING_COMMENT = new Comment(1, "страшный", null);
    private static final Comment NEW_COMMENT = new Comment(2, "круто", null);


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
        assertThat(bookDaoJpa.getById(EXISTING_BOOK.getId()).getTitle()).isEqualTo(NEW_BOOK.getTitle());
    }

    @Test
    @DisplayName("Неудачный insert коммента")
    void shouldNotInsertComment() {
        given(consoleIOService.getComment()).willReturn(NOT_EXISTING_BOOK.getComments().get(0));
        given(consoleIOService.getBookId()).willReturn(NOT_EXISTING_BOOK.getId());
        assertThat(shellCrudService.handleCommentOperation("createNewComment")).isEqualTo("Книги не существует!");
    }

    @Test
    @DisplayName("Неудачный поиск коммента")
    void shouldNotFindComment() {
        given(consoleIOService.getCommentId()).willReturn(NOT_EXISTING_BOOK.getComments().get(0).getId());
        assertThat(shellCrudService.handleCommentOperation("getById")).isEqualTo("Комментария не существует!");
    }

    @Test
    @DisplayName("Удачное обновление коммента")
    void shouldUpdateComment() {
        given(consoleIOService.getCommentId()).willReturn(EXISTING_COMMENT.getId());
        given(consoleIOService.getNewInfo()).willReturn(NEW_COMMENT.getText());
        shellCrudService.handleCommentOperation("updateTextById");
        assertThat(commentDaoJpa.getById(EXISTING_COMMENT.getId()).getText()).isEqualTo(NEW_COMMENT.getText());
    }
}
