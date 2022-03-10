package ru.otus.spring.service;

import java.io.*;

public class IOService {
    private final BufferedReader reader;
    private final PrintStream writer;

    public IOService(InputStream inputStream, OutputStream outputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.writer = new PrintStream(outputStream);
    }

    public void showMessage(String message) {
        writer.println(message);
    }

    public String getResponse() throws IOException {
        return reader.readLine();
    }
}
