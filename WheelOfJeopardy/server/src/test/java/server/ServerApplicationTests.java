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
	 * ?
	 */
	@Test
	void contextLoads() {
	}

	/*
	 * Player tests - turns, scoring,
	 */
	@Test
	void testPlayer() {
		assertTrue(true);
	}

	/*
	 * Game wheel tests - sectors, game board interface
	 */
	@Test
	void testWheel() {
		assertTrue(true);
	}

	/*
	 * ?
	 */
	@Test
	void testQuestion() {
		assertTrue(true);
	}

}
