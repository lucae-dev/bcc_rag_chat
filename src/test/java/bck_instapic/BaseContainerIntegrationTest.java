package bck_instapic;

import org.instancio.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;
import java.util.Random;

@SpringBootTest(
		classes = {BckInstapicApplication.class, IntegrationTestMockContext.class},
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(initializers = TestContainerInitializer.class)
public abstract class BaseContainerIntegrationTest extends BaseTest {

	@LocalServerPort
	private int port;

	protected String getContainerHost() {
		return "localhost";
	}

	protected int getContainerPort() {
		return port;
	}


	// Any additional common setup can go here
}