package ru.otus.nosql.testmongock.changelog;

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
        newAuthors.add(new Document().append("_id", "1")
                .append("fullName", "Эдгар Аллан По"));
        authors.insertMany(newAuthors);
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "rina")
    public void insertGenres(MongoDatabase db) {
        MongoCollection<Document> genres = db.getCollection("genres");
        List<Document> newGenres = new ArrayList<>();
        newGenres.add(new Document().append("_id", "1")
                .append("name", "детектив"));
        genres.insertMany(newGenres);
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "rina")
    public void insertBooks(MongoDatabase db) {
        MongoCollection<Document> genres = db.getCollection("genres");
        MongoCollection<Document> authors = db.getCollection("authors");
        MongoCollection<Document> books = db.getCollection("books");
        List<Document> newBooks = new ArrayList<>();
        newBooks.add(new Document().append("_id", "1")
                .append("title", "Убийство на улице Морг")
                .append("author", new DBRef("authors", authors.find(eq("fullName", "Эдгар Аллан По")).first().get("_id")))
                .append("genre", new DBRef("genres", genres.find(eq("name", "детектив")).first().get("_id")))
                .append("comments", new ArrayList<>()));
        books.insertMany(newBooks);
    }

    @ChangeSet(order = "005", id = "insertComments", author = "rina")
    public void insertComments(MongoDatabase db) {
        MongoCollection<Document> comments = db.getCollection("comments");
        MongoCollection<Document> books = db.getCollection("books");
        List<Document> newComments = new ArrayList<>();
        newComments.add(new Document().append("_id", "1")
                .append("text", "страшный")
                .append("book", new DBRef("books", books.find(eq("title", "Убийство на улице Морг")).first().get("_id"))));
        comments.insertMany(newComments);
    }

    @ChangeSet(order = "006", id = "insertBookComments", author = "rina")
    public void insertBookComments(MongoDatabase db) {
        MongoCollection<Document> comments = db.getCollection("comments");
        MongoCollection<Document> books = db.getCollection("books");

        Bson updates = Updates.addToSet("comments", comments.find(eq("text", "страшный")).first());
        books.updateOne(books.find(eq("title", "Убийство на улице Морг")).first(), updates, new UpdateOptions().upsert(true));
    }
}

