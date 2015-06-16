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

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	void startQuery(String query) throws SQLException {
		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement();
				ResultSet results = stmt.executeQuery(query)) {
			while (results.next()) {
				System.out.printf("%s, %s, %s, %s, %s, %s, %s, %s, %s %s%n",
						results.getString(1), results.getString(2),
						results.getString(3), results.getString(4),
						results.getString(5), results.getString(6),
						results.getString(7), results.getString(8),
						results.getString(9), results.getString(10));
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {

		}
	}

}