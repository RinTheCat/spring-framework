package ru.otus.springdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.springdata.domain.Author;
import ru.otus.springdata.domain.Book;
import ru.otus.springdata.domain.Genre;
import ru.otus.springdata.repository.AuthorRepository;
import ru.otus.springdata.repository.BookRepository;
import ru.otus.springdata.repository.GenreRepository;

import javax.persistence.EntityNotFoundException;
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
    public void createNewBook(Book newBook, long authorId, long genreId) {
        Optional<Author> wantedAuthor = authorRepository.findById(authorId);
        Optional<Genre> wantedGenre = genreRepository.findById(genreId);

        if (wantedAuthor.isEmpty()) throw new EntityNotFoundException();
        if (wantedGenre.isEmpty()) throw new EntityNotFoundException();

        wantedAuthor.get().getBooks().add(newBook);
        wantedGenre.get().getBooks().add(newBook);

        newBook.setAuthor(wantedAuthor.get());
        newBook.setGenre(wantedGenre.get());
        bookRepository.save(newBook);
    }

    public Optional<Book> getById(long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getByTitle(String fullName) {
        return bookRepository.getBooksByTitle(fullName);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void updateTitleById(long id, String title) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isEmpty()) throw new EntityNotFoundException();
        existingBook.get().setTitle(title);
    }
}
