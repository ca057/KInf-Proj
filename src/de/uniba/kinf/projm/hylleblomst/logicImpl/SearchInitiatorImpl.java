package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.DBUserKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;
import de.uniba.kinf.projm.hylleblomst.logic.DBAccess;
import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logic.UserQueries;

public class SearchInitiatorImpl implements SearchInitiator {
	DBAccess db = new DBAccess(DBUserKeys.dbURL, DBUserKeys.guestUser2, DBUserKeys.guestPassword2);
	SQLBuilder sql = new SQLBuilder();
	ArrayList<String> inputs = new ArrayList<String>();

	@Override
	public CachedRowSet search(Collection<UserQueries> userQueries) throws SQLException {
		if (!(userQueries == null || userQueries.isEmpty())) {
			return db.startQuery(buildQuery(userQueries), inputs);
		} else {
			throw new InputMismatchException("Die übergebene Collection ist fehlerhaft (null oder leer)");
		}
	}

	private String buildQuery(Collection<UserQueries> userQueries) throws SQLException {
		Boolean hasSource = false;
		StringBuilder sqlQuery = new StringBuilder();
		StringBuilder sqlFrom = new StringBuilder();
		StringBuilder sqlWhere = new StringBuilder();
		// FIXME Das mit inputs als Instanzvariable ist nicht besonders schön.
		inputs.clear();

		sqlQuery.append("SELECT ").append(sql.getSelect());
		sqlFrom.append(sql.getFrom());

		for (UserQueries qr : userQueries) {
			if (qr.getColumn() == ColumnNameKeys.STUDIENJAHR_INT) {
				sqlQuery.append(", " + qr.getTable() + "." + ColumnNameKeys.STUDIENJAHR + " AS " + qr.getSearchField());
			} else if (qr.getColumn() == ColumnNameKeys.DATUM) {
				// FIXME Da müssen Christian und ich nochmal drüber reden, da
				// bei der Rückgabe die ColumnNameKeys besser wären.
				sqlQuery.append(", " + qr.getTable() + "." + ColumnNameKeys.DATUM);
				sqlQuery.append(
						", " + qr.getTable() + "." + ColumnNameKeys.DATUMS_FELDER_GESETZT + " AS " + "DATUM_INT");
			} else {
				sqlQuery.append(", " + qr.getTable() + "." + qr.getColumn() + " AS " + qr.getSearchField());
			}
			if (qr.getSource() == SourceKeys.ORT_NORM_AB) {
				sqlQuery.append(", " + qr.getTable() + "." + ColumnNameKeys.ANMERKUNG);
			}
			if (qr.getSource() != SourceKeys.NO_SOURCE) {
				hasSource = true;
			}

			if (sqlWhere.length() == 0) {
				sqlWhere.append(qr.getWhere());
			} else {
				sqlWhere.append(" AND " + qr.getWhere());
			}
			if (!("true".equals(qr.getInput()))) {
				inputs.add(qr.getInput());
			}
		}

		if (hasSource) {
			sqlFrom.append(", " + TableNameKeys.QUELLEN);
		}

		sqlQuery.append(" FROM ").append(sqlFrom).append(" WHERE ").append(sqlWhere);
		System.out.println(sqlQuery);
		return sqlQuery.toString();
	}

	@Override
	public CachedRowSet searchPerson(String string) throws SQLException {
		inputs.clear();
		inputs.add(string);
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(sql.getSelectAll()).append(sql.getFrom()).append(" WHERE Person.PersonID = ?");
		return db.startQuery(sqlQuery.toString(), inputs);
	}
}
