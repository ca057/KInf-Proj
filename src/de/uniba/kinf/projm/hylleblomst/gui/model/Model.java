package de.uniba.kinf.projm.hylleblomst.gui.model;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Observable;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

import de.uniba.kinf.projm.hylleblomst.dataExport.ExportDataToFile;
import de.uniba.kinf.projm.hylleblomst.database.DatabaseManagement;
import de.uniba.kinf.projm.hylleblomst.exceptions.ExportException;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;
import de.uniba.kinf.projm.hylleblomst.exceptions.SearchException;
import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.gui.controller.DetailsViewController;
import de.uniba.kinf.projm.hylleblomst.keys.DatabaseKeys;
import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logic.UserQuery;

/**
 * This class serves as the model to the underlying MVC-pattern.
 * 
 * @author Christian, Johannes, Simon
 *
 */
public class Model extends Observable {
	SearchInitiator search;
	CachedRowSet searchResult;
	DatabaseManagement dbManagement;
	DetailsViewController detailsController;
	ExportDataToFile export;

	/**
	 * The constructor to the model.
	 * 
	 * @param search
	 *            {@code SearchInitiator} that holds the underlying logic. Must
	 *            not be <code>null</code>.
	 */
	public Model(SearchInitiator search) {
		if (search != null) {
			this.search = search;
		} else {
			throw new InputMismatchException(
					"Die Logik des Programms ist fehlerhaft (null)");
		}
	}

	/**
	 * @return {@code CachedRowSet} holding the results of the search and
	 *         relevant metadata.
	 */
	public CachedRowSet getSearchResult() {
		return searchResult;
	}

	/**
	 * 
	 * 
	 * @param detailsController
	 */
	public void setDetailsController(DetailsViewController detailsController) {
		if (detailsController != null) {
			this.detailsController = detailsController;
		} else {
			throw new InputMismatchException(
					"Der Controller für die Anzeige der Personendetails ist fehlerhaft (null).");
		}
	}

	/**
	 * Initiates a search on the data with the specified query.
	 * 
	 * @param userQuery
	 *            a collection of queries by the user
	 * @throws SQLException
	 *             if an error occurs handling the user queries
	 */
	public void search(Collection<UserQuery> userQuery) throws SQLException {
		if (!(userQuery == null || userQuery.isEmpty())) {
			searchResult = search.search(userQuery);
			setChanged();
			notifyObservers(searchResult);
		} else {
			throw new InputMismatchException(
					"Die übergebene Collection ist fehlerhaft: " + search);
		}
	}

	/**
	 * Search for a specific person.
	 * 
	 * @param personIDQuery
	 *            The query containing information about the specific person,
	 *            typically its ID
	 * @throws SQLException
	 *             if an error occurs executing the query
	 * @throws SearchException
	 *             if an error occurs during the search process
	 */
	public void searchPerson(UserQuery personIDQuery) throws SQLException,
			SearchException {
		if (personIDQuery != null) {
			searchResult = search.searchPersonOrNotation(personIDQuery);
			detailsController.processCompleteSearchResult(searchResult);
		} else {
			throw new InputMismatchException(
					"Die übergebene ID ist fehlerhaft (null)");
		}
	}

	/**
	 * Returns the specified spelling of one search field.
	 * 
	 * @param sourceDetails
	 * @throws SQLException
	 */
	public CachedRowSet searchSourceDetails(UserQuery sourceDetails)
			throws SQLException {
		CachedRowSet detailsResult = new CachedRowSetImpl();
		if (sourceDetails != null) {
			detailsResult = search.searchPersonOrNotation(sourceDetails);
		}
		return detailsResult;
	}

	/**
	 * Export the current result data to a specified file which will be created
	 * if not yet existent. Existing files will be overwritten.
	 * 
	 * @param file
	 *            The file to which to save the data.
	 * @param cachedRowSet
	 *            The result data
	 * @throws ExportException
	 *             thrown when an error occurs during the export process, e.g.
	 *             missing user rights
	 */
	public void exportSearchedData(File file, CachedRowSet cachedRowSet)
			throws ExportException {
		if (file == null || cachedRowSet == null) {
			throw new InputMismatchException(
					"Der übergebene Dateipfad hat keinen Wert.");
		}
		export = new ExportDataToFile();
		try {
			export.exportToCsv(file, cachedRowSet);
		} catch (ExportException e) {
			throw new ExportException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param dirForSetup
	 * @throws SetUpException
	 */
	public void setUpDatabase(File dirForSetup) throws SetUpException {
		if (dirForSetup != null) {
			dbManagement = new DatabaseManagement(dirForSetup);
			try {
				dbManagement.setUpDatabase();
				dbManagement.setUpTables();
				search.setDbKey(new DatabaseKeys(dirForSetup));
			} catch (SetUpException e) {
				throw new SetUpException(e.getMessage());
			}
		} else {
			throw new InputMismatchException(
					"Übergebener Pfad hat keinen Wert, die Datenbank kann nicht neu angelegt werden.");
		}
	}

	/**
	 * Import data from file. Only CSV supported at the moment.
	 * 
	 * @param importFile
	 *            The {@code File} from which to import the data from
	 * @throws ImportException
	 *             Thrown if an error occurs during importing the data, e.g. a
	 *             mismatch in format
	 */
	public void importData(File importFile) throws ImportException {
		if (importFile != null) {
			try {
				dbManagement.importDataIntoDatabase(importFile);
			} catch (ImportException e) {
				throw new ImportException(e.getMessage());
			}
		} else {
			throw new InputMismatchException(
					"Übergebener Pfad hat keinen Wert, die Datei kann nicht importiert werden.");
		}
	}

	/**
	 * Clear the current database of all data related to this program.
	 * 
	 * @throws SetUpException
	 *             Thrown if an error occurs during deletion of content, e.g.
	 *             containment of data that cannot be modified or accessed by
	 *             program
	 */
	public void clearDatabase() throws SetUpException {
		try {
			dbManagement.tearDownTables();
		} catch (SetUpException e) {
			throw new SetUpException(e.getMessage());
		}
	}
}
