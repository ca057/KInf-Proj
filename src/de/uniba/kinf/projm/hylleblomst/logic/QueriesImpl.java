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
		String query = "";
		for (QueryRequest qr : queryRequests) {
			if ("".equals(query)) {
				query = "SELECT DISTINCT * FROM " + qr.getSQLStatement();
			} else {
				query += " AND EXISTS (SELECT * FROM " + qr.getSQLStatement()
						+ ")";
			}
		}
		System.out.println(query);
		return query;
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
