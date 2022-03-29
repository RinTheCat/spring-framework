package ru.otus4.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import ru.otus4.spring.domain.Question;
import ru.otus4.spring.domain.User;
import ru.otus4.spring.service.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
public class ExaminationTest {

    @MockBean
    private Shell shell;  // если не сделать мок - ошибка
    @MockBean
    private CSVTranslator csvTranslator;
    @MockBean
    private IOService ioService;
    @MockBean
    private Register register;
    @Autowired
    private Examination examination;

    private final static List<Question> QUESTIONS = buildQuestions();

    @Test
    @DisplayName("Должен провести тест")
    public void shouldImplementExamination() {
        given(csvTranslator.parse()).willReturn(QUESTIONS);
        given(ioService.getResponse()).willReturn("a");
        given(register.getCurrentUser()).willReturn(new User("Oleg"));
        examination.start();
    }

    private static List<Question> buildQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("1?","a","b","c","d","a"));
        questions.add(new Question("2?","a","b","c","d","a"));
        return questions;
    }

}
