package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.CSVReader;

public class CsvHelper {
	List<String[]> allLines;
	Path path;

	CsvHelper(Path path) throws ImportException {
		if (Validation.isValidCSV(path)) {
			this.path = path;
		}
	}

	/**
	 * Gets a line
	 * 
	 * @return A line of a CSV-File.
	 * @throws IOException
	 * @throws ImportException
	 */
	List<String[]> getAllLines() throws IOException, ImportException {
		if (!Validation.isNotNull(path)) {
			throw new ImportException("Es wurde kein Pfad angegeben (null).");
		} else if (!Validation.isValidCSV(path)) {
			throw new ImportException(
					"Die angegebene Datei muss auf .csv enden.");
		}
		CSVReader reader = new CSVReader(new FileReader(path.toString()), ',',
				'"', 1);
		allLines = reader.readAll();
		reader.close();
		return allLines;
	}

	/**
	 * 
	 * @param path
	 * @param lineNumber
	 * @return
	 * @throws IOException
	 * @throws ImportException
	 */
	String[] getLine(int lineNumber) throws IOException, ImportException {
		if (allLines.isEmpty()) {
			getAllLines();
		}
		return allLines.get(lineNumber + 1);
	}
}
