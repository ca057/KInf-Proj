package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

/**
 * Provides methods to check whether entries in database are already existent or
 * not.
 * 
 * @author Simon
 *
 */
public class Validation {

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

		try {
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

	boolean entryAlreadyInDatabase(int tradID, int quellenID, int personID,
			String table) {
		boolean result = true;

		String columnOne = getColumnName(table, 1);
		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);

		try {
			String querySql = String
					.format("SELECT %s, %s, %s FROM %s WHERE %1$s=? AND %2$s=? AND %3$s=?",
							columnOne, columnTwo, columnThree, table);
			stmt = con.prepareStatement(querySql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, tradID);
			stmt.setInt(2, quellenID);
			stmt.setInt(3, personID);

			ResultSet rs = stmt.executeQuery();

			con.setAutoCommit(false);

			/*
			 * Returns true if ResultSet is not empty, false otherwise
			 */
			result = rs.isBeforeFirst();

			con.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
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

		try {
			String querySql = String.format("SELECT * FROM %s", table);
			stmt = con.prepareStatement(querySql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			result = rsmd.getColumnName(i);

		} catch (SQLException e) {
			e.printStackTrace();
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
	 * Returns the highest ID already taken in a table of the schema HYLLEBLOMST
	 * 
	 * @param table
	 *            The table in which to look for highest given ID must be in the
	 *            format <code>HYLLEBLOMST.table_name</code>
	 * @return The maximum ID as <code>int</code>
	 */
	int getMaxID(String table) {
		int result = 0;

		try {
			String column = (table.replace("_", "") + "ID").replace(
					"hylleblomst.", "");
			String getMaxIDSQL = String.format("SELECT max(%s) FROM %s",
					column, table);
			stmt = con.prepareStatement(getMaxIDSQL,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery();

			con.setAutoCommit(false);

			rs.first();
			result += rs.getInt(1);

			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
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

		try {
			String column = getColumnName(table, 2);
			String querySql = String.format("SELECT * FROM %s WHERE %s=?",
					table, column);
			stmt = con.prepareStatement(querySql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, entry);

			ResultSet rs = stmt.executeQuery();

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
		int id = 0;

		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);

		try {
			String querySql = String.format(
					"SELECT * FROM %3$s WHERE %1$s=? AND %2$s=?", columnTwo,
					columnThree, table);
			stmt = con.prepareStatement(querySql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, entry);
			stmt.setInt(2, foreignKey);

			ResultSet rs = stmt.executeQuery();

			con.setAutoCommit(false);

			rs.next();
			id = rs.getInt(1);

			con.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	int getIDOfOrtAbweichungEntry(String entry, String anmerkung, String table) {
		int id = 0;

		String columnOne = getColumnName(table, 1);
		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);

		try {
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

			rs.next();
			id = rs.getInt(1);

			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}
}
