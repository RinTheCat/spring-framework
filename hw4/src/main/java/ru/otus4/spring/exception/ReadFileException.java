package ru.otus4.spring.exception;

public class ReadFileException extends RuntimeException {

    public ReadFileException(String message) {
        super(message);
    }
}
