package de.uniba.kinf.projm.hylleblomst.database;

import java.io.File;

import de.uniba.kinf.projm.hylleblomst.dataImport.CsvFormatVerifier;

public class Main {

	public static void main(String[] args) {
		File location = new File("./db");
		File data = new File("./doc/example_data.csv");

		DatabaseController dbController = new DatabaseController(location);

		CsvFormatVerifier verify = new CsvFormatVerifier(data);
		System.out.println(verify.verifyCsv());

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
