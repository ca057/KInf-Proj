package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBAccess {
	private String dbURL;
	private String user;
	private String password;

	public DBAccess(String dbURL, String user, String password) {
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

	public ResultSet startQuery(String query, ArrayList<String> inputs) throws SQLException {
		try (Connection con = DriverManager.getConnection(dbURL, user, password);
				PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {
			int parameterIndex = 1;
			for (Object input : inputs) {
				stmt.setString(parameterIndex, "%" + (String) input + "%");
				parameterIndex++;
			}
			ResultSet results = stmt.executeQuery();

			ResultSetMetaData rsmd = stmt.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

			while (results.next()) {
				StringBuilder string2 = new StringBuilder();
				for (int x = 0; x < columnsNumber; x++) {
					string2.append(results.getString((x + 1)) + ", ");
				}
				System.out.println(string2.substring(0, string2.length() - 2).toString());
			}
			return results;
		}
	}
}