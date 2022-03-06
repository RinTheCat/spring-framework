package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.Test;

import java.io.IOException;

@Configuration
@ComponentScan
public class Main {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        Test test = context.getBean(Test.class);
        test.start();
    }
}
