package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

public class ImportDatabaseImpl implements ImportDatabase {

	private Validation validation;

	private String dbURL;
	private String user;
	private String password;

	private final int[] quellenID = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	private final String[] quellen = { "Standard", "HS B (AUB, I 11)",
			"HS C (AUB, I 13/1)", "HS D (AUB, I 13/2)", "HS E (AUB, I 9)",
			"HS F (AUB, I 8)", "HS G (AUB, I 6)",
			"HS H (AEB, Rep. I, Nr. 321)", "HS I (SB Bamberg, Msc.Add.3a)",
			"HS J (SB Bamberg, Msc.Add.3)", "AUB, V E 38" };

	private String insertSql = "INSERT into ";

	public ImportDatabaseImpl(String dbURL, String user, String password)
			throws ImportException {
		this.dbURL = dbURL;
		if (!user.equals("admin")) {
			throw new ImportException("No authorization for importing data");
		} else {
			this.user = user;
		}
		this.password = password;

		validation = new Validation(dbURL, user, password);
	}

	@Override
	public void importData(List<String[]> rows) throws ImportException {
		System.out.println("Kommt in ImportDatabaseImpl an");

		for (int i = 0; i < quellen.length; i++) {
			insertIntoTableOneAttributeNoForeignKeys("quellen", quellen[i]);
		}

		rows = rows.subList(1, rows.size());

		for (String[] strings : rows) {
			int personID = Integer.parseInt(strings[0]);

			int anredeNormID = 0;
			int anredeTradID = 0;
			int fakultaetenID = 0;
			int fundortID = 0;
			int titelNormID = 0;
			int titelTradID = 0;

			int vornameNormID = 0;
			int fachNormID = 0;
			int wirtschaftslageNormID = 0;
			int seminarNormID = 0;

			int vornameTradID;

			if (!strings[4].equals("")) {
				anredeNormID = insertIntoTableOneAttributeNoForeignKeys(
						"anrede_norm", strings[4]);
			}
			if (!strings[3].equals("")) {
				anredeTradID = insertIntoTableOneAttributeOneForeignKey(
						"anrede_trad", strings[3], anredeNormID);
			}
			if (!strings[47].equals("")) {
				fakultaetenID = insertIntoTableOneAttributeNoForeignKeys(
						"fakultaeten", strings[47]);
			}
			if (!strings[77].equals("")) {
				fundortID = insertIntoTableOneAttributeNoForeignKeys(
						"fundorte_norm", strings[77]);
			}
			if (!strings[65].equals("")) {
				titelNormID = insertIntoTableOneAttributeNoForeignKeys(
						"titel_norm", strings[65]);
			}
			if (!strings[64].equals("")) {
				titelTradID = insertIntoTableOneAttributeOneForeignKey(
						"titel_trad", strings[64], titelNormID);
			}
			// TODO Person can now be inserted

			if (!strings[6].equals("")) {
				vornameNormID = insertIntoTableOneAttributeNoForeignKeys(
						"vorname_norm", strings[6]);
			}
			if (!strings[5].equals("")) {
				vornameTradID = insertIntoTableOneAttributeOneForeignKey(
						"vorname_trad", strings[5], vornameNormID);
				// insertIntoTableNoAttributesThreeForeignKeys("vorname_info",
				// vornameTradID, quellenID[0], personID);
			}
			if (!strings[18].equals("")) {
				// insertNameIntoSimpleTable("name_norm", strings[47]);
			}
			if (!strings[42].equals("")) {
				// insertOrtAbweichungNorm(strings[42]);
			}

			if (!strings[49].equals("")) {
				fachNormID = insertIntoTableOneAttributeNoForeignKeys(
						"fach_norm", strings[49]);
			}
			if (!strings[48].equals("")) {
				insertIntoTableOneAttributeOneForeignKey("fach_trad",
						strings[48], fachNormID);
			}
			if (!strings[52].equals("")) {
				wirtschaftslageNormID = insertIntoTableOneAttributeNoForeignKeys(
						"wirtschaftslage_norm", strings[52]);
			}
			if (!strings[51].equals("")) {
				insertIntoTableOneAttributeOneForeignKey(
						"wirtschaftslage_trad", strings[51],
						wirtschaftslageNormID);
			}
			if (!strings[57].equals("")) {
				seminarNormID = insertIntoTableOneAttributeNoForeignKeys(
						"seminar_norm", strings[57]);
			}
			if (!strings[56].equals("")) {
				insertIntoTableOneAttributeOneForeignKey("seminar_trad",
						strings[56], seminarNormID);
			}

		}
	}

