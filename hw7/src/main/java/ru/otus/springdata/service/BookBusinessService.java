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
    public Book insert(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void createNewBook(Book newBook, long authorId, long genreId) {
        Author wantedAuthor = authorRepository.getById(authorId);
        Genre wantedGenre = genreRepository.getById(genreId);

        if (wantedAuthor == null) throw new EntityNotFoundException();
        if (wantedGenre == null) throw new EntityNotFoundException();

        wantedAuthor.getBooks().add(newBook);
        wantedGenre.getBooks().add(newBook);

        newBook.setAuthor(wantedAuthor);
        newBook.setGenre(wantedGenre);
    }

    public Book getById(long id) {
        return bookRepository.getById(id);
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
        Book existingBook = bookRepository.getById(id);
        existingBook.setTitle(title);
    }
}
