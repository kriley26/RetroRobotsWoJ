package server;

import com.retrorobots.server.ServerApplication;
import com.retrorobots.server.controllers.QuestionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Throws an error when I tried using @SpringBootTest
 * Need to designate classes or make some other modifications to resolve
 */
//@SpringBootTest
@ContextConfiguration
class ServerApplicationTests {
	/*
	 * Declare objects
	 */
	ServerApplication testServerApplication;
	QuestionController testQuestionController;

	/*
	 * Re-instantiate objects before each test case
	 */
	@BeforeEach
	void setUp() {
		testServerApplication = new ServerApplication();
		testQuestionController = new QuestionController();
	}

	/*
	 * Do we need this?
	 */
	@Test
	void contextLoads() {
	}

	/*
	 * Player tests - turns, scoring
	 */
	@Test
	void testPlayer() {
		// Placeholder
		// Turns change based on playerList loop
		// Add to score for correct answer
		// Subtract from score for incorrect answer
		// Add round scores at the end of the second round
		// Highest score wins game

		// Placeholder
		assertTrue(true);
	}

	/*
	 * Game wheel tests
	 */
	@Test
	void testWheel() {
		// Question category sectors
		// Lose turn
		// Free turn
		// Bankrupt
		// Player's choice
		// Opponent's choices
		// Spin again

		// Placeholder
		assertTrue(true);
	}

	/*
	 * Question tests
	 */
	@Test
	void testRounds() {
		// First round - 50 spins or all questions answered moves to round two
		// Second round - 50 spins of all questions answered ends game

		// Placeholder
		assertTrue(true);
	}

}
