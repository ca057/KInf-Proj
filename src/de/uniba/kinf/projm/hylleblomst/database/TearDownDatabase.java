package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.UserKeys;

/**
 * The purpose of this class is to provide an easy and fast way to tear down an
 * existing database if this is necessary due to maintenance, limited file space
 * or other reasons.
 * 
 * @author Simon
 *
 */
public class TearDownDatabase {

	public static void main(String[] args) {
		try {
			new TearDownDatabase().run();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TearDownDatabase() {
	}

	public boolean run() throws SQLException {
		try (Connection con = DriverManager.getConnection(UserKeys.dbURL,
				UserKeys.adminUser, UserKeys.adminPassword)) {
			Statement stmt = con.createStatement();

			String[] tables = TableNameKeys.getAllTableNames();

			while (schemaExists()) {
				for (String table : tables) {
					stmt.executeUpdate(String.format("DROP TABLE %s", table));
				}
				stmt.executeUpdate(String.format("DROP SCHEMA %s RESTRICT",
						TableNameKeys.schemaName));
			}
			return true;
		} catch (SQLException e) {
			throw new SQLException("Database could not be torn down: ", e);
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
	 * @return {@code True} if database contains this schema, {@code false}
	 *         otherwise.
	 * @throws SQLException
	 *             If an error occurs while trying to query the existence of the
	 *             schema in question.
	 */
	private boolean schemaExists() throws SQLException {
		boolean result = false;

		try (Connection con = DriverManager.getConnection(UserKeys.dbURL,
				UserKeys.guestUser, UserKeys.guestPassword)) {
			PreparedStatement stmt = con.prepareStatement(
					"SELECT * FROM sys.schemas WHERE name=?",
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, TableNameKeys.schemaName);
			ResultSet rs = stmt.executeQuery();

			con.setAutoCommit(false);
			result = rs.isBeforeFirst();
			con.setAutoCommit(true);
		}

		return result;
	}
}
