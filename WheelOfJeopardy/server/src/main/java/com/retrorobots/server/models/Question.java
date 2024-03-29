/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retrorobots.server.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KeeganRiley
 */
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int show_number;
    private String air_date;
    private String round;
    private String category;
    private String value;
    private String question;
    private String answer;

    @Transient
    private int questionNumber;

    @Transient
    private List<String> wrongAns;

    public Question() {
        this(0, 0, null, null, null, null, null, null);
    }

    public Question(int id, int show_number, String air_date, String round, String category, String value, String question, String answer) {
        this.id = id;
        this.show_number = show_number;
        this.air_date = air_date;
        this.round = round;
        this.category = category;
        this.value = value;
        this.question = question;
        this.answer = answer;
        this.wrongAns = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShow_number() {
        return show_number;
    }

    public void setShow_number(int show_number) {
        this.show_number = show_number;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public List<String> getWrongAns() {
        return wrongAns;
    }

    public void setWrongAns(List<String> wrongAns) {
        this.wrongAns = wrongAns;
    }

    public void addWrongAns(String ans) {
        this.wrongAns.add(ans);
    }

    public boolean isStringInWrongAns(String an) {
        for (String s : wrongAns) {
            if (s.equals(an)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return getQuestion() + " " + getQuestionNumber() + " " + getCategory();
    }
}
