package de.uniba.kinf.projm.hylleblomst.dataImport.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;
import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;

/**
 * Provides methods to check whether entries in database are already existent or
 * not.
 * 
 * @author Simon
 *
 */
class DataValidation {

	private Connection con;

	/**
	 * Constructor for class validation.
	 * 
	 * @param con
	 *            The {@link Connection} to use to access database.
	 */
	protected DataValidation(Connection con) {
		this.con = con;
	}

	/**
	 * Checks if the ID of a person is already in use in the database.
	 * 
	 * @return true if taken <br/>
	 *         false if new ID is necessary
	 */
	boolean personIDIsTaken(int personID) throws ImportException {
		boolean result = true;
		String personIdColumn = ColumnNameKeys.PERSON_ID;

		try (PreparedStatement stmt = con.prepareStatement("SELECT "
				+ personIdColumn + " FROM hylleblomst.person WHERE "
				+ personIdColumn + "=?", ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);) {
			stmt.setInt(1, personID);

			try (ResultSet rs = stmt.executeQuery();) {

				con.setAutoCommit(false);
				result = rs.isBeforeFirst();
				con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new ImportException(personIdColumn
					+ " could not be validated: " + e.getSQLState());
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
	 * @return <code>True</code> if entry was found, <code>false</code>
	 *         otherwise
	 * @throws ImportException
	 */
	boolean entryAlreadyInDatabase(String entry, String table)
			throws ImportException {
		boolean result = true;

		String column = getColumnName(table, 2);

		try (PreparedStatement stmt = con.prepareStatement(
				String.format("SELECT %s FROM %s WHERE %1$s=?", column, table),
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
			stmt.setString(1, entry);

			try (ResultSet rs = stmt.executeQuery();) {

				con.setAutoCommit(false);
				result = rs.isBeforeFirst();
				con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new ImportException("Could not be validated: " + entry
					+ " in " + table + "\n" + e.getSQLState());
		}

		return result;
	}

	/**
	 * Checks whether or not the given combination of {@code String} and foreign
	 * key already exists in the given table.
	 * 
	 * @param entry
	 *            The attribute to search.
	 * @param foreignKey
	 *            The associated foreign key.
	 * @param table
	 *            The table in which to look.
	 * @return <code>True</code> if combination already exists,
	 *         <code>false</code> otherwise.
	 * @throws ImportException
	 */
	boolean entryAlreadyInDatabase(String entry, int foreignKey, String table)
			throws ImportException {
		boolean result = true;

		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);

		try (PreparedStatement stmt = con.prepareStatement(String.format(
				"SELECT %s, %s FROM %s WHERE %1$s=? AND %2$s=?", columnTwo,
				columnThree, table), ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);) {
			stmt.setString(1, entry);
			stmt.setInt(2, foreignKey);

			try (ResultSet rs = stmt.executeQuery();) {

				con.setAutoCommit(false);
				// Returns true if ResultSet is not empty, false otherwise
				result = rs.isBeforeFirst();
				con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new ImportException("PersonID could not be validated: "
					+ e.getSQLState());
		}
		return result;
	}

	/**
	 * Checks whether or not the given combination of two {@code String}s is
	 * already in the given table.
	 * 
	 * <b>Note</b> In our use case this method is exclusively used to check for
	 * double entries in the table OrtAbweichungNorm.
	 * 
	 * @param entry
	 *            The entry to look for.
	 * @param additionalNote
	 *            The sometimes occurring additional note.
	 * @param table
	 *            The table in which to look for.
	 * @return <code>True</code> if combination already exists,
	 *         <code>false</code> otherwise.
	 * @throws ImportException
	 */
	boolean entryAlreadyInDatabase(String entry, String additionalNote,
			String table) throws ImportException {
		boolean result = true;

		String columnOne = getColumnName(table, 1);
		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);
		String sqlQuery = String.format(
				"SELECT %2$s FROM %1$s WHERE %3$s=? AND %4$s=?", table,
				columnOne, columnTwo, columnThree);

		try (PreparedStatement stmt = con.prepareStatement(sqlQuery,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, entry);
			stmt.setString(2, additionalNote);

			try (ResultSet rs = stmt.executeQuery();) {

				con.setAutoCommit(false);
				result = rs.isBeforeFirst();
				con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new ImportException("Could not be validated: " + table + "\n"
					+ e.getSQLState());
		}

		return result;
	}

	/**
	 * Checks whether or not the given combination of IDs already exists in a
	 * m:n:o-table.
	 * 
	 * @param tradID
	 *            The ID of a passed form of an entry.
	 * @param quellenID
	 *            The ID of the source in which to find the passed form.
	 * @param personID
	 *            The ID of the person with which the passed form is associated.
	 * @param table
	 *            The table in which to look for combination.
	 * @return <code>True</code> if combination of IDs already exists,
	 *         <code>false</code> otherwise.
	 * @throws ImportException
	 */
	boolean entryAlreadyInDatabase(int tradID, int quellenID, int personID,
			String table) throws ImportException {
		boolean result = true;

		String columnOne = getColumnName(table, 1);
		String columnTwo = ColumnNameKeys.QUELLEN_ID;
		String columnThree = ColumnNameKeys.PERSON_ID;
		String querySql = String.format(
				"SELECT %s, %s, %s FROM %s WHERE %1$s=? AND %2$s=? AND %3$s=?",
				columnOne, columnTwo, columnThree, table);

		try (PreparedStatement stmt = con.prepareStatement(querySql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
			stmt.setInt(1, tradID);
			stmt.setInt(2, quellenID);
			stmt.setInt(3, personID);

			try (ResultSet rs = stmt.executeQuery();) {

				con.setAutoCommit(false);
				result = rs.isBeforeFirst();
				con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new ImportException("Could not be validated: " + table + "\n"
					+ e.getSQLState());
		}

		return result;
	}

	/**
	 * Retrieves the name of a specified column in a table.
	 * 
	 * <b>Note:</b>
	 * 
	 * This way of retrieving column names stems from early development and
	 * would be replaced in later versions to take advantage of column name
	 * constants and enhance possible extensions. Yet, as the program runs just
	 * fine, we don't see enough reason to adjust the usage of this method for
	 * the moment.
	 * 
	 * @param table
	 *            The table in which the column exists
	 * @param i
	 *            The index of the column
	 * @return The name of the column as {@link String}
	 */
	private String getColumnName(String table, int i) {
		String result = "";
		String querySql = String.format("SELECT * FROM %s", table);

		try (PreparedStatement stmt = con.prepareStatement(querySql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			try (ResultSet rs = stmt.executeQuery();) {
				ResultSetMetaData rsmd = rs.getMetaData();

				result = rsmd.getColumnName(i);
			}
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

		String column = (table.replace("_", "") + "ID").replace("hylleblomst.",
				"");
		String getMaxIDSQL = String.format("SELECT max(%s) FROM %s", column,
				table);

		try (PreparedStatement stmt = con.prepareStatement(getMaxIDSQL,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
			try (ResultSet rs = stmt.executeQuery();) {

				con.setAutoCommit(false);
				rs.first();
				result += rs.getInt(1);
				con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Gets the ID of a pre-existent entry in a specified table.
	 * 
	 * @param entry
	 *            The entry which's ID is search
	 * @param table
	 *            The table in which to look for the ID
	 * @return The ID of the searched entry if existent in specified table,
	 *         <code>0</code> otherwise
	 * @throws ImportException
	 */
	int getIDOfEntry(String entry, String table) throws ImportException {
		int id = 0;
		String column = getColumnName(table, 2);
		String querySql = String.format("SELECT * FROM %s WHERE %s=?", table,
				column);

		try (PreparedStatement stmt = con.prepareStatement(querySql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, entry);
			try (ResultSet rs = stmt.executeQuery();) {

				con.setAutoCommit(false);
				rs.next();
				id = rs.getInt(1);
				con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new ImportException("Wasn't able to fetch ID: " + table
					+ "\n" + e.getSQLState());
		}
		return id;
	}

	/**
	 * Gets the ID of a combination of an attribute and a foreign key from a
	 * specified table.
	 * 
	 * @param entry
	 *            The attribute.
	 * @param foreignKey
	 *            The foreign key.
	 * @param table
	 *            The table in which to search.
	 * @return
	 */
	int getIDOfEntry(String entry, int foreignKey, String table) {
		int id = 0;

		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);
		String querySql = String.format(
				"SELECT * FROM %3$s WHERE %1$s=? AND %2$s=?", columnTwo,
				columnThree, table);

		try (PreparedStatement stmt = con.prepareStatement(querySql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, entry);
			stmt.setInt(2, foreignKey);

			try (ResultSet rs = stmt.executeQuery();) {

				con.setAutoCommit(false);
				rs.next();
				id = rs.getInt(1);
				con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Get the ID of a combination of a location and an additional note.
	 * 
	 * @param entry
	 *            The entry to look for.
	 * @param additionalNote
	 *            The additional note that accompanies the entry.
	 * @param table
	 *            The table in which to look for.
	 * @return <code>True</code> if entry already exists, <code>false</code>
	 *         otherwise.
	 */
	int getIDOfOrtAbweichungEntry(String entry, String additionalNote,
			String table) {
		int id = 0;

		String columnOne = getColumnName(table, 1);
		String columnTwo = getColumnName(table, 2);
		String columnThree = getColumnName(table, 3);
		String sqlQuery = String.format(
				"SELECT %2$s FROM %1$s WHERE %3$s=? AND %4$s=?", table,
				columnOne, columnTwo, columnThree);

		try (PreparedStatement stmt = con.prepareStatement(sqlQuery,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, entry);
			stmt.setString(2, additionalNote);

			try (ResultSet rs = stmt.executeQuery();) {

				con.setAutoCommit(false);
				rs.next();
				id = rs.getInt(1);
				con.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return id;
	}

}
