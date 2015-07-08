package de.uniba.kinf.projm.hylleblomst.database;

import java.io.File;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.dataImport.ImportDataImpl;
import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;

public class DatabaseController {

	public DatabaseController() {
	}

	/**
	 * Set up a database in the specified location.
	 * 
	 * @param file
	 * @return
	 */
	public boolean setUpDatabase(File file) {
		try {
			new SetUpDatabase().run(file);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean setUpTables() {
		try {
			return new SetUpTables().run();
		} catch (SetUpException e) {
			return false;
		}
	}

	public boolean tearDownTables() {
		try {
			return new TearDownTables().run();
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean importDataIntoDatabase(File file) {
		try {
			new ImportDataImpl().addData(file.getAbsolutePath());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
