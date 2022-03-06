package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.CSVTranslator;
import ru.otus.spring.service.Test;
import ru.otus.spring.service.Translator;

@PropertySource("classpath:application.properties")
@Configuration
public class ServicesConfig {

    @Bean
    Translator csvTranslator() {
        return new CSVTranslator();
    }

    @Bean
    Test test(Translator translator) {
        return new Test(translator);
    }
}
