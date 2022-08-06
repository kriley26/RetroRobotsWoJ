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

	public void endRound(){
			
	}

	public void takeTurn() {
		this.spinCount--;
		this.quesCount--;
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
		// TODO to get current's answer for checking
		boolean res = answer.equals(currQuestion.getAnswer());
		//TODO to get this question's points
		String[] arr = this.currQuestion.getValue().split("\\$");
		String val = arr[1];
		int points = Integer.parseInt(val);
		this.currPlayer.updateScore(res, points);
		return res;
	} // end answerQuestion

	/**
	 * process the sector selected in gui
	 * @param sector	sector selected on the wheel
	 * @param p			current player
	 * */
	//TODO
	public void sectors(String sector, Player p) {
		switch(sector) {
			case "category1": case "category2": case "category3":
			case "category4": case "category5":	case "category6":
				boolean res = answerQuestion(sector);
				// ToAdd ask player if redeem token or not
				if(res || p.getToken() != 0) onePlay(p);
				else onePlay(nextPlayer(p));
				break;
			case "lose turn":
				onePlay(nextPlayer(p));
				break;
			case "free turn":
				p.addToken();
				onePlay(p);
				break;
			case "bankrupt":
				p.setScore(0); // lost points for the current round
				onePlay(nextPlayer(p));
				break;
			case "spin again":
				onePlay(p);
				break;
			/**
			 * case "player's choice":
			 * case "opponents' choice":
			 * TO BE Processed on GUI side
			 */
		}
	} // end sectors

	/** display question and options retrieved from database
	 *
	 * @param category	selected category
	*/
	public String queryQ(String category) {
		// TODO send the selected category to database to retrieve current question
		String question = "";

		for(Category c : categoryList) {
			if (c.getCategoryName().equals(category)) {
				this.currQuestion = c.getAQuestion();
				question = this.currQuestion.getQuestion();
			}
		}
		return question;
	}
} // end class game
