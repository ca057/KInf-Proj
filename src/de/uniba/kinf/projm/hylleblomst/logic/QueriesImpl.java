package de.uniba.kinf.projm.hylleblomst.logic;

import java.util.Collection;
import java.util.HashSet;

public class QueriesImpl implements Queries {
	DBAccess db;
	public HashSet<String> set = new HashSet<String>();

	@Override
	public void search(Collection<QueryRequest> queryRequests) {

		buildQuery(queryRequests);
	}

	private void buildQuery(Collection<QueryRequest> queryRequests) {
		// In WHERE auch SELECT möglich!!
		StringBuffer query = new StringBuffer();

		String dbURL = "jdbc:derby:Users/Hannes/git/KInf-Proj/db/MyDB";
		String user = "admin";
		String password = "password";
		db = new DBAccess(dbURL, user, password);

	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {
		db = new DBAccess(dbURL, user, password);
	}

}
