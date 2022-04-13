package ru.orm.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.orm.otus.domain.Author;
import ru.orm.otus.domain.Book;
import ru.orm.otus.domain.Genre;
import ru.orm.otus.repository.AuthorDao;
import ru.orm.otus.repository.BookDao;
import ru.orm.otus.repository.GenreDao;

import java.util.List;

@Service
public class BookBusinessService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public BookBusinessService(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    public Long count() {
        return bookDao.count();
    }

    @Transactional
    public Book insert(Book book) {
        return bookDao.insert(book);
    }

    @Transactional
    public Book createNewBook(Book newBook, long authorId, long genreId) {
        Author wantedAuthor = authorDao.getById(authorId);
        if (wantedAuthor == null) return new Book(0, null,null,null, null);

        Genre wantedGenre = genreDao.getById(genreId);
        if (wantedGenre == null) return new Book(0, null,null,null, null);

        wantedAuthor.getBooks().add(newBook);
        wantedGenre.getBooks().add(newBook);

        newBook.setAuthor(wantedAuthor);
        newBook.setGenre(wantedGenre);

        return bookDao.insert(newBook);
    }

    @Transactional
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Transactional
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Transactional
    public List<Book> getByTitle(String fullName) {
        return null;
    }

    @Transactional
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Transactional
    public void updateTitleById(long id, String title) {
        Book existingBook = bookDao.getById(id);
        if (existingBook != null) {
            existingBook.setTitle(title);
        }
    }
}
