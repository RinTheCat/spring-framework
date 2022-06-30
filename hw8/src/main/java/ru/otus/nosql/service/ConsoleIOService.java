package ru.otus.nosql.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.nosql.domain.Author;
import ru.otus.nosql.domain.Book;
import ru.otus.nosql.domain.Comment;
import ru.otus.nosql.domain.Genre;
import ru.otus.nosql.exception.IOServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service
public class ConsoleIOService implements IOService {

    private final BufferedReader bufferedReader;
    private final PrintStream printStream;

    public ConsoleIOService(@Value("#{ T(java.lang.System).in }") InputStream inputStream,
                            @Value("#{ T(java.lang.System).out }") PrintStream printStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        this.printStream = printStream;
    }

    @Override
    public String getAuthorId() {
        try {
            printStream.println("Введите id автора");
            return bufferedReader.readLine();
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public String getBookId() {
        try {
            printStream.println("Введите id книги");
            return bufferedReader.readLine();
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public String getGenreId() {
        try {
            printStream.println("Введите id жанра");
            return bufferedReader.readLine();
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public String getCommentId() {
        try {
            printStream.println("Введите id комментария");
            return bufferedReader.readLine();
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public Book getBook() {
        try {
            printStream.println("Введите название книги");
            String title = bufferedReader.readLine();
            return new Book(null, title, null, null, new ArrayList<>());
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public Author getAuthor() {
        try {
            printStream.println("Введите имя автора");
            String fullName = bufferedReader.readLine();
            return new Author(null, fullName);
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public Genre getGenre() {
        try {
            printStream.println("Введите название жанра");
            String name = bufferedReader.readLine();
            return new Genre(null, name);
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public Comment getComment() {
        try {
            printStream.println("Введите комментарий");
            String text = bufferedReader.readLine();
            return new Comment(null, text, null);
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public String getNewInfo() {
        try {
            printStream.println("Введите информацию для поиска/обновления");
            return bufferedReader.readLine();
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }
}

