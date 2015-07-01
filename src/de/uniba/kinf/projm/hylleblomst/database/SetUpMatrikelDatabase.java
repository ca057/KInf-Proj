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
 * This class has the sole purpose of setting up a database with all tables
 * required to import project-specific data.
 * 
 * @author Simon
 *
 */
public class SetUpMatrikelDatabase {

	public static void main(String[] args) throws SQLException {

		try {
			new SetUpMatrikelDatabase().run();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SetUpMatrikelDatabase() {
	}

	public boolean run() throws SQLException {
		try (Connection con = DriverManager.getConnection(UserKeys.dbURL,
				UserKeys.adminUser, UserKeys.adminPassword);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {
			while (!allTablesExist()) {
				// TODO insert all tables
			}
		} catch (SQLException e) {
			throw new SQLException("Database could not be build: ", e);
		}

		return false;
	}

	/**
	 * This method checks whether all tables that are supposed to be inserted
	 * already exist in the database
	 * 
	 * @return
	 * @throws SQLException
	 */
	private boolean allTablesExist() throws SQLException {
		String[] tableNames = TableNameKeys.getAllTableNames();

		try (Connection con = DriverManager.getConnection(UserKeys.dbURL,
				UserKeys.adminUser, UserKeys.guestPassword);
				PreparedStatement stmt = con.prepareStatement(
						"SELECT TABLENAME FROM sys.systables",
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {

			ResultSet rs = stmt.executeQuery();

			con.setAutoCommit(false);

			for (String tableName : tableNames) {
				// We assume that '.' is not a valid character in any table's
				// name
				if (tableName.indexOf('.') != -1) {
					tableName = tableName.substring(tableName.indexOf('.') + 1)
							.toUpperCase();
				}
				boolean doesTableExist = false;
				for (; rs.next();) {
					if (rs.getString(1).equals(tableName)) {
						doesTableExist = true;
						rs.beforeFirst();
						break;
					}
				}
				if (!doesTableExist) {
					con.setAutoCommit(true);
					return false;
				}

			}
			con.setAutoCommit(true);
		}

		return true;
	}
}
