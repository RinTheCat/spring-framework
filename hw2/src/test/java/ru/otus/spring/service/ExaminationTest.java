package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ExaminationTest {
    private IOService ioService;
    private Translator csvTranslator;
    private Examination examination;

    private final static InputStream USER_INPUT = new ByteArrayInputStream("Olga\na\nb\nc\nd\ne".getBytes());
    private final static List<Question> QUESTIONS = buildQuestions();
    private ByteArrayOutputStream programOutput = new ByteArrayOutputStream();

    @BeforeEach
    public void prepare() {
        this.ioService = new IOService(USER_INPUT, programOutput);
        this.csvTranslator = mock(CSVTranslator.class);
        this.examination = new Examination(csvTranslator,ioService);
    }

    @Test
    @DisplayName("Должен провести тест")
    public void shouldImplementExamination() throws IOException {
        given(csvTranslator.parse()).willReturn(QUESTIONS);
        examination.start();
    }

    private static List<Question> buildQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("1?","a","b","c","d","a"));
        questions.add(new Question("2?","a","b","c","d","b"));
        questions.add(new Question("3?","a","b","c","d","c"));
        questions.add(new Question("4?","a","b","c","d","d"));
        questions.add(new Question("5?","a","b","c","d","e"));
        return questions;
    }
}
