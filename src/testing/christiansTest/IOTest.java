package testing.christiansTest;

import java.io.File;

public class IOTest {

	public File readFile(String pathOfFile) {
		File f = new File(getClass().getResource(pathOfFile).getFile());
		return f;
	}
}
