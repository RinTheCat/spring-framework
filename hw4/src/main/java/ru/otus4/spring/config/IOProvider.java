package ru.otus4.spring.config;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;

public interface IOProvider {
    InputStream getInputStream();
    PrintStream getPrintStream();
    Locale getCurrentLocale();
    void setPrintStream(PrintStream printStream); // плохо
}
