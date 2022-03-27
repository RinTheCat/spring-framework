package ru.otus4.spring.domain;

import com.opencsv.bean.CsvBindByPosition;

public class Question {

    @CsvBindByPosition(position = 0)
    private String question;

    @CsvBindByPosition(position = 1)
    private String answer1;

    @CsvBindByPosition(position = 2)
    private String answer2;

    @CsvBindByPosition(position = 3)
    private String answer3;

    @CsvBindByPosition(position = 4)
    private String answer4;

    @CsvBindByPosition(position = 5)
    private String rightAnswer;

    public Question() {
    }

    public Question(String question, String answer1, String answer2, String answer3, String answer4, String rightAnswer) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.rightAnswer = rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }
}
