package ru.jdbc.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.jdbc.otus.dao.AuthorDao;
import ru.jdbc.otus.dao.BookDao;
import ru.jdbc.otus.dao.GenreDao;

@ShellComponent
public class ShellCrudService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final IOService ioService;

    @Autowired
    public ShellCrudService(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, ConsoleIOService consoleIOService) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.ioService = consoleIOService;
    }

    @ShellMethod(value="Book", key={"book","b"})
    public String handleBookOperation(String operation) {
        switch (operation) {
            case ("count"):
                return "Книг в БД: " + bookDao.count();
            case ("insert"):
                bookDao.insert(ioService.getBook());
                return "Успешно!";
            case ("getById"):
                return bookDao.getById(ioService.getId()).toString();
            case ("getAll"):
                return bookDao.getAll().toString();
            case ("deleteById"):
                bookDao.deleteById(ioService.getId());
                return "Успешно!";
            default:
                throw new UnsupportedOperationException(String.format("%s is not a supported operation", operation));
        }
    }

    @ShellMethod(value="Author", key={"author","a"})
    public String handleAuthorOperation(String operation) {
        switch (operation) {
            case ("count"):
                return "Авторов в БД: " + authorDao.count();
            case ("insert"):
                authorDao.insert(ioService.getAuthor());
                return "Успешно!";
            case ("getById"):
                return authorDao.getById(ioService.getId()).toString();
            case ("getAll"):
                return authorDao.getAll().toString();
            case ("deleteById"):
                authorDao.deleteById(ioService.getId());
                return "Успешно!";
            default:
                throw new UnsupportedOperationException(String.format("%s is not a supported operation", operation));
        }
    }

    @ShellMethod(value="Genre", key={"genre","g"})
    public String handleGenreOperation(String operation) {
        switch (operation) {
            case ("count"):
                return "Жанров в БД: " + genreDao.count();
            case ("insert"):
                genreDao.insert(ioService.getGenre());
                return "Успешно!";
            case ("getById"):
                return genreDao.getById(ioService.getId()).toString();
            case ("getAll"):
                return genreDao.getAll().toString();
            case ("deleteById"):
                genreDao.deleteById(ioService.getId());
                return "Успешно!";
            default:
                throw new UnsupportedOperationException(String.format("%s is not a supported operation", operation));
        }
    }
}
