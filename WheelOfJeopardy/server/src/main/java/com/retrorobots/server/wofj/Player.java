/**
 * Class Player for Skeletal Demo
 * Will be updated after Database/server implemented
 * Features:
 * 1. Create a new player
 * 2. Set and get player's name
 * 3. Set and get player's score
 * 4. Set and get player's token amount
 * 5. Update player's score by addition and subtraction
 * 
 * @author WenjunZhou
 * */
package com.retrorobots.server.wofj;

public class Player {
	public String name;
	public int score;
    public int token;
    public int roundOneScore;
	public int roundTwoScore;

    /**
     * Create a new player
     * @param name 		player's name
     */
	public Player(String name) {
		setName(name);
	}
	
	// set player's name
	public void setName(String name) {
		this.name = name;
	}
	
	// get player's name
	public String getName() {
		return name;
	}
	
	// set player's score
	public void setScore(int score) {
		this.score = score;
	}
	
	// get player's score
	public int getScore() {
		return score;
	}

	// save player's score of the first round
	public void setRoundOneScore(int score){
		this.roundOneScore = score;
	}

	// get player's score of the first round
	public int getRoundOneScore(){
		return this.roundOneScore;
	}

	// get player's score of the second round
	public int getRoundTwoScore()
	{
		return roundTwoScore;
	}

	// save player's score of the second round
	public void setRoundTwoScore(int roundTwoScore)
	{
		this.roundTwoScore = roundTwoScore;
	}

	// calculate and return a player's final score
	public int getFinalScore(){
		return roundOneScore + roundTwoScore;
	}
	// save score for each round and reset
	public void saveRoundScore(int round){
		if(round == 1) roundOneScore = this.score;
		else if(round == 2) roundTwoScore = this.score;
		this.score = 0;
	}

	// set player's token amount
	public void setToken(int token) {
		this.token = token;
	}
	
	// get player's token amount
	public int getToken() {
		return token;
	}

	public void increaseToken() {
		this.token = 1;
	}

	public void useToken() {
		this.token = 0;
	}
	
	// add token when player gets a free turn sector
	public boolean addToken() {
		boolean newToken = getToken() == 0 ;
		increaseToken();
		return newToken;
	}

	// add points to current score and return the updated score
	public void add(int points) {
		setScore(score + points);
	}
	
	// subtract points from current score and return the updated score 
	public void subtract(int points) {
		setScore(score - points);
	}
	
	/** Update player's score according to the correctness of response
	 * @param res	boolean value of player's response
	 */
	void updateScore(boolean res, int points) {
		if (res) add(points);
		else subtract(points);
	}
} // end class Player

