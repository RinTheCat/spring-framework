package ru.otus.spring.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

@Service
@ConfigurationProperties(prefix = "csv")
public class CSVTranslator implements Translator {

    @Value("#{ systemProperties['user.country'] }")
    private Locale locale;

    private String fileExtension;
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

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath + "-" + locale + "." + fileExtension;
    }
}
