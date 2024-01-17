package com.esprit.wellbeing_final.entities;

public class Question {

    private Long id_question;
    private String questiontext;
    private String category;

    public Question(Long id_question, String questiontext, String category) {
        this.id_question = id_question;
        this.questiontext = questiontext;
        this.category = category;
    }

    public Question() {

    }

    public Long getId_question() {
        return id_question;
    }

    public void setId_question(Long id_question) {
        this.id_question = id_question;
    }

    public String getQuestiontext() {
        return questiontext;
    }

    public void setQuestiontext(String questiontext) {
        this.questiontext = questiontext;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}