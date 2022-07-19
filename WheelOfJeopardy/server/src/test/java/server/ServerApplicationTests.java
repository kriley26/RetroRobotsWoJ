package server;

import com.retrorobots.server.ServerApplication;
import com.retrorobots.server.controllers.QuestionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest
@ContextConfiguration
class ServerApplicationTests {

	ServerApplication testServerApplication;
	QuestionController testQuestionController;

	@BeforeEach
	void setUp() {
		testServerApplication = new ServerApplication();
		testQuestionController = new QuestionController();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void demoTestMethod() {
		//assertTrue(true);
		assertTrue(testServerApplication == testServerApplication);
	}

	@Test
	void testDatabaseConnection() {
		boolean isConnected = false;
		if (testQuestionController.getNewQuestion() != null) { isConnected = true; }
		assertTrue(isConnected);
	}

}
