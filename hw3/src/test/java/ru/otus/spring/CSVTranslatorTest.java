package ru.otus.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.CSVTranslator;
import ru.otus.spring.service.Translator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableAutoConfiguration
public class CSVTranslatorTest {

    private final static List<Question> QUESTIONS = buildQuestions();

    @Configuration
    public static class CSVTranslatorConfig {
        @Bean
        public Translator csvTranslator() {
            return new CSVTranslator();
        }
    }

    @Autowired
    private Translator csvTranslator;

    @Test
    @DisplayName("Должен парсить CSV на POJO")
    void shouldParseCsv() throws IOException {
        List<Question> actualResult = csvTranslator.parse();
        assertThat(actualResult.get(0)).isInstanceOf(Question.class);
//        assertThat(actualResult)
//                .usingRecursiveFieldByFieldElementComparator()
//                .containsExactlyInAnyOrderElementsOf(QUESTIONS);
    }

    private static List<Question> buildQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Столица Британии","Уэльс",
                "Лондон","Англия","Париж","Лондон"));
        return questions;
    }
}
