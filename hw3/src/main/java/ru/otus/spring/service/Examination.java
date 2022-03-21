package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.io.IOException;

@Service
@ConfigurationProperties(prefix = "exam")
public class Examination {

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
        ioService.showMessage("m1", null);
        String fullName = ioService.getResponse();

        for (Question question : translator.parse()) {
            ioService.showMessage(question.getQuestion());
            ioService.showMessage(question.getAnswer1() + " | " + question.getAnswer2() +
                    " | " + question.getAnswer3() + " | " + question.getAnswer4());
            ioService.showMessage("m2", null);
            String answer = ioService.getResponse();
            if (answer.toLowerCase().equals(question.getRightAnswer().toLowerCase())) score++;
        }
        ioService.showMessage("m3", new String[]{fullName, String.valueOf(score)});
        String msg = score >= minPoints ? "m4" : "m5";
        ioService.showMessage(msg, null);
    }

    public void setMinPoints(int minPoints) {
        this.minPoints = minPoints;
    }
}
