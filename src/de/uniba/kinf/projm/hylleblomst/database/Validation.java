package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Validation {

	private String dbURL;
	private String password;
	private String user;

	public Validation(String dbURL, String user, String password) {

		this.dbURL = dbURL;
		this.user = user;
		this.password = password;
	}

	/**
	 * Checks if an ID is already in use in the database.
	 * 
	 * @return true if taken <br/>
	 *         false if new ID
	 */
	public static boolean isTakenID() {
		// TODO Implement: If person number is taken, return true. (via
		// SQL-query)
		return false;
	}

	/**
	 * Checks whether or not the given {@link String} entry already exists in a
	 * specified column of a table
	 * 
	 * @param entry
	 *            The String which is to be determined already existing in
	 *            database or not
	 * @param table
	 *            The table in which to search for entry
	 * @param column
	 *            The column in which to search for entry
	 * @return <code>True</code> if entry was found, <code>false</code>
	 *         otherwise
	 */
	boolean entryAlreadyInDatabase(String entry, String table, String column) {
		boolean result = true;

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			String querySql = String.format(
					"SELECT %1$s FROM %2$s WHERE %1$s='%3$s'", column, table,
					entry);
			ResultSet rs = stmt.executeQuery(querySql);

			con.setAutoCommit(false);
			/*
			 * Returns true if ResultSet is not empty, false otherwise
			 */

			result = rs.isBeforeFirst();

			con.setAutoCommit(true);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Returns the highest ID already taken in a table of the schema HYLLEBLOMST
	 * 
	 * @param tableName
	 *            The table in which to look for highest given ID Must be in the
	 *            format <code>HYLLEBLOMST.table_name</code>
	 * @return The maximum ID as <code>int</code>
	 */
	int getMaxID(String tableName) {
		int result = 0;

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			String columnName = (tableName.replace("_", "") + "ID").replace(
					"hylleblomst.", "");
			String getMaxIDSQL = String.format("SELECT max(%s) FROM %s",
					columnName, tableName);
			ResultSet rs = stmt.executeQuery(getMaxIDSQL);

			con.setAutoCommit(false);

			rs.first();
			result += rs.getInt(1);

			con.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
