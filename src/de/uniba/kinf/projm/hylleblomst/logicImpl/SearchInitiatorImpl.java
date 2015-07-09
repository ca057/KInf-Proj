package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.InputMismatchException;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.keys.DBUserKeys;
import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logic.UserQueries;

/**
 * Implementation of {@link SearchInitiator}
 * 
 * @author Johannes
 *
 */
public class SearchInitiatorImpl implements SearchInitiator {
	DBAccess db = new DBAccess(DBUserKeys.dbURL, DBUserKeys.guestUser2, DBUserKeys.guestPassword2);
	SQLBuilder sqlBuilder;

	@Override
	public CachedRowSet search(Collection<UserQueries> userQueries) throws SQLException {
		if (!(userQueries == null || userQueries.isEmpty())) {
			sqlBuilder = new SQLBuilder(userQueries);
			return db.startQuery(sqlBuilder.getSQLStatement(), sqlBuilder.getInputs());
		} else {
			throw new InputMismatchException("Die übergebene Collection ist fehlerhaft (null oder leer)");
		}
	}

	@Override
	public CachedRowSet searchPerson(String personID) throws SQLException {
		if (personID != null) {
			sqlBuilder = new SQLBuilder(personID);
			return db.startQuery(sqlBuilder.getSQLStatement(), sqlBuilder.getInputs());
		} else {
			throw new InputMismatchException("Die übergebene ID ist fehlerhaft (null)");
		}
	}
}
