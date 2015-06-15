package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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

	public String getColumnName(String table) {
		String dbURL = "jdbc:derby:db/MyDB";
		String user = "admin";
		String password = "password";
		String result = "";
		try (Connection con = DriverManager
				.getConnection("jdbc:derby:/Users/Hannes/git/KInf-Proj/db/MyDB; user=admin; password=password");
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			String querySql = String.format("SELECT * FROM %s", table);
			ResultSet rs = stmt.executeQuery(querySql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int size = rsmd.getColumnCount();

			for (int i = 1; i <= size; i++) {
				result += rsmd.getColumnName(i) + "\n";
				set.add(rsmd.getTableName(i));

			}
			System.out.println(rsmd.getTableName(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {
		db = new DBAccess(dbURL, user, password);
	}

}
