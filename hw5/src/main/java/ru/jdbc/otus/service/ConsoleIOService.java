package ru.jdbc.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.jdbc.otus.domain.Author;
import ru.jdbc.otus.domain.Book;
import ru.jdbc.otus.domain.Genre;
import ru.jdbc.otus.exeption.IOServiceException;

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
    public long getId() {
        try {
            printStream.println("Введите id");
            return Long.parseLong(bufferedReader.readLine());
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public Book getBook() {
        try {
            printStream.println("Введите id");
            long id = 0;
            id = Long.parseLong(bufferedReader.readLine());

            printStream.println("Введите название");
            String title = bufferedReader.readLine();

            printStream.println("Введите id автора");
            long authorId = Long.parseLong(bufferedReader.readLine());

            printStream.println("Введите id жанра");
            long genreId = Long.parseLong(bufferedReader.readLine());

            return new Book(id, title, new Author(authorId, null), new Genre(genreId, null));
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public Author getAuthor() {
        try {
            printStream.println("Введите id");
            long id = Long.parseLong(bufferedReader.readLine());

            printStream.println("Введите имя автора");
            String fullName = bufferedReader.readLine();

            return new Author(id, fullName);
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }

    @Override
    public Genre getGenre() {
        try {
            printStream.println("Введите id");
            long id = Long.parseLong(bufferedReader.readLine());

            printStream.println("Введите название нового жанра");
            String name = bufferedReader.readLine();

            return new Genre(id, name);
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }
}
