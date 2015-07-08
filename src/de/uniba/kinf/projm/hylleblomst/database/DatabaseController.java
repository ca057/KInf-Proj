package de.uniba.kinf.projm.hylleblomst.database;

import java.io.File;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.dataImport.ImportDataImpl;
import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.keys.DatabaseKeys;

public class DatabaseController {

	DatabaseKeys db;

	public DatabaseController(File file) {
		this.db = new DatabaseKeys(file);
	}

	// TODO meaningful error messages

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
			return new SetUpTables().run(db.dbURL);
		} catch (SetUpException e) {
			e.printStackTrace();
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
			new ImportDataImpl().addData(db, file.getAbsolutePath());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
