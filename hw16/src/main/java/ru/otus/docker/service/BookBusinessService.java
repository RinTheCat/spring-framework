package ru.otus.docker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.docker.domain.Author;
import ru.otus.docker.domain.Book;
import ru.otus.docker.domain.Genre;
import ru.otus.docker.repository.AuthorRepository;
import ru.otus.docker.repository.BookRepository;
import ru.otus.docker.repository.GenreRepository;

import ru.otus.docker.exception.NotFoundException;

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
