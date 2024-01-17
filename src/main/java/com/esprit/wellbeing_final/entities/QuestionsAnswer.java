package com.esprit.wellbeing_final.entities;

public class QuestionsAnswer {

    private Long id;
    private Long answerId;
    private String answerText;
    private int coef;

    public QuestionsAnswer(Long id, Long answerId, String answerText, int coef) {
        this.id = id;
        this.answerId = answerId;
        this.answerText = answerText;
        this.coef = coef;
    }

    public QuestionsAnswer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getCoef() {
        return coef;
    }

    public void setCoef(int coef) {
        this.coef = coef;
    }
}