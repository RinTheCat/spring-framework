package ru.otus.nosql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.nosql.domain.Author;
import ru.otus.nosql.domain.Book;
import ru.otus.nosql.domain.Genre;
import ru.otus.nosql.exception.NotFoundException;
import ru.otus.nosql.repository.AuthorRepository;
import ru.otus.nosql.repository.BookRepository;
import ru.otus.nosql.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookBusinessService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookBusinessService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public Long count() {
        return bookRepository.count();
    }

    @Transactional
    public void createNewBook(Book newBook, String authorId, String genreId) {
        Optional<Author> wantedAuthor = authorRepository.findById(authorId);
        Optional<Genre> wantedGenre = genreRepository.findById(genreId);

        if (wantedAuthor.isEmpty()) throw new NotFoundException(String.format("The author with id=%s was not found", authorId));
        if (wantedGenre.isEmpty()) throw new NotFoundException(String.format("The genre with id=%s was not found", genreId));

        newBook.setAuthor(wantedAuthor.get());
        newBook.setGenre(wantedGenre.get());
        bookRepository.save(newBook);
    }

    public Optional<Book> getById(String id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getByTitle(String title) {
        return bookRepository.findBooksByTitle(title);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void updateTitleById(String id, String title) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isEmpty()) throw new NotFoundException(String.format("The book with id=%s was not found", id));
        existingBook.get().setTitle(title);
        bookRepository.save(existingBook.get());
    }

    public List<Book> getByAuthorId(String authorId) {
        return bookRepository.findBooksByAuthor_Id(authorId);
    }

    public List<Book> getByGenreId(String authorId) {
        return bookRepository.findBooksByGenre_Id(authorId);
    }
}
