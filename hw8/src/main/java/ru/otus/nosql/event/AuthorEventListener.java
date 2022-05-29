package ru.otus.nosql.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.nosql.domain.Author;
import ru.otus.nosql.domain.Book;
import ru.otus.nosql.service.BookBusinessService;

import javax.annotation.Nonnull;
import java.util.List;

@Component
public class AuthorEventListener extends AbstractMongoEventListener<Author> {

    private final BookBusinessService booksService;

    public AuthorEventListener(final BookBusinessService booksService) {
        this.booksService = booksService;
    }

    @Override
    public void onAfterDelete(@Nonnull final AfterDeleteEvent<Author> event) {
        super.onAfterDelete(event);
        String authorId = event.getSource().getObjectId("_id").toString();
        List<Book> books = booksService.getByAuthorId(authorId);
        for (Book book : books) {
            booksService.deleteById(book.getId());
        }
    }
}
