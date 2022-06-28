package ru.otus.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.web.domain.Author;
import ru.otus.web.domain.Book;
import ru.otus.web.domain.Genre;
import ru.otus.web.repository.AuthorRepository;
import ru.otus.web.repository.BookRepository;
import ru.otus.web.repository.GenreRepository;

import ru.otus.web.exception.NotFoundException;

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

        if (wantedAuthor.isEmpty()) throw new NotFoundException();
        if (wantedGenre.isEmpty()) throw new NotFoundException();

        newBook.setAuthor(wantedAuthor.get());
        newBook.setGenre(wantedGenre.get());

        wantedAuthor.get().getBooks().add(newBook);
        wantedGenre.get().getBooks().add(newBook);
        bookRepository.save(newBook);
    }

    public Book getById(long id) {
        return bookRepository.findById(id).orElseThrow(NotFoundException::new);
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
        if (existingBook.isEmpty()) throw new NotFoundException();
        existingBook.get().setTitle(title);
    }
}
