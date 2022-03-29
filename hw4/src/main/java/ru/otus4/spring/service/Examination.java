package ru.otus4.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus4.spring.domain.Question;


@ShellComponent
@ConfigurationProperties(prefix = "exam")
public class Examination {

    private int minPoints;

    private final Translator translator;
    private final IOService ioService;
    private final Register register;

    @Autowired
    public Examination(Translator translator, IOService ioService, Register register) {
        this.translator = translator;
        this.ioService = ioService;
        this.register = register;
    }

    @ShellMethod(value="Start the examination", key={"test","t"})
    @ShellMethodAvailability(value="isUserRegistered")
    public void start() {
        int score = 0;

        for (Question question : translator.parse()) {
            ioService.showMessage(question.getQuestion());
            ioService.showMessage(question.getAnswer1() + " | " + question.getAnswer2() +
                    " | " + question.getAnswer3() + " | " + question.getAnswer4());
            ioService.showMessage("task", null);
            String answer = ioService.getResponse();
            if (answer.toLowerCase().equals(question.getRightAnswer().toLowerCase())) score++;
        }
        ioService.showMessage("score", new String[]{register.getCurrentUser().getName(), String.valueOf(score)});
        String msg = score >= minPoints ? "positiveResult" : "negativeResult";
        ioService.showMessage(msg, null);
    }

    public void setMinPoints(int minPoints) {
        this.minPoints = minPoints;
    }

    public Availability isUserRegistered() {
        return register.getCurrentUser() == null ? Availability.unavailable("Тест доступен " +
                "только зарегестрированным студентам") : Availability.available();
    }
}