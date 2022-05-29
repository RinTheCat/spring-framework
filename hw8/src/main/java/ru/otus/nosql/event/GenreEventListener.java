package ru.otus.nosql.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.nosql.domain.Genre;
import ru.otus.nosql.domain.Book;
import ru.otus.nosql.service.BookBusinessService;

import javax.annotation.Nonnull;
import java.util.List;

@Component
public class GenreEventListener extends AbstractMongoEventListener<Genre> {

    private final BookBusinessService booksService;

    public GenreEventListener(final BookBusinessService booksService) {
        this.booksService = booksService;
    }

    @Override
    public void onAfterDelete(@Nonnull final AfterDeleteEvent<Genre> event) {
        super.onAfterDelete(event);
        String genreId = event.getSource().getObjectId("_id").toString();
        List<Book> books = booksService.getByGenreId(genreId);
        for (Book book : books) {
            booksService.deleteById(book.getId());
        }
    }
}