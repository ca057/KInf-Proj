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
import de.uniba.kinf.projm.hylleblomst.gui.controller.DetailsController;
import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logic.UserQuery;
import de.uniba.kinf.projm.hylleblomst.logicImpl.UserQueryImpl;

/**
 * @author Christian, Johannes, Simon
 *
 */
public class Model extends Observable {
	SearchInitiator search;
	CachedRowSet searchResult;
	DatabaseManagement dbManagement;
	DetailsController detailsController;

	public CachedRowSet getSearchResult() {
		return searchResult;
	}

	public Model(SearchInitiator search, DetailsController detailsController) {
		if (search != null && detailsController != null) {
			this.search = search;
			this.detailsController = detailsController;
		} else {
			throw new InputMismatchException("Die Logik des Programms ist fehlerhaft (null)");
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

	public void searchPerson(UserQueryImpl userQuery) throws SQLException {
		if (userQuery != null) {
			searchResult = search.searchPerson(userQuery);
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
