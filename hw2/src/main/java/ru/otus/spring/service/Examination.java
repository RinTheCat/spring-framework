package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.io.IOException;

@Service
public class Examination {

    @Value("${minPoints}")
    private int minPoints;
    private final Translator translator;
    private final IOService ioService;

    @Autowired
    public Examination(Translator translator, IOService ioService) {
        this.translator = translator;
        this.ioService = ioService;
    }

    public void start() throws IOException {
        int score = 0;
        ioService.showMessage("Introduce yourself");
        String fullName = ioService.getResponse();

        for (Question question : translator.parse()) {
            ioService.showMessage(question.getQuestion());
            ioService.showMessage(question.getAnswer1() + " | " + question.getAnswer2() +
                    " | " + question.getAnswer3() + " | " + question.getAnswer4());
            ioService.showMessage("Write the most suitable answer: ");
            String answer = ioService.getResponse();
            if (answer.toLowerCase().equals(question.getRightAnswer().toLowerCase())) score++;
        }
        ioService.showMessage(fullName + "'s score: " + score);
        String msg = score >= minPoints ? "Passed!" : "Try again.";
        ioService.showMessage(msg);
    }
}
