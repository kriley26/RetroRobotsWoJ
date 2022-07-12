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

package wofj;

public class Game {
	
	// spin the wheel to generate a random sector
	String spin() {
		String sector = ""; // random sector generated in GUI
		return sector;
	}
	
	/**
	 * process the sector selected in GUI
	 * @param sector	sector selected on the wheel
	 * @param p			current player
	 * */
	void sectors(String sector, Player p) {
		switch(sector) {
			case "category1": case "category2": case "category3":
			case "category4": case "category5":	case "category6":
				//TODO quesGenerator in Database
				quesGenerator(sector);
				//TODO break down info of questions, answer, and points from the returned question
				onePlay(p, "Question", 'A', 200);
				break;
			case "lose turn":
				turnMonitor(p);
				break;
			case "free turn":
				p.addToken();
				break;
			case "bankrupt":
				p.setScore(0); // lost points for the current round
				break;
			case "player's choice":
				//TODO quesGenerator in Database
				quesGenerator(playerChoice(p)); 
				//TODO break down info of questions, answer, and points from the returned question
				onePlay(p, "Question", 'A', 100); 
				break;
			case "opponents' choice":
				String category = playerChoice(turnMonitor(p)); // let the next player pick category for current
				//TODO quesGenerator in Database
				quesGenerator(category);
				//TODO break down info of questions, answer, and points from the returned question
				onePlay(p, "Question", 'A', 200);
				break;
			case "spin again":
				sectors(spin(), p);
				break;
		}
	} // end sectors
	
	// Monitor players' turn taking
	Player turnMonitor(Player cur) {
		//TODO check database to return the next player in line
		return "Player next";
	}

	// Prompt player to choose a category
	String playerChoice(Player p) {
		//TODO let player click button to choose a category
		String playerChoice = "";
		return playerChoice;
	}
	
	/** Display question and options retrieved from database
	 * 
	 * @param category	selected category
	 */
	void displayQ(String category) {
		// TODO send the selected category to database to retrieve current question
		String question = "";
		System.out.println(question);
	}
	
	/**
	 * display question-answer-check-update score and display name/score
	 * 
	 * @param curPlayer current Player
	 * @param question	current question to be answered
	 * @param ans		answer to current question
	 * @param points	point value of current question
	 */
	void onePlay(Player curPlayer, String question, char ans, int points) {
		displayQ(question);
		char input = curPlayer.input();// take player's input
		boolean res = curPlayer.checkResponse(ans, input);
		curPlayer.updateScore(res, points);
		System.out.println(curPlayer.getName() + curPlayer.getScore());
	}	
	
	/** Find the winner among three players
	 * @param p1 the 1st player
	 * @param p2 the 2nd player
	 * @param p3 the 3rd player
	 * @return the winner
	 */
	Player winCheck(Player p1, Player p2, Player p3) {
		if (p1.getScore() > p2.getScore() && p1.getScore() > p3.getScore()) {
			return p1;
		}else if(p2.getScore() > p1.getScore() && p2.getScore() > p3.getScore()) {
			return p2;
		}else return p3;
	}	
	
	// simulation of one round
	void oneRound(Player p1, Player p2, Player p3) {
		int spinCounter = 50; // max spins in one round
		int quesCounter = 30; // max questions in one round
		
		while(spinCounter > 0 && quesCounter > 0) {
			//TODO Game flow to be implemented after connected to GUI/Server/Database
		}
	} // end oneRound
} // end class Game
