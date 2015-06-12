package testing.johannesTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;


public class Main {

	public static void main(String[] args) throws IOException {
		

		// Build reader and ArrayList instance
		CSVReader reader = new CSVReader(
				new FileReader("doc/example_data.csv"), ',', '"', 1);
		ArrayList<Person> personen = new ArrayList();

		// Read all rows at once
		List<String[]> allRows = reader.readAll();

		// Add persons to ArrayList (wrong columns)
//		for(String s : row[31]) {
//			System.out.println(s);
//		}
		
		
		for (String[] row : allRows) {
		
			// personen.add(new Person(row[0], row[6], row[7], row[8]));
			personen.add(new Person().setAll(row));
		}
		

		// Show all Persons (toString)
		for (Person e : personen) {
			System.out.println(e);
		}
		
		//whole List:
		//for(String[] row : allRows){
	    //    System.out.println(Arrays.toString(row));
	    // }
		
	}
}
