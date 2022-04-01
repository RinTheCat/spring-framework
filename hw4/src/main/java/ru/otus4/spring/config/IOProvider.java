package ru.otus4.spring.config;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Locale;

public interface IOProvider {
    BufferedReader getBufferedReader();
    PrintStream getPrintStream();
    Locale getCurrentLocale();
}
