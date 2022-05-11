package ru.otus.springdata;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.springdata.domain.Book;
import ru.otus.springdata.domain.Comment;
import ru.otus.springdata.service.CommentBusinessService;
import ru.otus.springdata.service.ConsoleIOService;
import ru.otus.springdata.service.ShellCrudService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(CommentBusinessService.class)
@DataJpaTest
class CommentBusinessServiceTest {

    @Autowired
    private CommentBusinessService commentBusinessService;
    @Autowired
    private TestEntityManager em;

    @MockBean
    ConsoleIOService consoleIOService;
    @MockBean
    ShellCrudService shellCrudService;

    private static final int EXPECTED_COMMENT_COUNT = 1;
    private static final Book EXISTING_BOOK = new Book(1, "Убийство на улице Морг", null, null, null);
    private static final Comment NEW_COMMENT = new Comment(2, "прикольно", EXISTING_BOOK);
    private static final Comment EXISTING_COMMENT = new Comment(1, "страшный", EXISTING_BOOK);

    @DisplayName("Сосчитать кол-во комментов в БД")
    @Test
    void shouldReturnExpectedCommentCount() {
        Long actualCommentCount = commentBusinessService.count();
        assertThat(actualCommentCount).isEqualTo(EXPECTED_COMMENT_COUNT);
    }

    @DisplayName("Добавить коммент в БД")
    @Test
    void shouldInsertComment() {
        commentBusinessService.insert(NEW_COMMENT);
        Comment actualComment = commentBusinessService.getById(NEW_COMMENT.getId());
        assertThat(actualComment.getText()).isEqualTo(NEW_COMMENT.getText());
    }

    @DisplayName("Найти коммент по id")
    @Test
    void shouldReturnExpectedCommentById() {
        Comment actualComment = commentBusinessService.getById(EXISTING_COMMENT.getId());
        assertThat(actualComment.getText()).isEqualTo(EXISTING_COMMENT.getText());
    }

    @DisplayName("Удалить коммент по id")
    @Test
    void shouldCorrectDeleteCommentById() {
        assertThat(commentBusinessService.getById(EXISTING_COMMENT.getId()).getText())
                .isEqualTo(EXISTING_COMMENT.getText());

        commentBusinessService.deleteById(EXISTING_COMMENT.getId());

        assertThat(commentBusinessService.getById(EXISTING_COMMENT.getId())).isEqualTo(null);
    }

    @DisplayName("Вернуть список комментов")
    @Test
    void shouldReturnExpectedCommentList() {
        List<Comment> actualCommentList = commentBusinessService.getAll();
        assertThat(actualCommentList.get(0)).isInstanceOf(Comment.class);
    }
}
