package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class QueriesImpl implements Queries {
	DBAccess db;
	ArrayList<Object> inputs = new ArrayList<Object>();

	@Override
	public void search(Collection<QueryRequestImpl> queryRequests)
			throws SQLException {
		startQuery(buildQuery(queryRequests));
	}

	private String buildQuery(Collection<QueryRequestImpl> queryRequests)
			throws SQLException {
		inputs.clear();
		String sqlQuery = "SELECT DISTINCT *";
		StringBuilder sqlFrom = new StringBuilder();
		StringBuilder sqlWhere = new StringBuilder();
		for (QueryRequestImpl qr : queryRequests) {
			if (!(qr.getTable().toUpperCase().startsWith("PERSON"))) {
				sqlFrom.append(qr.getFrom() + ", ");
			}
			if (sqlWhere.length() == 0) {
				sqlWhere.append(qr.getWhere());
			} else {
				sqlWhere.append(" AND " + qr.getWhere());
			}
			if (!(qr.getInput() instanceof Boolean)) {
				inputs.add(qr.getInput());
			}
		}
		sqlQuery += " FROM " + sqlFrom
				+ String.format("%s.%s", "Hylleblomst", "Person") + " WHERE "
				+ sqlWhere;

		System.out.println(sqlQuery);
		return sqlQuery;
	}

	private void startQuery(String sqlQuery) throws SQLException {
		db = new DBAccess("jdbc:derby:./db/MyDB;create=true", "admin",
				"password");
		db.startQuery(sqlQuery, inputs);
	}

	@Override
	public void searchPerson(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {

	}

}
