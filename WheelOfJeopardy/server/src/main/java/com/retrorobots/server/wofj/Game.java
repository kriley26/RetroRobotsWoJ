/**
 * Class Game for Skeletal Demo
 * Will be updated after Database/server implemented
 * Features:
 * 1. 
 * 2. 
 * 3. 
 * 4. 
 * 5. 
 * 
 * @author WenjunZhou
 */

package com.retrorobots.server.wofj;

import com.retrorobots.server.models.Category;
import com.retrorobots.server.models.Question;

import java.util.ArrayList;
import java.util.List;

public class Game {
	public List<Player> playerList;
	public List<Category> categoryList;
	private int currRound = 0;
	private Player currPlayer;
	private Question currQuestion;
	private int spinCount = 0;
	private int quesCount = 0;

	public int getSpinCount() {
		return spinCount;
	}

	public int getQuesCount() {
		return quesCount;
	}

	public Game(){
		playerList = new ArrayList<Player>();
		categoryList = new ArrayList<Category>();
	}

	public Game(List<Player> p, List<Category> c) {
		this.playerList = p;
		this.categoryList = c;
	}

	public Player getCurrPlayer(){
		return this.currPlayer;
	}

	public void setCurrPlayer(Player cur){
		this.currPlayer = cur;
	}

	public void addCategory(Category c){
		categoryList.add(c);
	}

	public void addPlayerList(List<Player> lst){
		this.playerList = lst;
	}

	public void initRound() {
			this.currRound++;
			this.currPlayer = playerList.size() > 0 ? playerList.get(0) : null;
			this.spinCount = 50;
			this.quesCount = 30;
	}

	public boolean endRoundCheck(){
		return spinCount <= 0 || quesCount <= 0;
	}

	public void takeTurn() {
		this.spinCount--;
	}

	/** Find the winner among three players
	 * @return the winner
	 */
	public Player winCheck() {
		int max = 0;
		Player winner = null;
		for(Player p: playerList) {
			if (p.getFinalScore() > max){
				max = p.getFinalScore();
				winner = p;
			}
		}
		return winner;
	} // end winCheck

	// Get the next player
	public Player nextPlayer() {
		int curIndex = playerList.indexOf(getCurrPlayer());
		if(curIndex < playerList.size() - 1)
		{
			return playerList.get(curIndex + 1);
		}else return playerList.get(0);
	} // end nextPlayer

	// display question, answer, check answer and update score
	// return boolean value if player's answer correct/wrong

	public boolean answerQuestion(String answer){
		boolean res = answer.equals(currQuestion.getAnswer());
		String[] arr = this.currQuestion.getValue().split("\\$");
		String val = arr[1];
		int points = Integer.parseInt(val);
		this.currPlayer.updateScore(res, points);
		quesCount--;
		return res;
	} // end answerQuestion

	/** Retrieve current question
	 *
	 * @param category	selected category
	 * @return question
	*/
	public Question queryQ(String category) {
		String question = "";

		for(Category c : categoryList) {
			if (c.getCategoryName().equals(category)) {
				this.currQuestion = c.getNextQuestion();
			}
		}
		return this.currQuestion;
	}
} // end class game
