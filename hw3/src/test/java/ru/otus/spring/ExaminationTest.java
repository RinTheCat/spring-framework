package ru.otus.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.CSVTranslator;
import ru.otus.spring.service.Examination;
import ru.otus.spring.service.IOService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@EnableAutoConfiguration
public class ExaminationTest {

    private Examination examination;
    private final static List<Question> QUESTIONS = buildQuestions();

    @MockBean
    private CSVTranslator csvTranslator;
    @MockBean
    private IOService ioService;

    @BeforeEach
    void prepare() {
        this.examination = new Examination(csvTranslator, ioService);
    }

    @Test
    @DisplayName("Должен провести тест")
    public void shouldImplementExamination() throws IOException {
        given(csvTranslator.parse()).willReturn(QUESTIONS);
        given(ioService.getResponse()).willReturn("a");
        examination.start();
    }

    private static List<Question> buildQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("1?","a","b","c","d","a"));
        questions.add(new Question("2?","a","b","c","d","a"));
        return questions;
    }

}
