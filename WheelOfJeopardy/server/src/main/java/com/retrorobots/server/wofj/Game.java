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
import java.util.List;

public class Game {
	public List<Player> playerList;
	public List<Category> categoryList;
	// int array to save score after each round
	public int[] score = new int[5];
	public int playerIndex = 0; // start from the first player

	public Game(List<Player> p, List<Category> c) {
		this.playerList = p;
		this.categoryList = c;
	}

	/**
	 * A method to simulate a complete game
	 * @param g - A game object
	 * @return Player object - the winner
	*/
	public Player playOneGame(Game g){
		playerList = g.playerList;
		categoryList = g.categoryList;

		for(int round = 0; round < 2; round++) {
			oneRound(playerList, categoryList);
			saveResetScore(playerList);
		}
		return winCheck();
	} // end playOneGame

	// simulation of one round
	void oneRound(List<Player> p, List<Category> c) {
		int spinCounter = 50; // max spins in one round
		int quesCounter = 30; // max questions in one round
		Player curPlayer = p.get(0);

		while(spinCounter > 0 && quesCounter > 0) {
			onePlay(curPlayer);
			spinCounter--;
			quesCounter--;
			curPlayer = nextPlayer(curPlayer);
		}
	} // end oneRound

	// To save and reset each player's score to 0 after one round
	void saveResetScore(List<Player> p){
		for(int i = 0; i < p.size(); i++){
			score[i] = score[i] + p.get(i).score;
			p.get(i).setScore(0);
		}
	} // end saveResetScore

	/** Find the winner among three players
	 * @return the winner
	 */
	Player winCheck() {
		int max = 0;
		int winner = 0;
		for(int i = 0; i < score.length; i++) {
			if (score[i]> max){
				max = score[i];
				winner = i;
			}
		}
		return playerList.get(winner);
	} // end winCheck

	// Get the next player
	Player nextPlayer(Player cur) {
		int curIndex = playerList.indexOf(cur);
		if(curIndex < playerList.size() - 1)
		{
			return playerList.get(curIndex + 1);
		}else return playerList.get(0);
	} // end nextPlayer

	/**
	 * One play of a player and ends if loses turn
	 * display question-answer-check-update score and display name/score
	 *
	 */
	void onePlay(Player curPlayer) {
		String sector = spin();
		sectors(sector, curPlayer);
	} // end onePlay

	// spin the wheel to generate a random sector
	//TODO
	String spin() {
		String sector = ""; // random sector generated in PlayerWindow
		return sector;
	}

	// display question, answer, check answer and update score
	// return boolean value if player's answer correct/wrong
	// TODO
	boolean answerQuestion(String category, Player curPlayer){
		displayQ(category);
		char input = curPlayer.input(); // take player's input
		// TODO to get current's answer for checking
		boolean res = curPlayer.checkResponse(answer, input);
		//TODO to get this question's points
		curPlayer.updateScore(res, points);
		return res;
	} // end answerQuestion

	/**
	 * process the sector selected in gui
	 * @param sector	sector selected on the wheel
	 * @param p			current player
	 * */
	//TODO
	void sectors(String sector, Player p) {
		switch(sector) {
			case "category1": case "category2": case "category3":
			case "category4": case "category5":	case "category6":
				boolean res = answerQuestion(sector, p);
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
	void displayQ(String category) {
		// TODO send the selected category to database to retrieve current question
		String question = "";
		System.out.println(question);
	}
} // end class game
