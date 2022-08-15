package ru.otus.docker.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.docker.repository.BookRepository;

@Component
public class DbHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Autowired
    public DbHealthIndicator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Health health() {
        try {
            bookRepository.count();
        } catch (Exception e) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Something is wrong with db!")
                    .build();
        }
        return Health.up().withDetail("message", "Cool!").build();
    }
}
