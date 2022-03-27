package ru.otus4.spring.service;

import ru.otus4.spring.domain.Question;

import java.util.List;

public interface Translator {
    List<Question> parse();
}