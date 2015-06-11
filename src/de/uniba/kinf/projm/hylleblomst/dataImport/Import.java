package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.nio.file.Paths;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

public class Import {
	List<String[]> rows;

	/**
	 * Adds data of a file to the database.
	 * <p>
	 * 
	 * Reads a CSV-file and passes on a <code>List</code> of
	 * <code>String[]</code>.
	 * 
	 * @param path
	 *            The path of the file
	 * @throws ImportException
	 *             if there was a problem during import
	 */
	public void addCSV(String path) throws ImportException {
		CsvHelper csvhelper = new CsvHelper();
		csvhelper.setPath(Paths.get(path));
		rows = csvhelper.getAllLines();
		System.out.println(rows);
		for (String[] row : rows) {
			Validation.checkIDs(row[0]);
		}
		try {
			// databse.Import.addToDatabase(rows);
		} catch (Exception e) {
			// TODO Exception anpassen
			throw new ImportException(
					"Ein Fehler beim Schreiben der Daten ist aufgetreten: " + e);
		}

	}
}
