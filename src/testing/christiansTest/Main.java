package testing.christiansTest;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		IOTest nioTest = new IOTest();
		File testFile = nioTest.readFile("/testing/christiansTest/iotest.csv");
		System.out.println("Filename: " + testFile.getName());
		System.out.println(testFile.length());
	}
}
