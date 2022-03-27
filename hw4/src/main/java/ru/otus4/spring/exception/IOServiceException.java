package ru.otus4.spring.exception;

public class IOServiceException extends RuntimeException {

    public IOServiceException(String message) {
        super(message);
    }
}
