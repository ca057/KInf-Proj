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
import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logic.UserQueries;

/**
 * @author Christian, Johannes, Simon
 *
 */
public class Model extends Observable {
	SearchInitiator search;
	CachedRowSet searchResult;
	DatabaseManagement dbManagement;

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

	public void search(Collection<UserQueries> userQueries) throws SQLException {
		if (!(userQueries == null || userQueries.isEmpty())) {
			searchResult = search.search(userQueries);
			setChanged();
			notifyObservers(searchResult);
		} else {
			throw new InputMismatchException("Die übergebene Collection ist fehlerhaft: " + search);
		}
	}

	public void searchPerson(String id) throws SQLException {
		if (id != null) {
			searchResult = search.searchPerson(id);
			setChanged();
			notifyObservers();
		} else {
			throw new InputMismatchException("Die übergebene ID ist fehlerhaft (null)");
		}
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
		// importDataIntoDatabase()

	}

	public void clearDatabase() {
		// tearDownTables
	}
}
