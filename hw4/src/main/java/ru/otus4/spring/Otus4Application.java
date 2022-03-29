package ru.otus4.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus4.spring.service.Examination;


@SpringBootApplication
public class Otus4Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Otus4Application.class, args);

		Examination examination = context.getBean(Examination.class);
		examination.start();
	}

}
