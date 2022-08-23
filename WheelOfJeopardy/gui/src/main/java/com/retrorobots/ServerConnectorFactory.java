package com.retrorobots;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class ServerConnectorFactory {

    public static String MAIN_PATH = "http://localhost:8080";

    // game controller mappings
    public static String START_GAME_PATH = "/startGame/roundOne";
    public static String ROUND_TWO_PATH = "/startGame/roundTwo";
    public static String CURRENT_PLAYER_PATH = "/currentPlayer";
    public static String GET_QUESTION_PATH = "/getQuestion";
    public static String VERIFY_ANSWER_PATH = "/verifyAnswer";
    public static String FREE_TURN_PATH = "/freeTurn";
    public static String CHECK_PLAYER_FREE_TOKEN = "/freeTurn/player";
    public static String USE_FREE_TOKEN = "/freeTurn/useToken";
    public static String LOSE_TURN_PATH = "/loseTurn";
    public static String BANKRUPT_PATH = "/bankrupt";
    public static String OPPONENTS_CHOICE_PLAYER_PATH = "/opponentsChoice/player";

    // question controller mappings
    public static String QUESTION_PATH = "/question";
    public static String CATEGORIES_PATH = "/categories";

    public static String queryServer(String request) {
        try {
            if (request.contains(" ")) {
                request = request.replace(" ", "%20");
            }
            if (request.contains("\"")) {
                request = request.replace("\"", "%22");
            }
            if (request.contains("&")) {
                request = request.replace("&", "\u0026");
            }
            URL url = new URL(MAIN_PATH+request);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
