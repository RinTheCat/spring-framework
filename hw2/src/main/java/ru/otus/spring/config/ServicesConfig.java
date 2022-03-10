package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.IOService;

@Configuration
public class ServicesConfig {
    @Bean
    public IOService ioService() {
        return new IOService(System.in, System.out);
    }
}
