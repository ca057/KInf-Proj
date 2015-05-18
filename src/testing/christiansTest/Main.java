package testing.christiansTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import testing.johannesTest.Person;

// import csv with standard java-classes
public class Main {
	public static void main(String[] args) throws IOException {
		// read file
		IOTest ioTest = new IOTest();
		File testFile = ioTest
				.readFile("/testing/christiansTest/example_data.csv");

		System.out.println("Filename: " + testFile.getName());
		System.out.println("\n===\n");

		// read all lines of the file
		List<String> lines = Files.readAllLines(testFile.toPath());

		// split lines by commas and ignore commas inside of quotation marks
		// with a
		// copied regular expression from the internet!
		String regex = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

		// test with first line
		 String[] columnHeads = lines.get(0).split(regex);

		// split all lines and save them as arrays in an arraylist
		ArrayList<String[]> allLines = new ArrayList<String[]>();

		for (int i = 0; i < lines.size(); i++) {
			String[] temp = lines.get(i).split(regex);
			allLines.add(temp);
		}

		 // print all heads of the column with an index
		 int counter = 0;
		 for (String h : columnHeads) {
		 System.out.println(counter + ": " + h);
		 counter++;
		 }

		// print nummer, anrede und vorname
		// for (int i = 1; i < lines.size(); i++) {
		// String[] temp = lines.get(i).split(regex);
		// System.out.println(columnHeads[0] + " " + temp[0] + "; "
		// + columnHeads[3] + ": " + temp[3] + "; " + columnHeads[5]
		// + ": " + temp[5]);
		// }

		// use class person by johannes
		// testing.johannesTest.Person personTest = new Person();
		// personTest.setAll(allLines.get(1));
		// System.out.println(personTest.toString() + "\n");

		// print all persons from the testfile
//		for (int i = 1; i < allLines.size(); i++) {
//			testing.johannesTest.Person tmp = new Person();
//			tmp.setAll(allLines.get(i));
//			System.out.println(tmp.toString());
//		}
	}
}
