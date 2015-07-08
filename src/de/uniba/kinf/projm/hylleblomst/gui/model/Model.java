package de.uniba.kinf.projm.hylleblomst.gui.model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Observable;

import de.uniba.kinf.projm.hylleblomst.logic.PersonItem;
import de.uniba.kinf.projm.hylleblomst.logic.UserQueries;

public class Model extends Observable {

	public void search(Collection<UserQueries> userQueries) throws SQLException {

	}

	public List<PersonItem> searchPerson(String id) throws SQLException {
		return null;
	}

}
