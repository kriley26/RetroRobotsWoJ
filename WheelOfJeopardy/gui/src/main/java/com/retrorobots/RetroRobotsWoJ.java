/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retrorobots;

import com.retrorobots.mainGameGUI.MainWindow;
import com.retrorobots.server.ServerApplication;
import org.springframework.boot.SpringApplication;

/**
 *
 * @author KeeganRiley
 */
public class RetroRobotsWoJ {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        SpringApplication.run(ServerApplication.class, args);
    }
    
}
