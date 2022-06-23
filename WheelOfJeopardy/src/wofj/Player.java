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
package wofj;

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
	
	// Driver with sample test cases
	public static void main(String[] args) {
		Player p1 = new Player("Jack", 0, 0);
		Player p2 = new Player("Sue", 0, 0);
		
		p1.add(100);
		p1.add(20);
		p1.setToken(6);
		System.out.println(p1.name);
		System.out.println("Score: " + p1.getScore());
		System.out.println("Token#: " + p1.getToken());
		
		p2.add(200);
		p2.subtract(400);
		p2.setToken(3);
		System.out.println("\n" + p2.name);
		System.out.println("Score: " + p2.getScore());
		System.out.println("Token#: " + p2.getToken());
	}
} // end class Player

