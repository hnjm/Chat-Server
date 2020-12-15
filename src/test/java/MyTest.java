import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	@Test
	void test() {
		Server myServer = new Server();
		
		String testString = "Hello This Is Test String";
		
		String[] resultTest = myServer.messageProcess(testString);
		
		
		assertEquals("Hello", resultTest[0], "Wrong Value");
	}

}
