package ru.otus.nosql.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.nosql.domain.Book;
import ru.otus.nosql.domain.Comment;
import ru.otus.nosql.service.CommentBusinessService;

import javax.annotation.Nonnull;
import java.util.List;

@Component
public class BookEventListener extends AbstractMongoEventListener<Book> {

    private final CommentBusinessService commentService;

    public BookEventListener(final CommentBusinessService commentService) {
        this.commentService = commentService;
    }

    @Override
    public void onAfterDelete(@Nonnull final AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        String bookId = event.getSource().getObjectId("_id").toString();
        List<Comment> comments = commentService.getByBookId(bookId);
        for (Comment comment : comments) {
            commentService.deleteById(comment.getId());
        }
    }
}