	// TODO Erweiterung um Anmerkungen
	private boolean insertOrtAbweichungNorm(String nameNorm)
			throws ImportException {
		String table = "hylleblomst.Ort_Abweichung_Norm";
		String column = "Name";

		if (!validation.entryAlreadyInDatabase(nameNorm, table)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password); Statement stmt = con.createStatement();) {

				int id = validation.getMaxID(table) + 1;

				String insertStmt = String.format(
						"%1$s %2$s values(%3$d, '%4$s')", insertSql, table, id,
						nameNorm);

				stmt.execute(insertStmt);

				return true;
			} catch (SQLException e) {
				throw new ImportException(
						"A AbweichungNorm could not be inserted"
								+ e.getMessage());
			}
		}
		return false;
	}

	/**
	 * Inserts an entry into a specified table of the schema
	 * <code>HYLLEBLOMST</code>. This method is very simple and is only suited
	 * for tables which do not have more attributes than an id and one varchar.
	 * 
	 * @param table
	 *            The table in which to insert
	 * @param entry
	 *            The varchar to introduce into table
	 * @return The unique ID of the entry, whether is was already in the
	 *         database or not
	 * @throws ImportException
	 *             If an error occurred during writing to database
	 */
	private int insertIntoTableOneAttributeNoForeignKeys(String table,
			String entry) throws ImportException {
		table = String.format("hylleblomst.%s", table);

		int id;

		if (!validation.entryAlreadyInDatabase(entry, table)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password); Statement stmt = con.createStatement();) {

				id = validation.getMaxID(table) + 1;

				String insertStmt = String.format(
						"%1$s %2$s values(%3$d, '%4$s')", insertSql, table, id,
						entry);

				stmt.execute(insertStmt);

			} catch (SQLException e) {
				throw new ImportException(String.format(
						"A %s could not be inserted", entry) + e.getMessage());
			}
		} else {
			id = validation.getIDOfEntry(entry, table);
		}
		return id;
	}

	private int insertIntoTableOneAttributeOneForeignKey(String table,
			String entry, int foreignKey) throws ImportException {
		if (foreignKey == 0) {
			throw new ImportException("ForeignKey is empty");
		}
		table = String.format("hylleblomst.%s", table);

		int id;

		if (!validation.entryAlreadyInDatabase(entry, foreignKey, table)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password); Statement stmt = con.createStatement();) {

				id = validation.getMaxID(table) + 1;

				String insertStmt = String.format(
						"%1$s %2$s values(%3$d, '%4$s', %5$d)", insertSql,
						table, id, entry, foreignKey);

				stmt.execute(insertStmt);

			} catch (SQLException e) {
				throw new ImportException(String.format(
						"A %s could not be inserted", entry) + e.getMessage());
			}
		} else {
			id = validation.getIDOfEntry(entry, foreignKey, table);
		}
		return id;
	}

	private void insertIntoTableNoAttributesThreeForeignKeys(String table,
			int tradID, int quellenID, int personID) throws ImportException {
		table = String.format("hylleblomst.%s", table);

		if (!validation.entryAlreadyInDatabase(tradID, quellenID, personID,
				table)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password); Statement stmt = con.createStatement();) {

				String insertStmt = String.format(
						"%1$s %2$s values(%3$d, %4$d, %5$d)", insertSql, table,
						tradID, quellenID, personID);

				stmt.execute(insertStmt);

			} catch (SQLException e) {
				throw new ImportException("An info table could not be created"
						+ e.getMessage());
			}
		}
	}
}
