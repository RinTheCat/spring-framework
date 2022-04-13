package ru.orm.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.orm.otus.domain.Book;
import ru.orm.otus.domain.Comment;

@ShellComponent
public class ShellCrudService {

    private final BookBusinessService bookService;
    private final AuthorBusinessService authorService;
    private final GenreBusinessService genreService;
    private final CommentBusinessService commentService;
    private final IOService ioService;

    @Autowired
    public ShellCrudService(BookBusinessService bookService, AuthorBusinessService authorService, GenreBusinessService genreService,
                            CommentBusinessService commentService, ConsoleIOService consoleIOService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
        this.ioService = consoleIOService;
    }

    @ShellMethod(value="Book", key={"book","b"})
    public String handleBookOperation(String operation) {
        switch (operation) {
            case ("count"):
                return "Книг в БД: " + bookService.count();

            case ("createNewBook"):
                Book actualBook = bookService.createNewBook(ioService.getBook(), ioService.getAuthorId(), ioService.getGenreId());

                if (actualBook.getId() == 0) {
                    return "Автора или жанра не существует!";
                } else return "OK!";

            case ("getById"):
                Book expectedBook = bookService.getById(ioService.getBookId());
                if (expectedBook != null) {
                     return  expectedBook.toString();
                } else return "Книги не существует!";

            case ("getAll"):
                return bookService.getAll().toString();

            case ("deleteById"):
                bookService.deleteById(ioService.getBookId());
                return "OK!";

            case ("getByTitle"):
                return bookService.getByTitle(ioService.getNewInfo()).toString();

            case ("updateTitleById"):
                bookService.updateTitleById(ioService.getBookId(), ioService.getNewInfo());
                return "OK!";

            default:
                throw new UnsupportedOperationException(String.format("%s is not a supported operation", operation));
        }
    }

    @ShellMethod(value="Author", key={"author","a"})
    public String handleAuthorOperation(String operation) {
        switch (operation) {
            case ("getAll"):
                return authorService.getAll().toString();

            case ("deleteById"):
                authorService.deleteById(ioService.getAuthorId());
                return "OK!";

            case ("createNewAuthor"):
                authorService.insert(ioService.getAuthor());
                return "OK!";

            case ("updateNameById"):
                authorService.updateNameById(ioService.getAuthorId(), ioService.getNewInfo());
                return "OK!";

            default:
                throw new UnsupportedOperationException(String.format("%s is not a supported operation", operation));
        }
    }

    @ShellMethod(value="Genre", key={"genre","g"})
    public String handleGenreOperation(String operation) {
        switch (operation) {
            case ("getAll"):
                return genreService.getAll().toString();

            case ("deleteById"):
                genreService.deleteById(ioService.getGenreId());
                return "OK!";

            case ("createNewGenre"):
                genreService.insert(ioService.getGenre());
                return "OK!";

            case ("updateNameById"):
                genreService.updateNameById(ioService.getGenreId(), ioService.getNewInfo());
                return "OK!";

            default:
                throw new UnsupportedOperationException(String.format("%s is not a supported operation", operation));
        }
    }

    @ShellMethod(value="Comment", key={"comment","c"})
    public String handleCommentOperation(String operation) {
        switch (operation) {
            case ("count"):
                return "Комментариев в БД: " + commentService.count();

            case ("getAll"):
                return commentService.getAll().toString();

            case ("deleteById"):
                commentService.deleteById(ioService.getCommentId());
                return "OK!";

            case ("createNewComment"):
                Comment actualComment = commentService.createNewComment(ioService.getComment(), ioService.getBookId());

                if (actualComment.getId() == 0) {
                    return "Книги не существует!";
                } else return "OK!";

            case ("getById"):
                Comment expectedComment = commentService.getById(ioService.getCommentId());
                if (expectedComment != null) {
                    return  expectedComment.toString();
                } else return "Комментария не существует!";

            case ("updateTextById"):
                commentService.updateTextById(ioService.getCommentId(), ioService.getNewInfo());
                return "OK!";

            default:
                throw new UnsupportedOperationException(String.format("%s is not a supported operation", operation));
        }
    }
}
