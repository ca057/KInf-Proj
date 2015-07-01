package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
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
				String[][] normTableInfo = getNormTables();
				for (int i = 0; i < normTableInfo.length; i++) {
					stmt.addBatch("CREATE TABLE" + normTableInfo[i][0] + " ("
							+ normTableInfo[i][1]
							+ " integer PRIMARY KEY NOT NULL, "
							+ normTableInfo[i][2] + " varchar(255)");
				}
				stmt.executeBatch();
				stmt.clearBatch();

			}
		} catch (SQLException e) {
			throw new SQLException("Database could not be build: ", e);
		}

		return false;
	}

	private String[][] getNormTables() {
		String[][] result = {
				{ TableNameKeys.ANREDE_NORM, ColumnNameKeys.ANREDE_NORM_ID,
						ColumnNameKeys.ANREDE_NORM },
				{ TableNameKeys.FACH_NORM, ColumnNameKeys.FACH_NORM_ID,
						ColumnNameKeys.FACH_NORM },
				{ TableNameKeys.FAKULTAETEN, ColumnNameKeys.FAKULTAETEN_ID,
						ColumnNameKeys.FAKULTAETEN_NORM },
				{ TableNameKeys.FUNDORTE, ColumnNameKeys.FUNDORTE_ID,
						ColumnNameKeys.FUNDORTE_NORM },
				{ TableNameKeys.VORNAME_NORM, ColumnNameKeys.VORNAME_NORM_ID,
						ColumnNameKeys.VORNAME_NORM },
				{ TableNameKeys.NAME_NORM, ColumnNameKeys.NAME_NORM_ID,
						ColumnNameKeys.NAME_NORM },
				{ TableNameKeys.QUELLEN, ColumnNameKeys.QUELLEN_ID,
						ColumnNameKeys.QUELLEN_NAME },
				{ TableNameKeys.SEMINAR_NORM, ColumnNameKeys.SEMINAR_NORM_ID,
						ColumnNameKeys.SEMINAR_NORM },
				{ TableNameKeys.TITEL_NORM, ColumnNameKeys.TITEL_NORM_ID,
						ColumnNameKeys.TITEL_NORM },
				{ TableNameKeys.WIRTSCHAFTSLAGE_NORM,
						ColumnNameKeys.WIRTSCHAFTSLAGE_NORM_ID,
						ColumnNameKeys.WIRTSCHAFTSLAGE_NORM } };

		return result;
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
