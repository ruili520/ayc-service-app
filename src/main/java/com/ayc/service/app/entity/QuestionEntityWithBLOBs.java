package com.ayc.service.app.entity;

import java.io.Serializable;

public class QuestionEntityWithBLOBs extends QuestionEntity implements Serializable {

    private static final long serialVersionUID = 5461900910180433683L;

    private String question;

    private String answer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }
}