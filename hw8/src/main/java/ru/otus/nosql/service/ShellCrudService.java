package ru.otus.nosql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.nosql.domain.Author;
import ru.otus.nosql.domain.Book;
import ru.otus.nosql.domain.Comment;
import ru.otus.nosql.exception.NotFoundException;

import java.util.Optional;

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
                try {
                    bookService.createNewBook(ioService.getBook(), ioService.getAuthorId(), ioService.getGenreId());
                    return "OK!";
                } catch (NotFoundException e) {
                    return "Автора или жанра не существует!";
                }

            case ("getById"):
                Optional<Book> expectedBook = bookService.getById(ioService.getBookId());
                if (expectedBook.isPresent()) {
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
                try {
                    bookService.updateTitleById(ioService.getBookId(), ioService.getNewInfo());
                    return "OK!";
                } catch (NotFoundException e) {
                    return "Книги не существует!";
                }

            default:
                throw new UnsupportedOperationException(String.format("%s is not a supported operation", operation));
        }
    }

    @ShellMethod(value="Author", key={"author","a"})
    public String handleAuthorOperation(String operation) {
        switch (operation) {
            case ("getAll"):
                return authorService.getAll().toString();

            case ("getById"):
                Optional<Author> expectedAuthor = authorService.getById(ioService.getAuthorId());
                if (expectedAuthor.isPresent()) {
                    return  expectedAuthor.get().toString();
                } else return "Автора не существует!";

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
                try {
                    commentService.createNewComment(ioService.getComment(), ioService.getBookId());
                    return "OK!";
                } catch (NotFoundException e) {
                    return "Книги не существует!";
                }

            case ("getById"):
                Optional<Comment> expectedComment = commentService.getById(ioService.getCommentId());
                if (expectedComment.isPresent()) {
                    return  expectedComment.get().toString();
                } else return "Комментария не существует!";

            case ("updateTextById"):
                try {
                    commentService.updateTextById(ioService.getCommentId(), ioService.getNewInfo());
                    return "OK!";
                } catch (NotFoundException e) {
                    return "Комментария не существует!";
                }

            default:
                throw new UnsupportedOperationException(String.format("%s is not a supported operation", operation));
        }
    }
}

