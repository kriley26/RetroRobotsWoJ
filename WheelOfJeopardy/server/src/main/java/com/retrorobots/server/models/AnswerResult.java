package com.retrorobots.server.models;

import com.retrorobots.server.wofj.Game;

public class AnswerResult {

    private boolean result;
    private Game game;

    public AnswerResult() {
        game = null;
        result = false;
    }

    public AnswerResult(Game g, boolean r) {
        this.game = g;
        this.result = r;
    }

    private void setGame(Game g) {
        this.game = g;
    }

    private void setResult(boolean r) {
        this.result = r;
    }

    public Game getGame() {
        return game;
    }

    public boolean isResult() {
        return result;
    }

    public static void main(String[] args) {

    }
}
