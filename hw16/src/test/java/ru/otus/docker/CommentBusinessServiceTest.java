package ru.otus.docker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.docker.domain.Book;
import ru.otus.docker.domain.Comment;
import ru.otus.docker.exception.NotFoundException;
import ru.otus.docker.repository.BookRepository;
import ru.otus.docker.service.CommentBusinessService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Import(CommentBusinessService.class)
@DataJpaTest
class CommentBusinessServiceTest {

    @Autowired
    private CommentBusinessService commentBusinessService;
    @Autowired
    private TestEntityManager em;

    @MockBean
    private BookRepository bookRepository;

    private static final int EXPECTED_COMMENT_COUNT = 1;
    private static final Book EXISTING_BOOK = new Book(1, "Убийство на улице Морг", new ArrayList<>(), null, null);
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
        given(bookRepository.findById(EXISTING_BOOK.getId())).willReturn(Optional.of(EXISTING_BOOK));
        commentBusinessService.createNewComment(NEW_COMMENT, EXISTING_BOOK.getId());

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
        Comment dbComment = commentBusinessService.getById(EXISTING_COMMENT.getId());
        assertThat(dbComment.getText())
                .isEqualTo(EXISTING_COMMENT.getText());

        em.detach(dbComment);

        commentBusinessService.deleteById(EXISTING_COMMENT.getId());

        Assertions.assertThrows(NotFoundException.class, () -> {
            commentBusinessService.getById(EXISTING_COMMENT.getId());
        });
    }

    @DisplayName("Вернуть список комментов")
    @Test
    void shouldReturnExpectedCommentList() {
        List<Comment> actualCommentList = commentBusinessService.getAll();
        assertThat(actualCommentList.get(0)).isInstanceOf(Comment.class);
    }
}
