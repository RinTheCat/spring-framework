package ru.otus.spring.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.domain.Question;

import java.io.*;
import java.util.List;

public class CSVTranslator {

    private final List<Question> questionList;

    public CSVTranslator(String filePath) throws IOException {

        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        questionList = new CsvToBeanBuilder<Question>(reader)
                .withType(Question.class)
                .build()
                .parse();
    }

    public List<Question> getQuestionList() {
        return questionList;
    }
}
