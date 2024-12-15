package bck_rag_chat;

import org.instancio.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;
import java.util.Random;

@SpringBootTest(
		classes = {BckRagChatApplication.class, IntegrationTestMockContext.class},
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(initializers = TestContainerInitializer.class)
public abstract class BaseContainerIntegrationTest {

	private static final Random random = new Random();

	@LocalServerPort
	private int port;

	protected String getContainerHost() {
		return "localhost";
	}

	protected int getContainerPort() {
		return port;
	}

	public static <T> T random(Class<T> clazz, String... fieldsToExclude) {
		InstancioApi<T> builder = Instancio.of(clazz);

		Arrays.stream(fieldsToExclude)
				.map(Select::field)
				.forEach(builder::ignore);
		return builder.create();
	}

	private static Object generateRandomValue(Class<?> fieldType) {
		if (fieldType == int.class) {
			return random.nextInt(100); // Random integer between 0 and 99
		} else if (fieldType == double.class) {
			return random.nextDouble(); // Random double
		} else if (fieldType == boolean.class) {
			return random.nextBoolean(); // Random boolean
		} else if (fieldType == char.class) {
			return (char) (random.nextInt(26) + 'a'); // Random lowercase character
		} else if (fieldType == String.class) {
			return randomString(10); // Random String of length 10
		} else if (fieldType == UUID.class) {
			return UUID.randomUUID();
		}
		return null; // Default return value for unsupported types (you can handle other types if needed)
	}

	protected static String randomString() {
		int length = random.nextInt(10);
		return randomString(length);
	}

	protected static String randomString(int minLength, int maxLength) {
		int length = random.nextInt(minLength, maxLength);
		return randomString(length);
	}

	protected static String randomString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char randomChar = (char) (random.nextInt(26) + 'a');
			sb.append(randomChar);
		}
		return sb.toString();
	}

	// Any additional common setup can go here
}