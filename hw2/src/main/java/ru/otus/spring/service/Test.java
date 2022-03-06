package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.Scanner;

public class Test {

    @Value("${minPoints}")
    private int minPoints;
    private final Translator translator;

    @Autowired
    public Test(Translator translator) {
        this.translator = translator;
    }

    public void start() throws IOException {
        int score = 0;
        System.out.println("Introduce yourself");
        Scanner scanner = new Scanner(System.in);
        String fullName = scanner.next();

        for (Question question : translator.parse()) {
            System.out.println(question.getQuestion());
            System.out.println(question.getAnswer1() + " | " + question.getAnswer2() +
                    " | " + question.getAnswer3() + " | " + question.getAnswer4());
            System.out.println("Write the most suitable answer: ");
            String answer = scanner.next();
            if (answer.toLowerCase().equals(question.getRightAnswer().toLowerCase())) score++;
        }
        System.out.printf("%s's score: %d\n", fullName, score);
        String msg = score >= minPoints ? "Passed!" : "Try again.";
        System.out.println(msg);
    }
}
