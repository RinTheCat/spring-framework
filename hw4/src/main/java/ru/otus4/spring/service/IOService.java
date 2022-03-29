package ru.otus4.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus4.spring.config.IOProvider;
import ru.otus4.spring.exception.IOServiceException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class IOService {
    private final BufferedReader reader;
    private final PrintStream writer;
    private final MessageSource messageSource;
    private final Locale locale;

    @Autowired
    public IOService(IOProvider ioProvider, MessageSource messageSource) {
        this.reader = new BufferedReader(new InputStreamReader(ioProvider.getInputStream(), StandardCharsets.UTF_8));
        this.writer = ioProvider.getPrintStream();
        this.messageSource = messageSource;
        this.locale = ioProvider.getCurrentLocale(); // сомнительно ли сохранять состояние
    }

    public void showMessage(String message) {
        writer.println(message);
    }

    public void showMessage(String messageName, String[] values) {
        writer.println(messageSource.getMessage(messageName, values, locale));
    }

    public String getResponse() {
        try {
            return reader.readLine();
        } catch (IOException exception) {
            throw new IOServiceException("Error during reading from console");
        }
    }
}
