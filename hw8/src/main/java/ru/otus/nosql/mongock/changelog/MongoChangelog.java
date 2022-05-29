package ru.otus.nosql.mongock.changelog;

import static com.mongodb.client.model.Filters.eq;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class MongoChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "rina", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "rina")
    public void insertAuthors(MongoDatabase db) {
        MongoCollection<Document> authors = db.getCollection("authors");
        List<Document> newAuthors = new ArrayList<>();
        newAuthors.add(new Document().append("fullName", "Эдгар Аллан По"));
        newAuthors.add(new Document().append("fullName", "Артур Конан Дойл"));
        newAuthors.add(new Document().append("fullName", "Маргарет Митчелл"));
        newAuthors.add(new Document().append("fullName", "Лев Толстой"));
        newAuthors.add(new Document().append("fullName", "Франц Кафка"));
        newAuthors.add(new Document().append("fullName", "Джордж Оруэлл"));
        newAuthors.add(new Document().append("fullName", "Фёдор Достоевский"));
        authors.insertMany(newAuthors);
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "rina")
    public void insertGenres(MongoDatabase db) {
        MongoCollection<Document> genres = db.getCollection("genres");
        List<Document> newGenres = new ArrayList<>();
        newGenres.add(new Document().append("name", "детектив"));
        newGenres.add(new Document().append("name", "роман-эпопея"));
        newGenres.add(new Document().append("name", "антиутопия"));
        newGenres.add(new Document().append("name", "психология"));
        genres.insertMany(newGenres);
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "rina")
    public void insertBooks(MongoDatabase db) {
        MongoCollection<Document> genres = db.getCollection("genres");
        MongoCollection<Document> authors = db.getCollection("authors");
        MongoCollection<Document> books = db.getCollection("books");
        List<Document> newBooks = new ArrayList<>();
        newBooks.add(new Document().append("title", "Унесённые ветром")
                                   .append("author", authors.find(eq("fullName", "Маргарет Митчелл")).first())
                                   .append("genre", genres.find(eq("name", "роман-эпопея")).first()));
        newBooks.add(new Document().append("title", "Война и мир")
                                   .append("author", authors.find(eq("fullName", "Лев Толстой")).first())
                                   .append("genre", genres.find(eq("name", "роман-эпопея")).first()));
        newBooks.add(new Document().append("title", "Шерлок Холмс")
                                   .append("author", authors.find(eq("fullName", "Артур Конан Дойл")).first())
                                   .append("genre", genres.find(eq("name", "детектив")).first()));
        newBooks.add(new Document().append("title", "Убийство на улице Морг")
                                   .append("author", authors.find(eq("fullName", "Эдгар Аллан По")).first())
                                   .append("genre", genres.find(eq("name", "детектив")).first()));
        newBooks.add(new Document().append("title", "Превращение")
                                   .append("author", authors.find(eq("fullName", "Франц Кафка")).first())
                                   .append("genre", genres.find(eq("name", "психология")).first()));
        newBooks.add(new Document().append("title", "Процесс")
                                   .append("author", authors.find(eq("fullName", "Франц Кафка")).first())
                                   .append("genre", genres.find(eq("name", "антиутопия")).first()));
        newBooks.add(new Document().append("title", "1984")
                                   .append("author", authors.find(eq("fullName", "Джордж Оруэлл")).first())
                                   .append("genre", genres.find(eq("name", "антиутопия")).first()));
        newBooks.add(new Document().append("title", "Преступление и наказание")
                                   .append("author", authors.find(eq("fullName", "Фёдор Достоевский")).first())
                                   .append("genre", genres.find(eq("name", "психология")).first()));
        books.insertMany(newBooks);
    }

    @ChangeSet(order = "005", id = "insertComments", author = "rina")
    public void insertComments(MongoDatabase db) {
        MongoCollection<Document> comments = db.getCollection("comments");
        MongoCollection<Document> books = db.getCollection("books");
        List<Document> newComments = new ArrayList<>();
        newComments.add(new Document().append("text", "сериал понравился больше")
                .append("book", books.find(eq("title", "Шерлок Холмс")).first()));
        newComments.add(new Document().append("text", "страшный")
                .append("book", books.find(eq("title", "Убийство на улице Морг")).first()));
        newComments.add(new Document().append("text", "так себе")
                .append("book", books.find(eq("title", "Процесс")).first()));
        newComments.add(new Document().append("text", "ничего не понял")
                .append("book", books.find(eq("title", "Процесс")).first()));
        newComments.add(new Document().append("text", "не очень")
                .append("book", books.find(eq("title", "1984")).first()));
        comments.insertMany(newComments);
    }
}
