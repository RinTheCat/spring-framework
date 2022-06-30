package ru.otus.nosql.mongock.changelog;

import static com.mongodb.client.model.Filters.eq;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.DBRef;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

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
                                   .append("comments", new ArrayList<>())
                                   .append("author", new DBRef("authors", authors.find(eq("fullName", "Маргарет Митчелл")).first().get("_id")))
                                   .append("genre", new DBRef("genres", genres.find(eq("name", "роман-эпопея")).first().get("_id"))));
        newBooks.add(new Document().append("title", "Война и мир")
                                   .append("comments", new ArrayList<>())
                                   .append("author", new DBRef("authors", authors.find(eq("fullName", "Лев Толстой")).first().get("_id")))
                                   .append("genre", new DBRef("genres", genres.find(eq("name", "роман-эпопея")).first().get("_id"))));
        newBooks.add(new Document().append("title", "Шерлок Холмс")
                                   .append("comments", new ArrayList<>())
                                   .append("author", new DBRef("authors", authors.find(eq("fullName", "Артур Конан Дойл")).first().get("_id")))
                                   .append("genre", new DBRef("genres", genres.find(eq("name", "детектив")).first().get("_id"))));
        newBooks.add(new Document().append("title", "Убийство на улице Морг")
                                   .append("comments", new ArrayList<>())
                                   .append("author", new DBRef("authors", authors.find(eq("fullName", "Эдгар Аллан По")).first().get("_id")))
                                   .append("genre", new DBRef("genres", genres.find(eq("name", "детектив")).first().get("_id"))));
        newBooks.add(new Document().append("title", "Превращение")
                                   .append("comments", new ArrayList<>())
                                   .append("author", new DBRef("authors", authors.find(eq("fullName", "Франц Кафка")).first().get("_id")))
                                   .append("genre", new DBRef("genres", genres.find(eq("name", "психология")).first().get("_id"))));
        newBooks.add(new Document().append("title", "Процесс")
                                   .append("comments", new ArrayList<>())
                                   .append("author", new DBRef("authors", authors.find(eq("fullName", "Франц Кафка")).first().get("_id")))
                                   .append("genre", new DBRef("genres", genres.find(eq("name", "антиутопия")).first().get("_id"))));
        newBooks.add(new Document().append("title", "1984")
                                   .append("comments", new ArrayList<>())
                                   .append("author", new DBRef("authors", authors.find(eq("fullName", "Джордж Оруэлл")).first().get("_id")))
                                   .append("genre", new DBRef("genres", genres.find(eq("name", "антиутопия")).first().get("_id"))));
        newBooks.add(new Document().append("title", "Преступление и наказание")
                                   .append("comments", new ArrayList<>())
                                   .append("author", new DBRef("authors", authors.find(eq("fullName", "Фёдор Достоевский")).first().get("_id")))
                                   .append("genre", new DBRef("genres", genres.find(eq("name", "психология")).first().get("_id"))));
        books.insertMany(newBooks);
    }

    @ChangeSet(order = "005", id = "insertComments", author = "rina")
    public void insertComments(MongoDatabase db) {
        MongoCollection<Document> comments = db.getCollection("comments");
        MongoCollection<Document> books = db.getCollection("books");
        List<Document> newComments = new ArrayList<>();
        newComments.add(new Document().append("text", "сериал понравился больше")
                .append("book", new DBRef("books", books.find(eq("title", "Шерлок Холмс")).first().get("_id"))));
        newComments.add(new Document().append("text", "страшный")
                .append("book", new DBRef("books", books.find(eq("title", "Убийство на улице Морг")).first().get("_id"))));
        newComments.add(new Document().append("text", "так себе")
                .append("book", new DBRef("books", books.find(eq("title", "Процесс")).first().get("_id"))));
        newComments.add(new Document().append("text", "ничего не понял")
                .append("book", new DBRef("books", books.find(eq("title", "Процесс")).first().get("_id"))));
        newComments.add(new Document().append("text", "не очень")
                .append("book", new DBRef("books", books.find(eq("title", "1984")).first().get("_id"))));
        comments.insertMany(newComments);
    }

    @ChangeSet(order = "006", id = "insertBookComments", author = "rina")
    public void insertBookComments(MongoDatabase db) {
        MongoCollection<Document> comments = db.getCollection("comments");
        MongoCollection<Document> books = db.getCollection("books");

        books.updateOne(books.find(eq("title", "Шерлок Холмс")).first(),
                Updates.addToSet("comments", comments.find(eq("text", "сериал понравился больше")).first()),
                new UpdateOptions().upsert(true));

        books.updateOne(books.find(eq("title", "Убийство на улице Морг")).first(),
                Updates.addToSet("comments", comments.find(eq("text", "страшный")).first()),
                new UpdateOptions().upsert(true));

        books.updateOne(books.find(eq("title", "Процесс")).first(),
                Updates.addToSet("comments", comments.find(eq("text", "так себе")).first()),
                new UpdateOptions().upsert(true));

        books.updateOne(books.find(eq("title", "Процесс")).first(),
                Updates.addToSet("comments", comments.find(eq("text", "ничего не понял")).first()),
                new UpdateOptions().upsert(true));

        books.updateOne(books.find(eq("title", "1984")).first(),
                Updates.addToSet("comments", comments.find(eq("text", "не очень")).first()),
                new UpdateOptions().upsert(true));
    }
}
