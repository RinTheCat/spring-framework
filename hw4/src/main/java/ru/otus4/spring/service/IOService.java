package ru.otus4.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus4.spring.config.IOProvider;
import ru.otus4.spring.exception.IOServiceException;

import java.io.*;

@Service
public class IOService {
    private final IOProvider ioProvider;
    private final MessageSource messageSource;

    @Autowired
    public IOService(IOProvider ioProvider, MessageSource messageSource) {
        this.ioProvider = ioProvider;
        this.messageSource = messageSource;
    }

    public void showMessage(String message) {
        ioProvider.getPrintStream().println(message);
    }

    public void showMessage(String messageName, String[] values) {
        ioProvider.getPrintStream().println(messageSource.getMessage(messageName, values, ioProvider.getCurrentLocale()));
    }

    public String getResponse() {
        try {
            return ioProvider.getBufferedReader().readLine();
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }
}
