package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.nio.file.Paths;
import java.util.List;

public class Import {
	// FIXME Static oder rows als Instanzvariable?

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
	public static void addImport(String path) throws ImportException {
		CsvHelper csvhelper = new CsvHelper();
		csvhelper.setPath(Paths.get(path));
		List<String[]> rows = csvhelper.getAllLines();
		System.out.println(rows);
		for (String[] row : rows) {
			Validation.checkIDs(row[0]);
		}
		addToDatabase(rows);
	}

	/**
	 * 
	 * @param list
	 */
	private static void addToDatabase(List<String[]> list) {

	}

}
