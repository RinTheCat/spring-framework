package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;

public class IOServiceTest {

    private IOService ioService;
    private ByteArrayOutputStream programOutput = new ByteArrayOutputStream();

    private final static InputStream USER_INPUT = new ByteArrayInputStream("Hello, world!".getBytes());

    @BeforeEach
    public void prepare() {
        this.ioService = new IOService(USER_INPUT, programOutput);
    }

    @Test
    @DisplayName("Должен выводить требуемое сообщение")
    void shouldOutputCorrectMessage() {
        String message = "Hello, world!";
        ioService.showMessage(message);
        assertEquals(message + "\r\n", programOutput.toString());
    }

    @Test
    @DisplayName("Должен правильно читать сообщение")
    void shouldReadInputCorrectly() throws IOException {
        String message = ioService.getResponse();
        assertEquals("Hello, world!", message);
    }
}
