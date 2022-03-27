package ru.otus4.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus4.spring.domain.Question;
import ru.otus4.spring.service.CSVTranslator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CSVTranslatorTest {

    @Autowired
    private CSVTranslator csvTranslator;

    @Test
    @DisplayName("Должен парсить CSV на POJO")
    void shouldParseCsv() {
        List<Question> actualResult = csvTranslator.parse();
        assertThat(actualResult.get(0)).isInstanceOf(Question.class);
    }
}
