package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.service.Examination;

import java.io.IOException;

@SpringBootApplication
public class OtusApplication {

	public static void main(String[] args) throws IOException {

		ApplicationContext context = SpringApplication.run(OtusApplication.class, args);

		Examination examination = context.getBean(Examination.class);
		examination.start();
	}

}
