package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.CSVReader;

public class CsvHelper {
	List<String[]> allLines;
	String path;

	public CsvHelper(Path path) throws ImportException {
		if (Validation.isValidCSV(path)) {
			this.path = path.toString();
		} else {
			throw new ImportException("asdf");
		}
	}

	/**
	 * Gets a line
	 * 
	 * @return A line of a CSV-File.
	 * @throws IOException
	 * @throws ImportException
	 */
	public void getAllLines() throws IOException, ImportException {
		if (Validation.isNotNull(path)) {
			CSVReader reader = new CSVReader(new FileReader(path), ',', '"', 1);
			allLines = reader.readAll();
			reader.close();
		} else {
			throw new ImportException("Dieser Pfad ist nicht korrekt.");
		}
	}

	/**
	 * 
	 * @param path
	 * @param lineNumber
	 * @return
	 * @throws IOException
	 * @throws ImportException
	 */
	public String[] getLine(int lineNumber) throws IOException, ImportException {
		if (allLines.isEmpty()) {
			getAllLines();
		}
		return allLines.get(lineNumber);
	}
}
