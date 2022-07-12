package com.retrorobots.wofj;

public class Main {
	// Driver with sample test cases
	public static void main(String[] args) {
		// create a player
		Player p1 = new Player("Jack", 0, 0);
		Player p2 = new Player("Sue", 0, 0);
		Player p3 = new Player("Landon", 0, 0);
		String question1 = "What city is the capital of Germany? A. Munich, B. Berlin, C. Hamburg";
		String question2 = "What city is the capital of China? A. Beijing, B. Shanghai, C. Tianjin";
		String question3 = "What city is the capital of USA? A. Washington DC, B. New York City, C. Austin";
		
		Game round1 = new Game();
		round1.onePlay(p1, question1, 'B', 200);
		round1.onePlay(p2, question2, 'A', 300);
		round1.onePlay(p3, question3, 'A', 100);
		//round1.onePlay(p1, question2, 'A', 300);
		
		Game round2 = new Game();
		round2.onePlay(p1, question2, 'A', 600);
		System.out.println("The winner is " + round2.winCheck(p1, p2, p3).getName());
	} // end main
}
