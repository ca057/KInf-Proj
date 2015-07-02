package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.UserKeys;
import de.uniba.kinf.projm.hylleblomst.logic.DBAccess;
import de.uniba.kinf.projm.hylleblomst.logic.PersonItem;
import de.uniba.kinf.projm.hylleblomst.logic.Queries;
import de.uniba.kinf.projm.hylleblomst.logic.QueryRequest;

public class QueriesImpl implements Queries {
	DBAccess db = new DBAccess(UserKeys.dbURL, UserKeys.adminUser, UserKeys.adminPassword);
	SQLBuilder sql = new SQLBuilder();

	@Override
	public ArrayList<PersonItem> search(Collection<QueryRequest> queryRequests) throws SQLException {

		ResultSet test = startQuery(queryRequests);
		return new ArrayList<PersonItem>();
	}

	private ResultSet startQuery(Collection<QueryRequest> queryRequests) throws SQLException {
		Boolean hasSource = false;
		ArrayList<String> inputs = new ArrayList<String>();
		StringBuilder sqlQuery = new StringBuilder();
		StringBuilder sqlWhere = new StringBuilder();
		StringBuilder sqlFrom = new StringBuilder();

		sqlQuery.append("SELECT ").append(sql.getSelect());
		sqlFrom.append(sql.getFrom());
		for (QueryRequest qr : queryRequests) {
			sqlQuery.append(", " + qr.getTable() + "." + qr.getColumn() + " AS " + qr.getSearchField());
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
		return db.startQuery(sqlQuery.toString(), inputs);
	}

	@Override
	public ResultSet searchPerson(String string) throws SQLException {
		ArrayList<String> inputs = new ArrayList<String>();
		inputs.add("" + string);
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(sql.getSelectAll()).append(sql.getFrom()).append(" WHERE Person.PersonID = ?");
		return db.startQuery(sqlQuery.toString(), inputs);
	}
}
