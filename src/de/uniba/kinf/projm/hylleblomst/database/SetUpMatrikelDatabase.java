package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
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
		} catch (SetUpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SetUpMatrikelDatabase() {
	}

	public boolean run() throws SetUpException {
		try (Connection con = DriverManager.getConnection(UserKeys.dbURL,
				UserKeys.adminUser, UserKeys.adminPassword);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {
			int interrupt = 0;
			while (!allTablesExist()) {
				String[][] normTableInfo = getNormTables();
				for (int i = 0; i < normTableInfo.length; i++) {
					stmt.addBatch("CREATE TABLE" + normTableInfo[i][0] + " ("
							+ normTableInfo[i][1]
							+ " integer PRIMARY KEY NOT NULL, "
							+ normTableInfo[i][2] + " varchar(255)");
				}
				stmt.executeBatch();
				stmt.clearBatch();

				String[][] tradTableInfo = getTradTables();
				for (int i = 0; i < tradTableInfo.length; i++) {
					String tableName = tradTableInfo[i][0];
					String tableID = tradTableInfo[i][1];
					String tableAttribute = tradTableInfo[i][2];
					String tableFK = tradTableInfo[i][3];
					String tableRef = tradTableInfo[i][4];

					String sql = "CREAT TABLE" + tableName + " (" + tableID
							+ " integer PRIMARY KEY NOT NULL, "
							+ tableAttribute + " varchar(255), " + tableFK
							+ " integer NOT NULL, " + "FOREIGN KEY (" + tableFK
							+ ") REFERENCES " + tableRef + "(" + tableFK
							+ ") ON DELETE RESTRICT ON UPDATE RESTRICT";

					stmt.addBatch(sql);
				}
				stmt.executeBatch();
				stmt.clearBatch();

				String sqlOrtAbweichungNorm = "CREATE TABLE "
						+ TableNameKeys.ORT_ABWEICHUNG_NORM + "("
						+ ColumnNameKeys.ORT_ABWEICHUNG_NORM_ID
						+ " integer PRIMARY KEY NOT NULL, "
						+ ColumnNameKeys.ORT_ABWEICHUNG_NORM
						+ " varchar(255), "
						+ ColumnNameKeys.ORT_ABWEICHUNG_NORM_ANMERKUNG
						+ " varchar(255))";

				stmt.executeUpdate(sqlOrtAbweichungNorm);
				stmt.close();

				String[][] infoTableInfo = getInfoTables();
				for (int i = 0; i < infoTableInfo.length; i++) {
					String tableName = infoTableInfo[i][0];
					String refTableID = infoTableInfo[i][1];
					String srcTableID = infoTableInfo[i][2];
					String personTableID = infoTableInfo[i][3];
					String refTableName = infoTableInfo[i][4];
					String srcTableName = infoTableInfo[i][5];
					String personTableName = infoTableInfo[i][6];

					String sql = "CREATE TABLE " + tableName + " ("
							+ refTableID + " INTEGER NOT NULL, " + srcTableID
							+ " INTEGER NOT NULL, " + personTableID
							+ " INTEGER NOT NULL, " + "FOREIGN KEY ("
							+ refTableID + ") REFERENCES " + refTableName + "("
							+ refTableID
							+ ") ON DELETE RESTRICT ON UPDATE RESTRICT, "
							+ "FOREIGN KEY (" + srcTableID + ") REFERENCES "
							+ srcTableName + "(" + srcTableID
							+ ") ON DELETE RESTRICT ON UPDATE RESTRICT, "
							+ "FOREIGN KEY (" + personTableID + ") REFERENCES "
							+ personTableName + "(" + personTableID
							+ ") ON DELETE RESTRICT ON UPDATE RESTRICT) ";
					stmt.addBatch(sql);
				}
				stmt.executeBatch();
				stmt.clearBatch();

				interrupt++;
				if (interrupt >= 10) {
					throw new SetUpException(
							"Database could not be build, maybe some necessary information for setup is missing");
				}
			}
		} catch (SQLException e) {
			throw new SetUpException("Database could not be build: ", e);
		}

		return false;
	}

	private String[][] getInfoTables() {
		String[] fach = { TableNameKeys.FACH_INFO, ColumnNameKeys.FACH_TRAD_ID,
				ColumnNameKeys.QUELLEN_ID, ColumnNameKeys.PERSON_ID,
				TableNameKeys.FACH_TRAD, TableNameKeys.QUELLEN,
				TableNameKeys.PERSON };
		String[] vorname = { TableNameKeys.VORNAME_INFO,
				ColumnNameKeys.VORNAME_TRAD_ID, ColumnNameKeys.QUELLEN_ID,
				ColumnNameKeys.PERSON_ID, TableNameKeys.VORNAME_TRAD,
				TableNameKeys.QUELLEN, TableNameKeys.PERSON };
		String[] name = { TableNameKeys.NAME_INFO, ColumnNameKeys.NAME_TRAD_ID,
				ColumnNameKeys.QUELLEN_ID, ColumnNameKeys.PERSON_ID,
				TableNameKeys.NAME_TRAD, TableNameKeys.QUELLEN,
				TableNameKeys.PERSON };
		String[] ort = { TableNameKeys.ORT_INFO, ColumnNameKeys.ORT_TRAD_ID,
				ColumnNameKeys.QUELLEN_ID, ColumnNameKeys.PERSON_ID,
				TableNameKeys.ORT_TRAD, TableNameKeys.QUELLEN,
				TableNameKeys.PERSON };
		String[] seminar = { TableNameKeys.SEMINAR_INFO,
				ColumnNameKeys.SEMINAR_TRAD_ID, ColumnNameKeys.QUELLEN_ID,
				ColumnNameKeys.PERSON_ID, TableNameKeys.SEMINAR_TRAD,
				TableNameKeys.QUELLEN, TableNameKeys.PERSON };
		String[] wirtschaftslage = { TableNameKeys.WIRTSCHAFTSLAGE_INFO,
				ColumnNameKeys.WIRTSCHAFTSLAGE_TRAD_ID,
				ColumnNameKeys.QUELLEN_ID, ColumnNameKeys.PERSON_ID,
				TableNameKeys.WIRTSCHAFTSLAGE_TRAD, TableNameKeys.QUELLEN,
				TableNameKeys.PERSON };
		String[] zusaetze = { TableNameKeys.ZUSAETZE_INFO,
				ColumnNameKeys.ZUSAETZE_ID, ColumnNameKeys.QUELLEN_ID,
				ColumnNameKeys.PERSON_ID, TableNameKeys.ZUSAETZE,
				TableNameKeys.QUELLEN, TableNameKeys.PERSON };

		String[][] result = { fach, vorname, name, ort, seminar,
				wirtschaftslage, zusaetze };
		return result;
	}

	private String[][] getTradTables() {
		String[] anrede = { TableNameKeys.ANREDE_TRAD,
				ColumnNameKeys.ANREDE_TRAD_ID, ColumnNameKeys.ANREDE_TRAD,
				ColumnNameKeys.ANREDE_NORM_ID, TableNameKeys.ANREDE_NORM };
		String[] titel = { TableNameKeys.TITEL_TRAD,
				ColumnNameKeys.TITEL_TRAD_ID, ColumnNameKeys.TITEL_TRAD,
				ColumnNameKeys.TITEL_NORM_ID, TableNameKeys.TITEL_NORM };
		String[] name = { TableNameKeys.NAME_TRAD, ColumnNameKeys.NAME_TRAD_ID,
				ColumnNameKeys.NAME_TRAD, ColumnNameKeys.NAME_NORM_ID,
				TableNameKeys.NAME_NORM };
		String[] vorname = { TableNameKeys.VORNAME_TRAD,
				ColumnNameKeys.VORNAME_TRAD_ID, ColumnNameKeys.VORNAME_TRAD,
				ColumnNameKeys.VORNAME_NORM_ID, TableNameKeys.VORNAME_NORM };
		String[] ort = { TableNameKeys.ORT_TRAD, ColumnNameKeys.ORT_TRAD_ID,
				ColumnNameKeys.ORT_TRAD, ColumnNameKeys.ORT_NORM_ID,
				TableNameKeys.ORT_NORM };
		String[] seminar = { TableNameKeys.SEMINAR_TRAD,
				ColumnNameKeys.SEMINAR_TRAD_ID, ColumnNameKeys.SEMINAR_TRAD,
				ColumnNameKeys.SEMINAR_NORM_ID, TableNameKeys.SEMINAR_NORM };
		String[] wirtschaftslage = { TableNameKeys.WIRTSCHAFTSLAGE_TRAD,
				ColumnNameKeys.WIRTSCHAFTSLAGE_TRAD_ID,
				ColumnNameKeys.WIRTSCHAFTSLAGE_TRAD,
				ColumnNameKeys.WIRTSCHAFTSLAGE_NORM_ID,
				TableNameKeys.WIRTSCHAFTSLAGE_NORM };
		String[] ortNorm = { TableNameKeys.ORT_NORM,
				ColumnNameKeys.ORT_NORM_ID, ColumnNameKeys.ORT_NORM,
				ColumnNameKeys.ORT_ABWEICHUNG_NORM_ID,
				TableNameKeys.ORT_ABWEICHUNG_NORM };

		String[][] result = { anrede, titel, name, ort, vorname, seminar,
				wirtschaftslage, ortNorm };

		return result;
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
						ColumnNameKeys.WIRTSCHAFTSLAGE_NORM },
				{ TableNameKeys.ZUSAETZE, ColumnNameKeys.ZUSAETZE_ID,
						ColumnNameKeys.ZUSAETZE } };

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
