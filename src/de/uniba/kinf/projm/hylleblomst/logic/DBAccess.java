package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class DBAccess {
	private String dbURL;
	private String user;
	private String password;

	DBAccess(String dbURL, String user, String password) {
		this.dbURL = dbURL;
		this.user = user;
		this.password = password;
	}

	void setUser(String user) {
		this.user = user;
	}

	void setPassword(String password) {
		this.password = password;
	}

	void startQuery(String query, List<Object> inputs) throws SQLException {
		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				PreparedStatement stmt = con.prepareStatement(query,
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {
			int parameterIndex = 1;
			for (Object input : inputs) {
				stmt.setString(parameterIndex, "%" + (String) input + "%");
				parameterIndex++;
			}
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				System.out.printf("%s, %s, %s, %s%n", results.getString(1),
						results.getString(2), results.getString(3),
						results.getString(4));
			}
			new TableViewImpl().fillTable(results);
			results.close();
		}
	}
}