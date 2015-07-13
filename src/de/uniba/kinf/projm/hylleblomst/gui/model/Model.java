package de.uniba.kinf.projm.hylleblomst.gui.model;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Observable;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

import de.uniba.kinf.projm.hylleblomst.dataExport.ExportDataToCSV;
import de.uniba.kinf.projm.hylleblomst.database.DatabaseManagement;
import de.uniba.kinf.projm.hylleblomst.exceptions.ExportException;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;
import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.exceptions.SearchException;
import de.uniba.kinf.projm.hylleblomst.gui.controller.DetailsViewController;
import de.uniba.kinf.projm.hylleblomst.keys.DatabaseKeys;
import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logic.UserQuery;

/**
 * @author Christian, Johannes, Simon
 *
 */
public class Model extends Observable {
	SearchInitiator search;
	CachedRowSet searchResult;
	DatabaseManagement dbManagement;
	DetailsViewController detailsController;
	ExportDataToCSV export;

	public Model(SearchInitiator search) {
		if (search != null) {
			this.search = search;
		} else {
			throw new InputMismatchException("Die Logik des Programms ist fehlerhaft (null)");
		}
	}

	public CachedRowSet getSearchResult() {
		return searchResult;
	}

	public void setDetailsController(DetailsViewController detailsController) {
		if (detailsController != null) {
			this.detailsController = detailsController;
		} else {
			throw new InputMismatchException(
					"Der Controller für die Anzeige der Personendetails ist fehlerhaft (null).");
		}
	}

	public void search(Collection<UserQuery> userQuery) throws SQLException {
		if (!(userQuery == null || userQuery.isEmpty())) {
			searchResult = search.search(userQuery);
			setChanged();
			notifyObservers(searchResult);
		} else {
			throw new InputMismatchException("Die übergebene Collection ist fehlerhaft: " + search);
		}
	}

	public void searchPerson(UserQuery personIDQuery) throws SQLException, SearchException {
		if (personIDQuery != null) {
			searchResult = search.searchPersonOrNotation(personIDQuery);
			detailsController.processCompleteSearchResult(searchResult);
		} else {
			throw new InputMismatchException("Die übergebene ID ist fehlerhaft (null)");
		}
	}

	/**
	 * Returns the specified spelling of one search field.
	 * 
	 * @param sourceDetails
	 * @throws SQLException
	 */
	public CachedRowSet searchSourceDetails(UserQuery sourceDetails) throws SQLException {
		CachedRowSet detailsResult = new CachedRowSetImpl();
		if (sourceDetails != null) {
			detailsResult = search.searchPersonOrNotation(sourceDetails);
		}
		return detailsResult;
	}

	public void exportSearchedData(File file, CachedRowSet cachedRowSet) throws ExportException {
		if (file == null || cachedRowSet == null) {
			throw new InputMismatchException("Der übergebene Dateipfad hat keinen Wert.");
		}
		export = new ExportDataToCSV();
		try {
			export.exportToCsv(file, cachedRowSet);
		} catch (ExportException e) {
			e.printStackTrace();
			throw new ExportException(e.getMessage());
		}
	}

	public void setUpDatabase(File dirForSetup) throws SetUpException {
		if (dirForSetup != null) {
			dbManagement = new DatabaseManagement(dirForSetup);
			try {
				dbManagement.setUpDatabase();
				dbManagement.setUpTables();
				search.setDbKey(new DatabaseKeys(dirForSetup));
			} catch (SetUpException e) {
				e.printStackTrace();
				throw new SetUpException(e.getMessage());
			}
		} else {
			throw new InputMismatchException(
					"Übergebener Pfad hat keinen Wert, die Datenbank kann nicht neu angelegt werden.");
		}
	}

	public void importData(File importFile) throws ImportException {
		if (importFile != null) {
			try {
				dbManagement.importDataIntoDatabase(importFile);
			} catch (ImportException e) {
				e.printStackTrace();
				throw new ImportException(e.getMessage());
			}
		} else {
			throw new InputMismatchException(
					"Übergebener Pfad hat keinen Wert, die Datei kann nicht importiert werden.");
		}
	}

	public void clearDatabase() throws SetUpException {
		try {
			dbManagement.tearDownTables();
		} catch (SetUpException e) {
			e.printStackTrace();
			throw new SetUpException(e.getMessage());
		}
	}
}
