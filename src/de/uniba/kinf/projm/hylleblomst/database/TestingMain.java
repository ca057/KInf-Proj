package de.uniba.kinf.projm.hylleblomst.database;

import java.io.File;

import de.uniba.kinf.projm.hylleblomst.dataImport.CsvFormatVerifier;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

/**
 * @author Simon
 *
 */
public class TestingMain {

	public static void main(String[] args) {
		File location = new File("./db");
		File data = new File("./doc/example_data.csv");

		DatabaseManagement dbController = new DatabaseManagement(location);

		CsvFormatVerifier verify;
		try {
			new CsvFormatVerifier().verifyCsv(data);
		} catch (ImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// try {
		// dbController.setUpDatabase();
		// } catch (SetUpException e) {
		// System.out.println(e.getMessage());
		// }
		//
		// try {
		// dbController.setUpTables();
		// } catch (SetUpException e) {
		// System.out.println(e.getMessage());
		// }
		//
		// try {
		// dbController.importDataIntoDatabase(data);
		// } catch (ImportException e) {
		// System.out.println(e.getMessage());
		// }

	}
}
