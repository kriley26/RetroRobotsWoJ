package server;

import com.retrorobots.server.ServerApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest
@ContextConfiguration
class ServerApplicationTests {

	ServerApplication testServerApplication;

	@BeforeEach
	void setUp() {
		testServerApplication = new ServerApplication();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void demoTestMethod() {
		//assertTrue(true);
		assertTrue(testServerApplication == testServerApplication);
	}

}
