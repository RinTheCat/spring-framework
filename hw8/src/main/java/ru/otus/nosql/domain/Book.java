package ru.otus.nosql.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "books")
public class Book {
    @Id
    private String id;
    @Field(name = "title")
    private String title;
    @DBRef
    @Field(name = "author")
    private Author author;
    @DBRef
    @Field(name = "genre")
    private Genre genre;
    @Field(name = "comments")
    private List<Comment> comments;

    public Book() {
    }

    public Book(String id, String title, Author author, Genre genre, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author.getFullName() +
                ", genre=" + genre.getName() +
                ", comments=" + this.comments.stream().map(Comment::getText).collect(Collectors.toList()) +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
