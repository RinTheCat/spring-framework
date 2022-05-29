package ru.otus.nosql;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.nosql.repository.AuthorRepository;

@EnableMongock
@SpringBootApplication
@EnableMongoRepositories(basePackages = "ru.otus.nosql.repository")
public class Hw8Application {

	public static void main(String[] args) {
		SpringApplication.run(Hw8Application.class, args);
	}

}
