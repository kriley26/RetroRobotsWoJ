/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retrorobots.server.controllers;

import com.retrorobots.server.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 *
 * @author KeeganRiley
 */
@RestController
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

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
    
}
