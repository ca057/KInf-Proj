package testing.johannesTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import com.csvreader.*;

public class MainCsvReader {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		// CsvReader reader = new CsvReader(new FileReader("doc/example_data.csv"));
		CsvReader reader = new CsvReader("doc/example_data.csv");
		System.out.println(reader.getHeaderCount());
		System.out.println(reader.getColumnCount());
	}

}
