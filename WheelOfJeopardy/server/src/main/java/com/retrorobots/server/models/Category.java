package com.retrorobots.server.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Category {

    private List<Question> questions;
    private Question currQuestion;
    private String categoryName;
    private int questionCounter;
    private int round;

    public Category() { }

    public Category(String name, List<Question> q) {
        this(0, name, q);
    }

    public Category(int round, String name, List<Question> q) {
        this.round = round;
        this.categoryName = name;
        this.questions = new ArrayList<>();
        this.questionCounter = 0;

        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            int max = q.size();
            int int_rand = rand.nextInt(max);
            Question quest = q.remove(int_rand);
            int cashInt = (i+1) * (200*round);
            String cash = "$"+cashInt;
            quest.setValue(cash);
            quest.setQuestionNumber(i);
            this.questions.add(i, quest);
        }
        this.currQuestion = this.questions.get(this.questionCounter++);
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

    public void getNextQuestion(){
        this.currQuestion = this.questions.get(questionCounter++);
    }

    public void setCategoryName(String name) {
        categoryName = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getQuestionCounter() {
        return questionCounter;
    }

    public void setQuestionCounter(int questionCounter) {
        this.questionCounter = questionCounter;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Question getCurrQuestion() {
        return currQuestion;
    }

    public void setCurrQuestion(Question currQuestion) {
        this.currQuestion = currQuestion;
    }
}
