package ru.otus.docker.dto;

import ru.otus.docker.domain.Book;
import ru.otus.docker.domain.Comment;

import java.util.List;
import java.util.stream.Collectors;

public class BookDto {
    private long id;

    private String title;

    private List<String> comments;

    private AuthorDto author;

    private GenreDto genre;

    public BookDto(long id, String title, AuthorDto author, GenreDto genre, List<String> comments) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public GenreDto getGenre() {
        return genre;
    }

    public void setGenre(GenreDto genre) {
        this.genre = genre;
    }

    public Book toDomainObject() {
        return new Book(0, this.title, null, null, null);
    }
    
    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(),
                book.getTitle(),
                new AuthorDto(book.getAuthor().getId(), book.getAuthor().getFullName()),
                new GenreDto(book.getGenre().getId(), book.getGenre().getName()),
                book.getComments().stream().map(Comment::getText).collect(Collectors.toList()));
    }
}
