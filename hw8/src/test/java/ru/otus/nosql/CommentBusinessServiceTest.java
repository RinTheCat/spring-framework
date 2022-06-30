package ru.otus.nosql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.nosql.domain.Book;
import ru.otus.nosql.domain.Comment;
import ru.otus.nosql.repository.BookRepository;
import ru.otus.nosql.service.CommentBusinessService;
import ru.otus.nosql.service.ConsoleIOService;
import ru.otus.nosql.service.ShellCrudService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Import(CommentBusinessService.class)
@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CommentBusinessServiceTest {

    @Autowired
    private CommentBusinessService commentBusinessService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @MockBean
    ConsoleIOService consoleIOService;
    @MockBean
    ShellCrudService shellCrudService;
    @MockBean
    BookRepository bookRepository;

    private static final int EXPECTED_COMMENT_COUNT = 1;
    private static final Book EXISTING_BOOK = new Book("1", "Убийство на улице Морг", null, null, new ArrayList<>());
    private static final Comment NEW_COMMENT = new Comment("2", "прикольно", EXISTING_BOOK);
    private static final Comment EXISTING_COMMENT = new Comment("1", "страшный", EXISTING_BOOK);

    @DisplayName("Сосчитать кол-во комментов в БД")
    @Test
    void shouldReturnExpectedCommentCount() {
        Long actualCommentCount = commentBusinessService.count();
        assertThat(actualCommentCount).isEqualTo(EXPECTED_COMMENT_COUNT);
    }

    @DisplayName("Добавить коммент в БД")
    @Test
    void shouldInsertComment() {
        given(bookRepository.findById(EXISTING_BOOK.getId())).willReturn(Optional.of(EXISTING_BOOK));
        commentBusinessService.createNewComment(NEW_COMMENT, EXISTING_BOOK.getId());

        Optional<Comment> actualComment = commentBusinessService.getById(NEW_COMMENT.getId());
        assertThat(actualComment.get().getText()).isEqualTo(NEW_COMMENT.getText());
    }

    @DisplayName("Найти коммент по id")
    @Test
    void shouldReturnExpectedCommentById() {
        Optional<Comment> actualComment = commentBusinessService.getById(EXISTING_COMMENT.getId());
        assertThat(actualComment.get().getText()).isEqualTo(EXISTING_COMMENT.getText());
    }

    @DisplayName("Удалить коммент по id")
    @Test
    void shouldCorrectDeleteCommentById() {
        Comment dbComment = commentBusinessService.getById(EXISTING_COMMENT.getId()).get();
        assertThat(dbComment.getText())
                .isEqualTo(EXISTING_COMMENT.getText());

        commentBusinessService.deleteById(EXISTING_COMMENT.getId());
        assertThat(commentBusinessService.getById(EXISTING_COMMENT.getId())).isEqualTo(Optional.empty());
    }

    @DisplayName("Вернуть список комментов")
    @Test
    void shouldReturnExpectedCommentList() {
        List<Comment> actualCommentList = commentBusinessService.getAll();
        assertThat(actualCommentList.get(0)).isInstanceOf(Comment.class);
    }
}
