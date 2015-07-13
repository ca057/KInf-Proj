package de.uniba.kinf.projm.hylleblomst.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.dataImport.CsvFormatVerifier;
import de.uniba.kinf.projm.hylleblomst.dataImport.impl.DataImportControllerImpl;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;
import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.keys.DBUserKeys;
import de.uniba.kinf.projm.hylleblomst.keys.DatabaseKeys;

/**
 * This class serves as the model for the database. Its methods invoke actions
 * effecting the structure and content of the database.
 * 
 * <b>Note:</b> This class' main flaw is the static reference to user names and
 * passwords. A security module for the program should enhance this by asking
 * for the user credentials before effecting the state of the database.
 * 
 * @author Simon
 *
 */
public class DatabaseManagement {

	DatabaseKeys db;

	/**
	 * Set the Keys for this database. These include the URL to the database.
	 * 
	 * <b>Note:</b> Later development would have the DatabaseKeys contain the
	 * user name and password.
	 * 
	 * @param file
	 */
	public DatabaseManagement(File file) {
		this.db = new DatabaseKeys(file);
	}

	/**
	 * Set up a database in the specified location.
	 * 
	 * @param csvFile
	 * @throws SetUpException
	 */
	public void setUpDatabase() throws SetUpException {
		try (Connection con = DriverManager.getConnection(db.dbURL
				+ "; create=true", DBUserKeys.adminUser,
				DBUserKeys.adminPassword)) {
			new SetUpDatabase().setUpDatabase(con);
		} catch (SQLException | SetUpException e) {
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("Database could not be set up: ");
			errorMessage.append(e.getMessage());
			throw new SetUpException(errorMessage.toString());
		}
	}

	/**
	 * Set up defined tables that are able to hold the data from
	 * project-specific sources.
	 * 
	 * @throws SetUpException
	 */
	public void setUpTables() throws SetUpException {
		try (Connection con = DriverManager.getConnection(db.dbURL,
				DBUserKeys.adminUser, DBUserKeys.adminPassword)) {
			new SetUpTables().setUpTables(con);
			new SetUpDatabaseFunctions().setUpUserDefinedFunctions(con);
		} catch (SQLException | SetUpException e) {
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("Database could not be set up: ");
			errorMessage.append(e.getMessage());
			throw new SetUpException(errorMessage.toString());
		}
	}

	/**
	 * Tear down defined tables and functions.
	 * 
	 * @throws SetUpException
	 */
	public void tearDownTables() throws SetUpException {
		try (Connection con = DriverManager.getConnection(db.dbURL,
				DBUserKeys.adminUser, DBUserKeys.adminPassword)) {
			new TearDownFunctions().tearDownFunctions(con);
			new TearDownTables().tearDownTables(con);
		} catch (SQLException | SetUpException e) {
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("Database could not be torn down: ");
			errorMessage.append(e.getMessage());
			throw new SetUpException(errorMessage.toString());
		}
	}

	/**
	 * Import data from file to database.
	 * 
	 * @param file
	 *            The file containing the data to import.
	 * @throws ImportException
	 *             If an error occurs during import.
	 */
	public void importDataIntoDatabase(File file) throws ImportException {
		try {
			if (CsvFormatVerifier.verifyCsv(file)) {
				new DataImportControllerImpl().addData(db.dbURL,
						file.getAbsolutePath());
			} else {
				throw new ImportException("Chosen file is not in valid format");
			}
		} catch (Exception e) {
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("Data could not be imported: ");
			errorMessage.append(e.getMessage());
			throw new ImportException(errorMessage.toString());
		}
	}
}
