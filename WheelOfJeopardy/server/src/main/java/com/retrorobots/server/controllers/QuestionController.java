/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retrorobots.server.controllers;

import com.retrorobots.server.models.Category;
import com.retrorobots.server.models.Question;
import com.retrorobots.server.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author KeeganRiley
 */
@RestController
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    private String q1 = "What is for lunch?";

    @RequestMapping("/lunch")
    public String lunch() {
        return q1;
    }

    @RequestMapping("/question")
    public String getNewQuestion() {
        Random rand = new Random();
        int upperbound = 1000;
        int int_random = rand.nextInt(upperbound);
        return questionRepository.getReferenceById(int_random).toString();
    }

    @RequestMapping("/categories")
    public List<String> getCategories() {
        List<String> list = questionRepository.findDistinctCategory();
        List<String> currentCats = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        Random rand = new Random();

        System.out.println();
        while (categories.size() < 5) {
            int upperbound = list.size();
            int int_random = rand.nextInt(upperbound);
            List<Question> tempList;

            System.out.println(int_random);
            String category = list.remove(int_random);
            tempList = validateCategory(category);
            if (tempList.size() >= 5) {
                //currentCats.add(category);
                categories.add(new Category(category, tempList));
            }
        }

//        for(String cat : currentCats) {
//            System.out.println(cat);
//            List<Question> questions = this.questionRepository.findCategoryQuestions(cat);
//            Category newCat = new Category(cat, questions);
//            categories.add(newCat);
//        }

        for(Category c : categories) {
            System.out.println(c.getCategoryName() + " Questions: " + c.getQuestions().size());
            currentCats.add(c.getCategoryName());
        }


        return currentCats;
    }

    private List<Question> validateCategory(String category) {
        return this.questionRepository.findCategoryQuestions(category);
    }
    
}
