package ru.otus4.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component
public class AppConfig implements IOProvider, LocalizationProvider {

    private final Locale userLocale;
    private final BufferedReader bufferedReader;

    private final PrintStream printStream;
    private final String questionFilePath;
    private final String questionFileExtension;

    public AppConfig(@Value("#{ systemProperties['user.country'] }") Locale userLocale,
                     @Value("#{ T(java.lang.System).in }") InputStream inputStream,
                     @Value("#{ T(java.lang.System).out }") PrintStream printStream,
                     @Value("${csv.file-name}") String questionFilePath,
                     @Value("${csv.file-extension}") String questionFileExtension) {
        this.userLocale = userLocale;
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        this.printStream = printStream;
        this.questionFilePath = questionFilePath;
        this.questionFileExtension = questionFileExtension;
    }

    @Override
    public Locale getCurrentLocale() {
        return userLocale;
    }

    @Override
    public String getLocalizedFilePath() {
        return questionFilePath + "-" + userLocale.getLanguage() + "." + questionFileExtension;
    }

    @Override
    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    @Override
    public PrintStream getPrintStream() {
        return printStream;
    }
}
