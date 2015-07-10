package de.uniba.kinf.projm.hylleblomst.gui.model;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Observable;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.database.DatabaseManagement;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;
import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.gui.controller.DetailsViewController;
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

	public CachedRowSet getSearchResult() {
		return searchResult;
	}

	public Model(SearchInitiator search) {
		if (search != null) {
			this.search = search;
		} else {
			throw new InputMismatchException("Die Logik des Programms ist fehlerhaft (null)");
		}
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

	public void searchPerson(String personID) throws SQLException {
		if (personID != null) {
			searchResult = search.searchPerson(personID);
			detailsController.processSearchResult(searchResult);

		} else {
			throw new InputMismatchException("Die übergebene ID ist fehlerhaft (null)");
		}
	}

	public void exportSearchedData() {
		// TODO Simon do some stuff here
	}

	public void setUpDatabase(File dirForSetup) throws SetUpException {
		if (dirForSetup != null) {
			dbManagement = new DatabaseManagement(dirForSetup);
			try {
				dbManagement.setUpDatabase();
				dbManagement.setUpTables();
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
