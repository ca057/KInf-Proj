package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class QueriesImpl implements Queries {
	String dbURL = "jdbc:derby:/db/MyDB";
	String user = "admin";
	String password = "password";

	@Override
	public void fullTextSearch(String query) {
		startQuery(query);
	}

	@Override
	public void extendedSearch(Map<Enum<ColumnKeys>, Object> map) {
		// TODO Auto-generated method stub

	}

	private void startQuery(String query) {
		try {

			Connection con = DriverManager.getConnection(dbURL, user, password);
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery("SELECT "
					+ query.toUpperCase() + " FROM *");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
