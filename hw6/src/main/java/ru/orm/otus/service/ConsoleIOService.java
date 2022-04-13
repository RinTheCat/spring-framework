package ru.orm.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.orm.otus.domain.Author;
import ru.orm.otus.domain.Book;
import ru.orm.otus.domain.Comment;
import ru.orm.otus.domain.Genre;
import ru.orm.otus.exeption.IOServiceException;

import java.io.*;
import java.nio.charset.StandardCharsets;

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
    public long getAuthorId() {
        try {
            printStream.println("Введите id автора");
            return Long.parseLong(bufferedReader.readLine());
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public long getBookId() {
        try {
            printStream.println("Введите id книги");
            return Long.parseLong(bufferedReader.readLine());
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public long getGenreId() {
        try {
            printStream.println("Введите id жанра");
            return Long.parseLong(bufferedReader.readLine());
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public long getCommentId() {
        try {
            printStream.println("Введите id комментария");
            return Long.parseLong(bufferedReader.readLine());
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public Book getBook() {
        try {
            printStream.println("Введите название книги");
            String title = bufferedReader.readLine();
            return new Book(0, title, null, null, null);
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public Author getAuthor() {
        try {
            printStream.println("Введите имя автора");
            String fullName = bufferedReader.readLine();
            return new Author(0, fullName, null);
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public Genre getGenre() {
        try {
            printStream.println("Введите название жанра");
            String name = bufferedReader.readLine();
            return new Genre(0, name, null);
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public Comment getComment() {
        try {
            printStream.println("Введите комментарий");
            String text = bufferedReader.readLine();
            return new Comment(0, text, null);
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
