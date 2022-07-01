/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retrorobots.server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author KeeganRiley
 */
@RestController
public class QuestionController {

    private String q1 = "What is for lunch?";

    @RequestMapping("/lunch")
    public String lunch() {
        return q1;
    }
    
}
