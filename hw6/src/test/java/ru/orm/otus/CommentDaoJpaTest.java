package ru.orm.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.orm.otus.domain.Book;
import ru.orm.otus.domain.Comment;
import ru.orm.otus.repository.CommentDaoJpa;
import ru.orm.otus.service.ConsoleIOService;
import ru.orm.otus.service.ShellCrudService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(CommentDaoJpa.class)
@DataJpaTest
class CommentDaoJpaTest {

    @Autowired
    private CommentDaoJpa commentDaoJpa;
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
        Long actualCommentCount = commentDaoJpa.count();
        assertThat(actualCommentCount).isEqualTo(EXPECTED_COMMENT_COUNT);
    }

    @DisplayName("Добавить коммент в БД")
    @Test
    void shouldInsertComment() {
        commentDaoJpa.insert(NEW_COMMENT);
        Comment actualComment = commentDaoJpa.getById(NEW_COMMENT.getId());
        assertThat(actualComment.getText()).isEqualTo(NEW_COMMENT.getText());
    }

    @DisplayName("Найти коммент по id")
    @Test
    void shouldReturnExpectedCommentById() {
        Comment actualComment = commentDaoJpa.getById(EXISTING_COMMENT.getId());
        assertThat(actualComment.getText()).isEqualTo(EXISTING_COMMENT.getText());
    }

    @DisplayName("Удалить коммент по id")
    @Test
    void shouldCorrectDeleteCommentById() {
        assertThat(commentDaoJpa.getById(EXISTING_COMMENT.getId()).getText())
                .isEqualTo(EXISTING_COMMENT.getText());

        commentDaoJpa.deleteById(EXISTING_COMMENT.getId());

        assertThat(commentDaoJpa.getById(EXISTING_COMMENT.getId())).isEqualTo(null);
    }

    @DisplayName("Вернуть список комментов")
    @Test
    void shouldReturnExpectedCommentList() {
        List<Comment> actualCommentList = commentDaoJpa.getAll();
        assertThat(actualCommentList.get(0)).isInstanceOf(Comment.class);
    }
}
