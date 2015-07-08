package de.uniba.kinf.projm.hylleblomst.gui.model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Observable;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logic.UserQueries;

public class Model extends Observable {
	SearchInitiator search;
	CachedRowSet searchResult;

	public Model(SearchInitiator search) {
		if (search != null) {
			this.search = search;
		} else {
			throw new InputMismatchException("Die Logik des Programms ist fehlerhaft: " + search);
		}
	}

	public void search(Collection<UserQueries> userQueries) throws SQLException {
		if (!(userQueries == null || userQueries.isEmpty())) {
			searchResult = search.search(userQueries);
			notifyObservers();
		} else {
			throw new InputMismatchException("Die übergebene Collection ist fehlerhaft: " + search);
		}
	}

	public void searchPerson(String id) throws SQLException {
		if (id != null) {
			searchResult = search.searchPerson(id);
			notifyObservers();
		} else {
			throw new InputMismatchException("Die übergebene ID ist fehlerhaft: " + search);
		}
	}
}
