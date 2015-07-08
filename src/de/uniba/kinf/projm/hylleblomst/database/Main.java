package de.uniba.kinf.projm.hylleblomst.database;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		File location = new File("./db");
		File data = new File("./doc/example_data.csv");

		DatabaseController dbController = new DatabaseController(location);

		System.out.println("Set up database: "
				+ dbController.setUpDatabase(location));

		System.out.println("Set up tables: " + dbController.setUpTables());

		System.out.println("Import data: "
				+ dbController.importDataIntoDatabase(data));
	}
}
