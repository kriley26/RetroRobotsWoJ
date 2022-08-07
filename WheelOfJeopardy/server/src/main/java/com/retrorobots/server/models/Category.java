package com.retrorobots.server.models;

import java.util.List;

public class Category {

    private List<Question> questions;
    private String categoryName;
    private int questionNumber = 0;

    public Category() { }

    public Category(String name, List<Question> q) {
        this.categoryName = name;
        this.questions = q;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void setQuestions(List<Question> qs) {
        questions = qs;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Question getNextQuestion(){
        return this.questions.get(questionNumber++);
    }

    public void setCategoryName(String name) {
        categoryName = name;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
