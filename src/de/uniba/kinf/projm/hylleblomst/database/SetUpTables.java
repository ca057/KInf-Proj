package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;

/**
 * This class has the sole purpose of setting up all tables required to import
 * project-specific data.
 * 
 * @author Simon
 *
 */
public class SetUpTables {

	public void run(String dbURL, String adminUser, String adminPassword)
			throws SetUpException {
		int interrupt = 0;
		try (Connection con = DriverManager.getConnection(dbURL, adminUser,
				adminPassword);
				PreparedStatement stmt = con.prepareStatement(
						"SELECT * FROM Hylleblomst.Person",
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {

			while (!allTablesExist(con)) {
				con.setAutoCommit(false);

				// Use a barrier to prevent possible infinite loop
				interrupt++;
				if (interrupt >= 10) {
					throw new SetUpException(
							"Tables could not be build, maybe some necessary information for setup is missing");
				}
				createSchema(stmt);

				// Set up simple Norm-tables which only hold an ID and one
				// attribute
				String[][] normTableInfo = getNormTables();
				for (int i = 0; i < normTableInfo.length; i++) {
					setUpNormTable(stmt, normTableInfo, i);
				}

				// Set up table Ort_Abweichung_Norm in Hylleblomst which is
				// also a norm table but contains one extra column
				setUpTaleOrtAbweichungNorm(stmt);

				// Set up Trad-tables which hold one ID, one attribute and
				// one FK ID
				String[][] tradTableInfo = getTradTables();
				for (int i = 0; i < tradTableInfo.length; i++) {
					setUpTradTable(stmt, tradTableInfo, i);
				}

				// Set up person table
				setUpTablePerson(stmt);

				// Set up Info-tables which represent a m:n:o relation
				String[][] infoTableInfo = getInfoTables();
				for (int i = 0; i < infoTableInfo.length; i++) {
					setUpInfoTable(stmt, infoTableInfo, i);
				}

			}
		} catch (SQLException e) {
			throw new SetUpException(e.getErrorCode()
					+ ": Tables could not be build: " + e.getMessage(), e);
		}
	}

	/**
	 * This method checks whether all tables that are supposed to be inserted
	 * already exist in the database
	 * 
	 * @param con
	 * 
	 * @return
	 * @throws SQLException
	 */
	private boolean allTablesExist(Connection con) throws SQLException {
		String[] tableNames = TableNameKeys.getAllTableNames();

		try (PreparedStatement stmt = con.prepareStatement(
				"SELECT TABLENAME FROM sys.systables",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery();) {

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

	private void setUpNormTable(PreparedStatement stmt,
			String[][] normTableInfo, int i) throws SetUpException {
		String tableName = normTableInfo[i][0];
		String tableID = normTableInfo[i][1];

		try {
			if (tableName.equals(TableNameKeys.ZUSAETZE)) {
				stmt.executeUpdate("CREATE TABLE " + tableName + " (" + tableID
						+ " integer PRIMARY KEY NOT NULL, "
						+ normTableInfo[i][2] + " varchar(5000))");
			} else {
				stmt.executeUpdate("CREATE TABLE " + tableName + " (" + tableID
						+ " integer PRIMARY KEY NOT NULL, "
						+ normTableInfo[i][2] + " varchar(500))");
			}
		} catch (SQLException e) {
			throw new SetUpException(tableName + " could not be build: "
					+ e.getMessage(), e);
		}
	}

	private void setUpTradTable(PreparedStatement stmt,
			String[][] tradTableInfo, int i) throws SetUpException {
		String tableName = tradTableInfo[i][0];
		String tableID = tradTableInfo[i][1];
		String tableAttribute = tradTableInfo[i][2];
		String tableFK = tradTableInfo[i][3];
		String tableRef = tradTableInfo[i][4];

		String sql = "CREATE TABLE " + tableName + " (" + tableID
				+ " integer PRIMARY KEY NOT NULL, " + tableAttribute
				+ " varchar(500), " + tableFK + " integer, " + "FOREIGN KEY ("
				+ tableFK + ") REFERENCES " + tableRef + "(" + tableFK
				+ ") ON DELETE RESTRICT ON UPDATE RESTRICT)";

		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new SetUpException(tableName + " could not be build: "
					+ e.getMessage(), e);
		}
	}

	private void setUpInfoTable(PreparedStatement stmt,
			String[][] infoTableInfo, int i) throws SetUpException {
		String tableName = infoTableInfo[i][0];
		String refTableID = infoTableInfo[i][1];
		String srcTableID = ColumnNameKeys.QUELLEN_ID;
		String personTableID = ColumnNameKeys.PERSON_ID;
		String refTableName = infoTableInfo[i][2];
		String srcTableName = TableNameKeys.QUELLEN;
		String personTableName = TableNameKeys.PERSON;

		String sql = "CREATE TABLE " + tableName + " (" + refTableID
				+ " INTEGER NOT NULL, " + srcTableID + " INTEGER NOT NULL, "
				+ personTableID + " INTEGER NOT NULL, " + "FOREIGN KEY ("
				+ refTableID + ") REFERENCES " + refTableName + "("
				+ refTableID + ") ON DELETE RESTRICT ON UPDATE RESTRICT, "
				+ "FOREIGN KEY (" + srcTableID + ") REFERENCES " + srcTableName
				+ "(" + srcTableID
				+ ") ON DELETE RESTRICT ON UPDATE RESTRICT, " + "FOREIGN KEY ("
				+ personTableID + ") REFERENCES " + personTableName + "("
				+ personTableID + ") ON DELETE RESTRICT ON UPDATE RESTRICT) ";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new SetUpException(tableName + " could not be build: "
					+ e.getMessage(), e);
		}
	}

	private void setUpTablePerson(PreparedStatement stmt) throws SetUpException {
		String sqlPerson = "CREATE TABLE " + TableNameKeys.PERSON + "("
				+ ColumnNameKeys.PERSON_ID + " integer PRIMARY KEY NOT NULL, "
				+ ColumnNameKeys.SEITE_ORIGINAL + " integer, "
				+ ColumnNameKeys.NUMMER_HESS + " integer, "
				+ ColumnNameKeys.JESUIT + " varchar(100), "
				+ ColumnNameKeys.ADLIG + " varchar(100), "
				+ ColumnNameKeys.DATUM + " date, "
				+ ColumnNameKeys.DATUMS_FELDER_GESETZT + " varchar(3), "
				+ ColumnNameKeys.STUDIENJAHR + " varchar(30), "
				+ ColumnNameKeys.STUDIENJAHR_INT + " integer, "
				+ ColumnNameKeys.GRADUIERT + " varchar(100), "
				+ ColumnNameKeys.ANMERKUNG + " varchar(255), "
				+ ColumnNameKeys.ANREDE_TRAD_ID + " integer, "
				+ ColumnNameKeys.FAKULTAETEN_ID + " integer, "
				+ ColumnNameKeys.FUNDORTE_ID + " integer, "
				+ ColumnNameKeys.TITEL_TRAD_ID + " integer, " + "FOREIGN KEY ("
				+ ColumnNameKeys.ANREDE_TRAD_ID + ") REFERENCES "
				+ TableNameKeys.ANREDE_TRAD + "("
				+ ColumnNameKeys.ANREDE_TRAD_ID
				+ ") ON DELETE RESTRICT ON UPDATE RESTRICT, " + "FOREIGN KEY ("
				+ ColumnNameKeys.FAKULTAETEN_ID + ") REFERENCES "
				+ TableNameKeys.FAKULTAETEN + "("
				+ ColumnNameKeys.FAKULTAETEN_ID
				+ ") ON DELETE RESTRICT ON UPDATE RESTRICT, " + "FOREIGN KEY ("
				+ ColumnNameKeys.FUNDORTE_ID + ") REFERENCES "
				+ TableNameKeys.FUNDORTE + "(" + ColumnNameKeys.FUNDORTE_ID
				+ ") ON DELETE RESTRICT ON UPDATE RESTRICT, " + "FOREIGN KEY ("
				+ ColumnNameKeys.TITEL_TRAD_ID + ") REFERENCES "
				+ TableNameKeys.TITEL_TRAD + "(" + ColumnNameKeys.TITEL_TRAD_ID
				+ ") ON DELETE RESTRICT ON UPDATE RESTRICT) ";
		try {
			stmt.executeUpdate(sqlPerson);
		} catch (SQLException e) {
			throw new SetUpException("Table person could not be set up: "
					+ e.getMessage(), e);
		}
	}

	private void setUpTaleOrtAbweichungNorm(PreparedStatement stmt)
			throws SetUpException {
		String sqlOrtAbweichungNorm = "CREATE TABLE "
				+ TableNameKeys.ORT_ABWEICHUNG_NORM + "("
				+ ColumnNameKeys.ORT_ABWEICHUNG_NORM_ID
				+ " integer PRIMARY KEY NOT NULL, "
				+ ColumnNameKeys.ORT_ABWEICHUNG_NORM + " varchar(255), "
				+ ColumnNameKeys.ORT_ABWEICHUNG_NORM_ANMERKUNG
				+ " varchar(255))";
		try {
			stmt.executeUpdate(sqlOrtAbweichungNorm);
		} catch (SQLException e) {
			throw new SetUpException(
					"Table OrtAbweichungNorm could not be build: "
							+ e.getMessage(), e);
		}
	}

	private void createSchema(PreparedStatement stmt) throws SetUpException {
		try {
			stmt.executeUpdate("CREATE SCHEMA hylleblomst");
		} catch (SQLException e) {
			throw new SetUpException("Schema could not be build: "
					+ e.getMessage(), e);
		}
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

	private String[][] getTradTables() {
		String[] ortNorm = { TableNameKeys.ORT_NORM,
				ColumnNameKeys.ORT_NORM_ID, ColumnNameKeys.ORT_NORM,
				ColumnNameKeys.ORT_ABWEICHUNG_NORM_ID,
				TableNameKeys.ORT_ABWEICHUNG_NORM };
		String[] anrede = { TableNameKeys.ANREDE_TRAD,
				ColumnNameKeys.ANREDE_TRAD_ID, ColumnNameKeys.ANREDE_TRAD,
				ColumnNameKeys.ANREDE_NORM_ID, TableNameKeys.ANREDE_NORM };
		String[] fach = { TableNameKeys.FACH_TRAD, ColumnNameKeys.FACH_TRAD_ID,
				ColumnNameKeys.FACH_TRAD, ColumnNameKeys.FACH_NORM_ID,
				TableNameKeys.FACH_NORM };
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

		String[][] result = { anrede, fach, titel, name, ortNorm, ort, vorname,
				seminar, wirtschaftslage };

		return result;
	}

	private String[][] getInfoTables() {
		String[] fach = { TableNameKeys.FACH_INFO, ColumnNameKeys.FACH_TRAD_ID,
				TableNameKeys.FACH_TRAD, };
		String[] vorname = { TableNameKeys.VORNAME_INFO,
				ColumnNameKeys.VORNAME_TRAD_ID, TableNameKeys.VORNAME_TRAD, };
		String[] name = { TableNameKeys.NAME_INFO, ColumnNameKeys.NAME_TRAD_ID,
				TableNameKeys.NAME_TRAD, };
		String[] ort = { TableNameKeys.ORT_INFO, ColumnNameKeys.ORT_TRAD_ID,
				TableNameKeys.ORT_TRAD, };
		String[] seminar = { TableNameKeys.SEMINAR_INFO,
				ColumnNameKeys.SEMINAR_TRAD_ID, TableNameKeys.SEMINAR_TRAD, };
		String[] wirtschaftslage = { TableNameKeys.WIRTSCHAFTSLAGE_INFO,
				ColumnNameKeys.WIRTSCHAFTSLAGE_TRAD_ID,
				TableNameKeys.WIRTSCHAFTSLAGE_TRAD };
		String[] zusaetze = { TableNameKeys.ZUSAETZE_INFO,
				ColumnNameKeys.ZUSAETZE_ID, TableNameKeys.ZUSAETZE, };

		String[][] result = { fach, vorname, name, ort, seminar,
				wirtschaftslage, zusaetze };
		return result;
	}
}
