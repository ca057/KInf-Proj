package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.GregorianCalendar;
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
			int current;

			int anredeNormID = 0;
			int anredeTradID = 0;
			int fakultaetenID = 0;
			int fundortID = 0;
			int titelNormID = 0;
			int titelTradID = 0;

			int vornameNormID = 0;
			int nameNormID = 0;
			int fachNormID = 0;
			int wirtschaftslageNormID = 0;
			int seminarNormID = 0;

			int vornameTradID;
			int nameTradID;
			int fachTradID;
			int wirtschaftslageTradID;
			int seminarTradID;

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

			// Preparatory statements for inserting persons
			int tag = -1;
			int monat = -1;
			int jahr = -1;
			if (!strings[43].equals("")) {
				tag = Integer.parseInt(strings[43]);
			}
			if (!strings[44].equals("")) {
				// Minus one as month is 0-based (0 is January)
				monat = Integer.parseInt(strings[44]) - 1;
			}
			if (!strings[45].equals("")) {
				jahr = Integer.parseInt(strings[45]);
			}

			java.sql.Date datum = new Date(new GregorianCalendar(jahr, monat,
					tag).getTimeInMillis());
			int nummerHess;
			try {
				nummerHess = Integer.parseInt(strings[2]);
			} catch (NumberFormatException e) {
				nummerHess = 0;
			}

			insertPerson(personID, Integer.parseInt(strings[1]), nummerHess,
					strings[29], strings[17], datum, strings[46], strings[63],
					strings[78], anredeTradID, fakultaetenID, fundortID,
					titelTradID);

			if (!strings[6].equals("")) {
				vornameNormID = insertIntoTableOneAttributeNoForeignKeys(
						"vorname_norm", strings[6]);
			}
			// Insert all different variations of name
			current = 0;
			for (int i = 5; i <= 16; i++) {
				if (i != 6) {
					if (!strings[i].equals("")) {
						vornameTradID = insertIntoTableOneAttributeOneForeignKey(
								"vorname_trad", strings[i], vornameNormID);
						insertIntoTableNoAttributesThreeForeignKeys(
								"vorname_info", vornameTradID,
								quellenID[current], personID);
					}
					current++;
				}
			}

			if (!strings[18].equals("")) {
				nameNormID = insertIntoTableOneAttributeNoForeignKeys(
						"name_norm", strings[18]);
			}
			// Insert all different variations of name
			current = 1;
			for (int i = 19; i <= 28; i++) {
				if (!strings[i].equals("")) {
					nameTradID = insertIntoTableOneAttributeOneForeignKey(
							"name_trad", strings[i], nameNormID);
					insertIntoTableNoAttributesThreeForeignKeys("name_info",
							nameTradID, quellenID[current], personID);
				}
				current++;
			}

			int ortAbweichNormID = 0;
			if (!strings[42].equals("")) {
				ortAbweichNormID = insertIntoOrtAbweichungNorm(strings[42]);
			}
			int ortNormID = 0;
			if (!strings[41].equals("")) {
				ortNormID = insertIntoTableOneAttributeOneForeignKey(
						"ort_norm", strings[41], ortAbweichNormID);
			}
			current = 0;
			for (int i = 30; i <= 40; i++) {
				if (!strings[i].equals("")) {
					int ortTradID = insertIntoTableOneAttributeOneForeignKey(
							"ort_trad", strings[i], ortNormID);

					insertIntoTableNoAttributesThreeForeignKeys("ort_info",
							ortTradID, quellenID[current], personID);
				}
				current++;
			}

			if (!strings[49].equals("")) {
				fachNormID = insertIntoTableOneAttributeNoForeignKeys(
						"fach_norm", strings[49]);
			}

			if (!strings[48].equals("")) {
				fachTradID = insertIntoTableOneAttributeOneForeignKey(
						"fach_trad", strings[48], fachNormID);
				insertIntoTableNoAttributesThreeForeignKeys("fach_info",
						fachTradID, quellenID[0], personID);
			}
			if (!strings[50].equals("")) {
				fachTradID = insertIntoTableOneAttributeOneForeignKey(
						"fach_trad", strings[50], fachNormID);
				insertIntoTableNoAttributesThreeForeignKeys("fach_info",
						fachTradID, quellenID[1], personID);
			}
			if (!strings[52].equals("")) {
				wirtschaftslageNormID = insertIntoTableOneAttributeNoForeignKeys(
						"wirtschaftslage_norm", strings[52]);
			}

			int[] tmpWirtschaftslage = { 51, 53, 54, 55 };
			current = 0;
			for (int i : tmpWirtschaftslage) {
				if (!strings[i].equals("")) {
					wirtschaftslageTradID = insertIntoTableOneAttributeOneForeignKey(
							"wirtschaftslage_trad", strings[i],
							wirtschaftslageNormID);

					insertIntoTableNoAttributesThreeForeignKeys(
							"wirtschaftslage_info", wirtschaftslageTradID,
							quellenID[current], personID);

				}
				if (i == 51) {
					current = 1;
				} else if (i == 53) {
					current = 3;
				} else if (i == 54) {
					current = 9;
				}
			}

			if (!strings[57].equals("")) {
				seminarNormID = insertIntoTableOneAttributeNoForeignKeys(
						"seminar_norm", strings[57]);
			}

			current = 0;
			int[] tmpSeminar = { 56, 58, 59, 60, 61, 62 };
			for (int i : tmpSeminar) {
				if (!strings[i].equals("")) {
					seminarTradID = insertIntoTableOneAttributeOneForeignKey(
							"seminar_trad", strings[i], seminarNormID);
					insertIntoTableNoAttributesThreeForeignKeys("seminar_info",
							seminarTradID, quellenID[current], personID);
				}
				if (i == 56) {
					current = 1;
				} else if (i == 58) {
					current = 3;
				} else if (i == 59) {
					current = 4;
				} else if (i >= 60) {
					current++;
				}
			}

			current = 0;
			for (int i = 66; i <= 76; i++) {
				if (!strings[i].equals("")) {
					int zusaetzeID = insertIntoTableOneAttributeNoForeignKeys(
							"zusaetze", strings[i]);
					insertIntoTableNoAttributesThreeForeignKeys(
							"zusaetze_info", zusaetzeID, quellenID[current],
							personID);
				}
				current++;
			}

		}
	}

	/**
	 * Inserts persons into the database
	 * 
	 * @param personID
	 * @param seite
	 * @param nummerHess
	 * @param jesuit
	 * @param adlig
	 * @param datum
	 * @param studienjahr
	 * @param graduiert
	 * @param anmerkung
	 * @param anredeTradID
	 * @param fakultaetenID
	 * @param fundortID
	 * @param titelTradID
	 * @return
	 * @throws ImportException
	 */
	private boolean insertPerson(int personID, int seite, int nummerHess,
			String jesuit, String adlig, Date datum, String studienjahr,
			String graduiert, String anmerkung, int anredeTradID,
			int fakultaetenID, int fundortID, int titelTradID)
			throws ImportException {

		String sqlQuery = "INSERT INTO hylleblomst.person values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		if (!validation.personIDIsTaken(personID)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password);
					PreparedStatement stmt = con.prepareStatement(sqlQuery);) {

				stmt.setInt(1, personID);
				stmt.setInt(2, seite);

				if (nummerHess == 0) {
					stmt.setNull(3, Types.INTEGER);
				} else {
					stmt.setInt(3, nummerHess);
				}
				stmt.setString(4, jesuit);
				stmt.setString(5, adlig);
				stmt.setDate(6, datum);
				stmt.setString(7, studienjahr);
				stmt.setString(8, graduiert);
				stmt.setString(9, anmerkung);
				if (anredeTradID == 0) {
					stmt.setNull(10, Types.INTEGER);
				} else {
					stmt.setInt(10, anredeTradID);
				}
				if (fakultaetenID == 0) {
					stmt.setNull(11, Types.INTEGER);
				} else {
					stmt.setInt(11, fakultaetenID);
				}
				if (fundortID == 0) {
					stmt.setNull(12, Types.INTEGER);
				} else {
					stmt.setInt(12, fundortID);
				}
				if (titelTradID == 0) {
					stmt.setNull(13, Types.INTEGER);
				} else {
					stmt.setInt(13, titelTradID);
				}

				stmt.executeUpdate();

				return true;
			} catch (SQLException e) {
				throw new ImportException("A Person could not be inserted "
						+ e.getMessage());
			}
		}
		return false;
	}

	private int insertIntoOrtAbweichungNorm(String entry)
			throws ImportException {
		String table = "hylleblomst.ort_abweichung_norm";
		String sqlQuery = String
				.format("INSERT INTO %s values(?, ?, ?)", table);
		int id;
		String anmerkung = "";
		if (entry.contains("*")) {
			int indexOfStar = entry.indexOf("*");
			anmerkung = entry.substring(indexOfStar + 1);
		}

		if (!validation.entryAlreadyInDatabase(entry, anmerkung, table)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password);) {
				PreparedStatement stmt = con.prepareStatement(sqlQuery);
				id = validation.getMaxID(table) + 1;

				stmt.setInt(1, id);
				stmt.setString(2, entry);
				stmt.setString(3, anmerkung);

				stmt.executeUpdate();
			} catch (SQLException e) {
				throw new ImportException(String.format(
						"A %s could not be inserted", entry) + e.getMessage());
			}
		} else {
			id = validation.getIDOfOrtAbweichungEntry(entry, anmerkung, table);
		}

		return id;
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
		String sqlQuery = String.format("INSERT INTO %s values(?, ?)", table);
		int id;

		if (!validation.entryAlreadyInDatabase(entry, table)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password);) {
				PreparedStatement stmt = con.prepareStatement(sqlQuery);
				id = validation.getMaxID(table) + 1;

				stmt.setInt(1, id);
				stmt.setString(2, entry);

				stmt.executeUpdate();

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
		table = String.format("hylleblomst.%s", table);
		String sqlQuery = String.format("INSERT INTO %s values(?,?,?)", table);
		int id;

		if (!validation.entryAlreadyInDatabase(entry, foreignKey, table)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password);) {
				PreparedStatement stmt = con.prepareStatement(sqlQuery);
				id = validation.getMaxID(table) + 1;

				stmt.setInt(1, id);
				stmt.setString(2, entry);
				if (foreignKey == 0) {
					stmt.setNull(3, Types.INTEGER);
				} else {
					stmt.setInt(3, foreignKey);
				}
				stmt.executeUpdate();

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
		String sqlQuery = String.format("INSERT INTO %s values(?,?,?)", table);

		if (!validation.entryAlreadyInDatabase(tradID, quellenID, personID,
				table)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password);) {
				PreparedStatement stmt = con.prepareStatement(sqlQuery);

				stmt.setInt(1, tradID);
				stmt.setInt(2, quellenID);
				stmt.setInt(3, personID);

				stmt.executeUpdate();

			} catch (SQLException e) {
				throw new ImportException("An info table could not be created"
						+ e.getMessage());
			}
		}
	}
}
