package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.CSVReader;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

class CsvHelper {
	private List<String[]> allLines;
	private Path path;

	CsvHelper(Path path) {
		this.path = path;
	}

	/**
	 * Returns all lines of a .csv.
	 * 
	 * @return A line of a CSV-File
	 * @throws ImportException
	 *             if path was not set correctly or there was a problem during
	 *             import
	 */
	List<String[]> getAllLines() throws ImportException {
		try {
			CSVReader reader = new CSVReader(new InputStreamReader(
					new FileInputStream(path.toString()), "UTF-8"));
			allLines = reader.readAll();
			reader.close();
			return allLines;
		} catch (IOException e) {
			throw new ImportException("Fehler beim Einlesen: " + e.getMessage());
		}
	}

	/**
	 * Returns a specific line of a file.
	 * 
	 * @param lineNumber
	 *            the number of the wanted line.
	 * @return An array with all fields of a line
	 * @throws ImportException
	 *             if there was a problem during import
	 */
	String[] getLine(int lineNumber) throws ImportException {
		if (allLines.isEmpty()) {
			getAllLines();
		}
		return allLines.get(lineNumber + 1);
	}
}
