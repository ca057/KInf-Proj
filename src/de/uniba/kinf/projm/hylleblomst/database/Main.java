package de.uniba.kinf.projm.hylleblomst.database;

import de.uniba.kinf.projm.hylleblomst.dataImport.ImportData;
import de.uniba.kinf.projm.hylleblomst.dataImport.ImportDataImpl;

public class Main {
	public static void main(String[] args) throws Exception {

		String dbURL = "jdbc:derby:C:/Users/Simon/git/KInf-Projekt/db/MyDB";
		String user = "admin";
		String password = "password";

		ImportDatabaseImpl imports = new ImportDatabaseImpl(dbURL, user,
				password);

		ImportData importData = new ImportDataImpl();

		importData
				.addData("C:/Users/Simon/git/KInf-Projekt/doc/example_data.csv");
	}
}
