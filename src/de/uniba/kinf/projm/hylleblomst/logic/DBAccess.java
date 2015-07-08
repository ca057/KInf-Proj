package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;

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

	public CachedRowSet startQuery(String query, ArrayList<String> inputs) throws SQLException {
		CachedRowSet crs = new CachedRowSetImpl();
		try (Connection con = DriverManager.getConnection(dbURL, user, password);
				PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {
			int parameterIndex = 1;
			if (inputs != null) {
				for (String input : inputs) {
					stmt.setString(parameterIndex, input);
					parameterIndex++;
				}
			}

			con.setAutoCommit(false);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				System.out.println(results.getString(1));
			} else {
				System.out.println("Problem.");
			}

			crs.populate(results);
			con.setAutoCommit(true);

			ResultSetMetaData rsmd = stmt.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (results.next()) {
				StringBuilder string2 = new StringBuilder();
				for (int x = 0; x < columnsNumber; x++) {
					string2.append(results.getString((x + 1)) + ", ");
				}
				System.out.println(string2.substring(0, string2.length() - 2).toString());
			}
			for (int i = 1; i < results.getFetchSize(); i++) {
				results.getString(i);
				String blub = results.getString("DATUM_INT");
				String date = results.getString(ColumnNameKeys.DATUM);
				if ("010".equals(blub)) {
					date = date.substring(0, 4) + "X" + date.substring(7, 9);
				} else if ("001".equals(blub)) {
					date = date.substring(0, 7) + "X";
				} else if ("011".equals(blub)) {
					date = date.substring(0, 4) + "X.X";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return crs;
	}
}