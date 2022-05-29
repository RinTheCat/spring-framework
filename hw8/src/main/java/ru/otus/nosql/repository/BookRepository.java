package ru.otus.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.nosql.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByAuthor_Id(String id);

    List<Book> findBooksByGenre_Id(String id);
}
