package ru.otus.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import ru.otus.spring.service.IOService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@EnableAutoConfiguration
public class IOServiceTest {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String TEST_MESSAGE = "Hello world!";

    private ByteArrayOutputStream arrayOutputStream;
    private IOService ioService;
    private MessageSource messageSource;

    @BeforeEach
    void prepare() {
        this.messageSource = mock(MessageSource.class);
        arrayOutputStream = new ByteArrayOutputStream();
        ioService = new IOService(System.in, new PrintStream(arrayOutputStream), messageSource);
        ioService.setLocale(new Locale("en"));
    }

    @Test
    @DisplayName("Должен выводить требуемое сообщение" + TEST_MESSAGE)
    public void shouldPrintMessage() {
        ioService.showMessage(TEST_MESSAGE);
        assertThat(arrayOutputStream.toString()).isEqualTo(TEST_MESSAGE + LINE_SEPARATOR);
    }

    @Test
    @DisplayName("Должен выводить требуемое сообщение из messageSource")
    public void shouldPrintMessageWithNullParam() {
        given(messageSource.getMessage("testMessage", null, new Locale("en"))).willReturn(TEST_MESSAGE);

        ioService.showMessage("testMessage",null);
        assertThat(arrayOutputStream.toString()).isEqualTo(TEST_MESSAGE + LINE_SEPARATOR);
    }
}
