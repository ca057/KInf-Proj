package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

/**
 * Provides methods to check whether entries in database are already existent or
 * not.
 * 
 * @author Simon
 *
 */
public class Validation {

	private String dbURL;
	private String password;
	private String user;

	private Connection con;
	private PreparedStatement stmt;

	/**
	 * Constructor for class validation.
	 * 
	 * @param dbURL
	 *            A String that contains the directory of the database which is
	 *            to be validated
	 * @param user
	 *            The username provided as String
	 * @param password
	 *            The password of the specified user to access database
	 */
	protected Validation(String dbURL, String user, String password) {
		this.dbURL = dbURL;
		this.user = user;
		this.password = password;

		try {
			this.con = DriverManager.getConnection(dbURL, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the ID of a person is already in use in the database.
	 * 
	 * @return true if taken <br/>
	 *         false if new ID is necessary
	 */
	boolean personIDIsTaken(int personID) throws ImportException {
		boolean result = true;

		try {
			stmt = con.prepareStatement(
					"SELECT PersonID FROM hylleblomst.person WHERE PersonID=?",
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, personID);

			ResultSet rs = stmt.executeQuery();

			con.setAutoCommit(false);
			/*
			 * Returns true if ResultSet is not empty, false otherwise
			 */
			result = rs.isBeforeFirst();
			con.setAutoCommit(true);

		} catch (SQLException e) {
			throw new ImportException("PersonID could not be validated: "
					+ e.getSQLState());
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
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
	 * @throws ImportException
	 */
	boolean entryAlreadyInDatabase(String entry, String table)
			throws ImportException {
		boolean result = true;

		String column = getColumnName(table, 2);

		try {
			stmt = con.prepareStatement(String.format(
					"SELECT %s FROM %s WHERE %1$s=?", column, table),
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, entry);

			ResultSet rs = stmt.executeQuery();

			con.setAutoCommit(false);
			/*
			 * Returns true if ResultSet is not empty, false otherwise
			 */
			result = rs.isBeforeFirst();
			con.setAutoCommit(true);

		} catch (SQLException e) {
			throw new ImportException("PersonID could not be validated: "
					+ e.getSQLState());
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	boolean entryAlreadyInDatabase(String entry, int foreignKey, String table)
			throws ImportException {
		boolean result = true;

		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);

		try {
			stmt = con.prepareStatement(String.format(
					"SELECT %s, %s FROM %s WHERE %1$s=? AND %2$s=?", columnTwo,
					columnThree, table), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, entry);
			stmt.setInt(2, foreignKey);

			ResultSet rs = stmt.executeQuery();

			con.setAutoCommit(false);

			/*
			 * Returns true if ResultSet is not empty, false otherwise
			 */
			result = rs.isBeforeFirst();
			con.setAutoCommit(true);

		} catch (SQLException e) {
			throw new ImportException("PersonID could not be validated: "
					+ e.getSQLState());
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	boolean entryAlreadyInDatabase(String entry, String anmerkung, String table) {
		boolean result = true;

		String columnOne = getColumnName(table, 1);
		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);) {
			String sqlQuery = String.format(
					"SELECT %2$s FROM %1$s WHERE %3$s=? AND %4$s=?", table,
					columnOne, columnTwo, columnThree);
			PreparedStatement stmt = con.prepareStatement(sqlQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			stmt.setString(1, entry);
			stmt.setString(2, anmerkung);

			ResultSet rs = stmt.executeQuery();

			con.setAutoCommit(false);

			result = rs.isBeforeFirst();

			con.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	boolean entryAlreadyInDatabase(int tradID, int quellenID, int personID,
			String table) {
		boolean result = true;

		String columnOne = getColumnName(table, 1);
		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			String querySql = String
					.format("SELECT %2$s, %3$s, %4$s FROM %1$s WHERE %2$s=%5$d AND %3$s=%6$d AND %4$s=%7$d",
							table, columnOne, columnTwo, columnThree, tradID,
							quellenID, personID);
			ResultSet rs = stmt.executeQuery(querySql);

			con.setAutoCommit(false);

			/*
			 * Returns true if ResultSet is not empty, false otherwise
			 */
			result = rs.isBeforeFirst();

			con.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Retrieves the name of a specified column in a table
	 * 
	 * @param table
	 *            The table in which the column exists
	 * @param i
	 *            The index of the column
	 * @return The name of the column as {@link String}
	 */
	private String getColumnName(String table, int i) {
		String result = "";

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			String querySql = String.format("SELECT * FROM %s", table);
			ResultSet rs = stmt.executeQuery(querySql);
			ResultSetMetaData rsmd = rs.getMetaData();

			result = rsmd.getColumnName(i);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Returns the highest ID already taken in a table of the schema HYLLEBLOMST
	 * 
	 * @param table
	 *            The table in which to look for highest given ID must be in the
	 *            format <code>HYLLEBLOMST.table_name</code>
	 * @return The maximum ID as <code>int</code>
	 */
	int getMaxID(String table) {
		int result = 0;

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			String column = (table.replace("_", "") + "ID").replace(
					"hylleblomst.", "");
			String getMaxIDSQL = String.format("SELECT max(%s) FROM %s",
					column, table);
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

	/**
	 * Gets the ID of a pre-existent entry in a specified table
	 * 
	 * @param entry
	 *            The entry which's ID is search
	 * @param table
	 *            The table in which to look for the ID
	 * @return The ID of the searched entry if existent in specified table,
	 *         <code>0</code> otherwise
	 */
	int getIDOfEntry(String entry, String table) {
		int id = 0;

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			String column = getColumnName(table, 2);
			String querySql = String.format(
					"SELECT * FROM %2$s WHERE %1$s='%3$s'", column, table,
					entry);
			ResultSet rs = stmt.executeQuery(querySql);

			con.setAutoCommit(false);

			rs.next();
			id = rs.getInt(1);

			con.setAutoCommit(true);

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return id;
	}

	int getIDOfEntry(String entry, int foreignKey, String table) {
		// TODO Auto-generated method stub
		int id = 0;

		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			String querySql = String.format(
					"SELECT * FROM %1$s WHERE %2$s='%4$s' AND %3$s=%5$d",
					table, columnTwo, columnThree, entry, foreignKey);
			ResultSet rs = stmt.executeQuery(querySql);

			con.setAutoCommit(false);

			rs.next();
			id = rs.getInt(1);

			con.setAutoCommit(true);

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return id;
	}

	int getIDOfEntry(String entry, String anmerkung, String table) {
		int id = 0;

		String columnOne = getColumnName(table, 1);
		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);) {
			String sqlQuery = String.format(
					"SELECT %2$s FROM %1$s WHERE %3$s=? AND %4$s=?", table,
					columnOne, columnTwo, columnThree);
			PreparedStatement stmt = con.prepareStatement(sqlQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			stmt.setString(1, entry);
			stmt.setString(2, anmerkung);

			ResultSet rs = stmt.executeQuery(sqlQuery);
			con.setAutoCommit(false);

			rs.next();
			id = rs.getInt(1);

			con.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return id;
	}
}
