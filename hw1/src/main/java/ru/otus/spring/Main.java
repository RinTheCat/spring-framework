package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.CSVTranslator;
import ru.otus.spring.domain.Question;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        CSVTranslator csvTranslator = context.getBean(CSVTranslator.class);

        for (Question question : csvTranslator.getQuestionList()) {
            System.out.println(question.getQuestion());
            System.out.println(question.getAnswer1() + "|" + question.getAnswer2() + "|"
                    + question.getAnswer3() + "|" + question.getAnswer4());
            System.out.println("The right answer is: " + question.getRightAnswer() + "\n");
        }

        context.close();
    }
}
