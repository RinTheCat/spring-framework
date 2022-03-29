package ru.otus4.spring.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus4.spring.config.LocalizationProvider;
import ru.otus4.spring.domain.Question;
import ru.otus4.spring.exception.ReadFileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CSVTranslator implements Translator {

    private final LocalizationProvider localizationProvider;

    @Autowired
    public CSVTranslator(LocalizationProvider localizationProvider) {
        this.localizationProvider = localizationProvider;
    }

    @Override
    public List<Question> parse() {

        InputStream inputStream = null;

        try {
            inputStream = new ClassPathResource(localizationProvider.getLocalizedFilePath()).getInputStream();
        } catch (IOException exception) {
            throw new ReadFileException("Error during reading the file");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        return new CsvToBeanBuilder<Question>(reader)
                .withType(Question.class)
                .build()
                .parse();
    }

}