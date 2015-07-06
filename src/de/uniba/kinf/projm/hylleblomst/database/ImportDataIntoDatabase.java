package de.uniba.kinf.projm.hylleblomst.database;

import de.uniba.kinf.projm.hylleblomst.dataImport.ImportData;
import de.uniba.kinf.projm.hylleblomst.dataImport.ImportDataImpl;

public class ImportDataIntoDatabase {
	public static void main(String[] args) throws Exception {

		String dbURL = "jdbc:derby:./db/MyDB";
		String user = "admin";
		String password = "password";

		ImportDatabaseImpl imports = new ImportDatabaseImpl(dbURL, user,
				password);

		ImportData importData = new ImportDataImpl();

		importData.addData("./doc/example_data.csv");
	}
}
