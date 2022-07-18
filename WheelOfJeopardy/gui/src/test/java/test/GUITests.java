package test;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.retrorobots.playerGUI.QuestionPanel;
import com.retrorobots.server.ServerApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nick
 */
public class GUITests {
    QuestionPanel testQuestionPanel;

    @BeforeEach
    void setUp() {
        testQuestionPanel = new QuestionPanel();
    }

    @Test
    void demoTestMethod() {
        assertTrue(true);
    }

    @Test
    void testDatabaseConnection() {
        boolean databaseConnectionTest = false;

        try {
            URL url = new URL("http://localhost:8080/question");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            System.out.println("test");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                System.out.println(inputLine);
                System.out.println(content);
            }
            if (inputLine != null) { databaseConnectionTest = true; }
            in.close();

            con.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(QuestionPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(QuestionPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(QuestionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertTrue(databaseConnectionTest == true);
    }
}
