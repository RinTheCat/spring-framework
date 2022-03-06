package ru.otus.spring.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.domain.Question;

import java.io.*;
import java.util.List;

public class CSVTranslator implements Translator {

    @Value("${filePath}")
    private String filePath;

    @Override
    public List<Question> parse() throws IOException {

        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        return new CsvToBeanBuilder<Question>(reader)
                .withType(Question.class)
                .build()
                .parse();
    }
}
