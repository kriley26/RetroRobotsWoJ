package com.retrorobots.server.controllers;

import com.retrorobots.server.models.Category;
import com.retrorobots.server.models.Question;
import com.retrorobots.server.wofj.Game;
import com.retrorobots.server.wofj.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class GameController {

    private QuestionController qc = new QuestionController();
    private Game g;

    @RequestMapping("/startGame")
    private Game startGame(@RequestParam int pCount) throws IOException {
        List<Category> categories = new ArrayList<>();
        g = new Game();

        // get categories and questions
        URL url = new URL("http://localhost:8080/categories");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        // JSONify the string data. Parse JSON data into a list of Categories housing a list of Questions
        JSONArray catArray = new JSONArray(content.toString());
        for (int i = 0; i < catArray.length(); i++) {
            JSONObject catObj = catArray.getJSONObject(i);
            String catName = catObj.getString("categoryName");

            JSONArray questArr = catObj.getJSONArray("questions");
            List<Question> quests = new ArrayList<>();
            for (int j = 0; j < questArr.length(); j++) {
                JSONObject questObj = questArr.getJSONObject(j);
                int id = questObj.getInt("id");
                int show_number = questObj.getInt("show_number");
                String air_date = questObj.getString("air_date");
                String round = questObj.getString("round");
                String category = questObj.getString("category");
                String value = questObj.getString("value");
                String question = questObj.getString("question");
                String answer = questObj.getString("answer");

                quests.add(new Question(id, show_number, air_date, round, category, value, question, answer));
            }
            categories.add(new Category(catName, quests));
        }

        // add categories and questions to game
        for(Category c: categories){
            g.addCategory(c);
        }
        // create players and add them to game
        //TODO to be updated with player's choice
        List<Player> playerList = new ArrayList<Player>();
        for (int i = 0; i < pCount; i++) {
            playerList.add(new Player("Player " + (i+1)));
        }
        g.addPlayerList(playerList);
        g.initRound();

        return g;
    }

    @RequestMapping("/currentPlayer")
    public String currentPlayer(){
        return g.getCurrPlayer().getName();
    }

    @RequestMapping("/getQuestion")
    public Question getQuestion(@RequestParam String cat) {
        return g.queryQ(cat);
    }

    @RequestMapping("/verifyAnswer")
    public String verifyAnswer() {
        String answer = "";
        boolean correct = g.answerQuestion(answer);
        if (correct) {
            return g.getCurrPlayer().getName();
        } else {
            Player player = g.nextPlayer();
            g.setCurrPlayer(player);
            return player.getName();
        }
    }

    @RequestMapping("/freeTurn")
    public void freeTurn(){
        g.getCurrPlayer().addToken();
    }

    @RequestMapping("/loseTurn")
    public String loseTurn() {
        Player player = g.nextPlayer();
        g.setCurrPlayer(player);
        return player.getName();
    }

    @RequestMapping("/bankrupt")
    public String bankrupt() {
        g.getCurrPlayer().setScore(0);
        return loseTurn();
    }

    @RequestMapping("/opponentsChoice/player")
    public String oppoChoice() {
        return g.nextPlayer().getName();
    }

    @RequestMapping("/opponentsChoice/question")
    public String oppoChoiceQ() {
    // TODO
        return "";
    }
}
