package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.SQLException;
import java.util.Collection;

public class QueriesImpl implements Queries {
	DBAccess db;

	@Override
	public void search(Collection<QueryRequestImpl> queryRequests)
			throws SQLException {
		startQuery(buildQuery(queryRequests));
	}

	private String buildQuery(Collection<QueryRequestImpl> queryRequests)
			throws SQLException {
		String sqlQuery = "SELECT DISTINCT *";
		StringBuilder sqlFrom = new StringBuilder();
		StringBuilder sqlWhere = new StringBuilder();
		for (QueryRequestImpl qr : queryRequests) {
			if (sqlFrom.length() == 0) {
				sqlFrom.append(qr.getFrom());
			} else {
				sqlFrom.append(", " + qr.getFrom());
			}
			if (sqlWhere.length() == 0) {
				sqlWhere.append(qr.getWhere());
			} else {
				sqlWhere.append(" AND " + qr.getWhere());
			}

		}
		sqlQuery += " FROM " + sqlFrom
				+ String.format("%s.%s", "Hylleblomst", "Person") + " WHERE "
				+ sqlWhere;
		System.out.println(sqlQuery);
		return sqlQuery;
	}

	private void startQuery(String query) throws SQLException {
		db = new DBAccess("jdbc:derby:./db/MyDB;create=true", "admin",
				"password");
		db.startQuery(query);
	}

	@Override
	public void searchPerson(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {

	}

}
