package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.nio.file.Paths;
import java.util.List;

public class Import {

	/**
	 * Adds data of a file to the database
	 * 
	 * @param path
	 *            The path of the file
	 * @throws ImportException
	 *             if there was a problem during import
	 */
	public static void addImport(String path) throws ImportException {
		CsvHelper csvhelper = new CsvHelper();
		csvhelper.setPath(Paths.get(path));
		List<String[]> test = csvhelper.getAllLines();
		System.out.println(test);
		// TODO Methode zur Validierung aufrufen
		// TODO Methode zur Weitergabe an DB aufrufen

	}

}
