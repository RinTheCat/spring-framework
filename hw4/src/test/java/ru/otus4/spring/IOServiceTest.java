package ru.otus4.spring;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus4.spring.config.AppConfig;
import ru.otus4.spring.service.IOService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class IOServiceTest {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String TEST_MESSAGE = "Hello world!";
    private ByteArrayOutputStream arrayOutputStream;

    @MockBean
    private MessageSource messageSource;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private IOService ioService;

    @BeforeEach
    void prepare() {
        this.arrayOutputStream = new ByteArrayOutputStream();
        ReflectionTestUtils.setField(appConfig, "printStream", new PrintStream(arrayOutputStream));
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
        given(messageSource.getMessage("testMessage", null, new Locale("ru"))).willReturn(TEST_MESSAGE);
        ioService.showMessage("testMessage",null);
        assertThat(arrayOutputStream.toString()).isEqualTo(TEST_MESSAGE + LINE_SEPARATOR);
    }

}
