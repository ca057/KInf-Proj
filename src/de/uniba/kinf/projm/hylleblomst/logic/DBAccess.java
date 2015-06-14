package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class DBAccess {
	public String dbURL;
	public String user;
	public String password;

	DBAccess(String dbURL, String user, String password) {
		this.dbURL = dbURL;
		this.user = user;
		this.password = password;
	}

	void startQuery(QueriesImpl queriesImpl, String query) throws SQLException {
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