package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class IOService {
    private final BufferedReader reader;
    private final PrintStream writer;
    private final MessageSource messageSource;

    @Value("#{ systemProperties['user.country'] }")
    private Locale locale;

    @Autowired
    public IOService(@Value("#{ T(java.lang.System).in }") final InputStream inputStream,
                     @Value("#{ T(java.lang.System).out }") final PrintStream printStream,
                     MessageSource messageSource) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        this.writer = printStream;
        this.messageSource = messageSource;
    }

    public void showMessage(String message) {
        writer.println(message);
    }

    public void showMessage(String messageNumber, String[] values) {
        writer.println(messageSource.getMessage(messageNumber, values, locale));
    }

    public String getResponse() throws IOException {
        return reader.readLine();
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
