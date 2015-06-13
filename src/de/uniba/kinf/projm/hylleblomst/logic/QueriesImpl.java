package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueriesImpl implements Queries {
	String dbURL = "jdbc:derby:db/MyDB";
	String user = "admin";
	String password = "password";

	@Override
	public void fullTextSearch(String query) throws SQLException {
		startQuery(query);
	}

	@Override
	public void extendedSearch(ColumnKeys[] colums, Object[] input, int[] source) {

	}

	private void startQuery(String query) throws SQLException {
		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement();
				ResultSet results = stmt
						.executeQuery("SELECT *  WHERE CONTAINS (" + query
								+ ")");) {
			while (results.next()) {
				System.out.printf("%s, %s, %s%n", results.getString(1),
						results.getString(2), results.getString(3));
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {

		}

	}
}
