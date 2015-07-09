package de.uniba.kinf.projm.hylleblomst.database;

import java.io.File;

import de.uniba.kinf.projm.hylleblomst.dataImport.CsvFormatVerifier;
import de.uniba.kinf.projm.hylleblomst.dataImport.ImportDataImpl;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;
import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.keys.DatabaseKeys;

public class DatabaseController {

	DatabaseKeys db;

	public DatabaseController(File file) {
		this.db = new DatabaseKeys(file);
	}

	/**
	 * Set up a database in the specified location.
	 * 
	 * @param csvFile
	 * @throws SetUpException
	 */
	public void setUpDatabase() throws SetUpException {
		try {
			new SetUpDatabase().run(db.dbURL);
		} catch (SetUpException e) {
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("Database could not be set up: ");
			errorMessage.append(e.getMessage());
			throw new SetUpException(errorMessage.toString());
		}
	}

	public void setUpTables() throws SetUpException {
		try {
			new SetUpTables().run(db.dbURL);
		} catch (SetUpException e) {
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("Database could not be set up: ");
			errorMessage.append(e.getMessage());
			throw new SetUpException(errorMessage.toString());
		}
	}

	/**
	 * @throws SetUpException
	 */
	public void tearDownTables() throws SetUpException {
		try {
			new TearDownTables().run(db.dbURL);
		} catch (SetUpException e) {
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("Database could not be torn down: ");
			errorMessage.append(e.getMessage());
			throw new SetUpException(errorMessage.toString());
		}
	}

	/**
	 * @param file
	 * @throws ImportException
	 */
	public void importDataIntoDatabase(File file) throws ImportException {
		try {
			new CsvFormatVerifier(file);
			new ImportDataImpl().addData(file.getAbsolutePath());
		} catch (Exception e) {
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("Data could not be imported: ");
			errorMessage.append(e.getMessage());
			throw new ImportException(errorMessage.toString());
		}
	}
}
