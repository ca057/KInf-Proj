package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;

/**
 * The purpose of this class is to provide an easy and fast way to tear down the
 * information in an existing database if this is necessary due to maintenance,
 * limited file space or other reasons.
 * 
 * @author Simon
 *
 */
public class TearDownTables {

	public boolean run(String dbURL, String adminUser, String adminPassword)
			throws SetUpException {
		int interrupt = 0;

		try (Connection con = DriverManager.getConnection(dbURL, adminUser,
				adminPassword); Statement stmt = con.createStatement();) {

			String[] tables = TableNameKeys.getAllTableNames();

			while (schemaExists(con)) {
				// Use a barrier to prevent possible infinite loop
				interrupt++;
				if (interrupt >= 10) {
					throw new SetUpException(
							"Tables could not be torn down, maybe some necessary information for setup is missing");
				}

				for (String table : tables) {
					try {
						stmt.executeUpdate(String
								.format("DROP TABLE %s", table));
					} catch (SQLException e) {
						// empty by intention
					}
				}
				try {
					stmt.executeUpdate(String.format("DROP SCHEMA %s RESTRICT",
							TableNameKeys.SCHEMA_NAME));
				} catch (Exception e) {
					// empty by intention
				}
			}
			return true;
		} catch (SQLException e) {
			throw new SetUpException("Database could not be torn down: ", e);
		}
	}

	/**
	 * Queries a database whether it contains a schema holding data from the
	 * projects specified CSV-file.
	 * 
	 * This method is used in this context as due to database-settings a schema
	 * may only be dropped if it does not contain any more data. Furthermore, a
	 * schema that does not exist can not only hold any data.
	 * 
	 * @param con
	 *            The {@code Connection} to the database
	 * 
	 * @return {@code True} if database contains this schema, {@code false}
	 *         otherwise.
	 * @throws SQLException
	 *             If an error occurs while trying to query the existence of the
	 *             schema in question.
	 */
	private boolean schemaExists(Connection con) throws SQLException {
		boolean result = true;

		try (PreparedStatement stmt = con.prepareStatement(
				"SELECT * FROM sys.sysschemas WHERE SCHEMANAME=?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			stmt.setString(1, TableNameKeys.SCHEMA_NAME.toUpperCase());

			try (ResultSet rs = stmt.executeQuery();) {

				con.setAutoCommit(false);
				result = rs.isBeforeFirst();
				con.setAutoCommit(true);
			}
		}

		return result;
	}
}
