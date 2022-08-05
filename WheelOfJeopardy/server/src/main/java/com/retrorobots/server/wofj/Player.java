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

import java.util.Scanner;

public class Player {
	public String name;
	public int score;
    public int token;
    
    /**
     * Create a new player
     * @param name 		player's name
     * @param score		player's score
     * @param token		player's token amount 
     */ 
	public Player(String name, int score, int token) {
		setName(name);
		setScore(score);
		setToken(token);
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
	
	// set player's token amount
	public void setToken(int token) {
		this.token = token;
	}
	
	// get player's token amount
	public int getToken() {
		return token;
	}
	
	// add token when player gets a free turn sector
	public int addToken() {
		int newToken = getToken() + 1;
		setToken(newToken);
		return newToken;
	}

	// add points to current score and return the updated score
	public int add(int points) {
		setScore(score + points);
		return score;
	}
	
	// subtract points from current score and return the updated score 
	public int subtract(int points) {
		setScore(score - points);
		return score;
	}
	
	// Prompt player to enter a choice among A, B, and C
	char input() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your choice: A, B, or C");
		char res = sc.next().charAt(0);
		return res;
	}

	/** Check if player's response is correct
	 * options: 'A', 'B', 'C'
	 * @param answer	the correct answer
	 * @param response	the response from player
	 */
	boolean checkResponse(char answer, char response) {
		boolean res = (response == Character.toLowerCase(answer) || response == answer);
		return res;
	}

	/** Update player's score according to the correctness of response
	 * @param res	boolean value of player's response
	 */
	void updateScore(boolean res, int points) {
		int newScore = 0;
		if (res == true) newScore = add(points);
		else newScore = subtract(points);
	}
} // end class Player

