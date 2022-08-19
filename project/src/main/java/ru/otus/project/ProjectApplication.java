package ru.otus.project;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.project.service.OrderService;

import java.sql.SQLException;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) throws SQLException {
		var c = SpringApplication.run(ProjectApplication.class, args);

		var orderService = c.getBean(OrderService.class);
		System.out.println("count: " + orderService.count());
		System.out.println("first: " + orderService.getById(1L).toString());
		orderService.pushStatus(1L);
		System.out.println("withPushedStatus: " + orderService.getById(1L).toString());
		orderService.pullStatus(1L);
		System.out.println("withPulledStatus: " + orderService.getById(1L).toString());

		Console.main(args);
	}

}
