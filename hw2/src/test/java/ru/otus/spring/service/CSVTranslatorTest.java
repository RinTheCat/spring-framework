package ru.otus.spring.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CSVTranslatorTest {

    private Translator csvTranslator;

    @BeforeEach
    public void prepare() {
        this.csvTranslator = new CSVTranslator();
    }

    @Test
    @DisplayName("Должен парсить CSV на POJO")
    void shouldParseCsv() throws IOException {
        List<Question> actualResult = csvTranslator.parse();
        assertThat(actualResult.get(0)).isInstanceOf(Question.class);
//        assertThat(actualResult)
//                .usingRecursiveFieldByFieldElementComparator()
//                .containsExactlyInAnyOrderElementsOf();
    }
}
