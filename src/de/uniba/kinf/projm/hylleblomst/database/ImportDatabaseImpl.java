package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;

/**
 * @author Simon
 *
 */
public class ImportDatabaseImpl implements ImportDatabase {

	private Validation validation;

	private String dbURL;
	private String user;
	private String password;

	private final int[] quellenID = { SourceKeys.STANDARD,
			SourceKeys.HSB_AUB_I11, SourceKeys.HSC_AUB_I131,
			SourceKeys.HSD_AUB_I132, SourceKeys.HSE_AUB_I9,
			SourceKeys.HSF_AUB_I8, SourceKeys.HSG_AUB_I6,
			SourceKeys.HSH_AEB_I321, SourceKeys.HSI_SB_3a, SourceKeys.HSJ_3,
			SourceKeys.AUB_V_E38 };
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
			int currentSourceID;

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

			// Necessary tables for being able to insert person
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
			Calendar tmpCalendar = new GregorianCalendar();
			tmpCalendar.clear();

			String calendarFieldsSet = "";

			if (!strings[45].isEmpty()) {
				tmpCalendar.set(Calendar.YEAR, Integer.parseInt(strings[45]));
				calendarFieldsSet = calendarFieldsSet + "0";
			} else {
				calendarFieldsSet = calendarFieldsSet + "1";
			}
			if (!strings[44].isEmpty()) {
				// Minus one as month is 0-based (0 is January) but entries in
				// source file are 1-based
				tmpCalendar.set(Calendar.MONTH,
						Integer.parseInt(strings[44]) - 1);
				calendarFieldsSet = calendarFieldsSet + "0";
			} else {
				calendarFieldsSet = calendarFieldsSet + "1";
			}
			if (!strings[43].isEmpty()) {
				tmpCalendar.set(Calendar.DAY_OF_MONTH,
						Integer.parseInt(strings[43]));
				calendarFieldsSet = calendarFieldsSet + "0";
			} else {
				calendarFieldsSet = calendarFieldsSet + "1";
			}

			Date datum;
			if (tmpCalendar.isSet(Calendar.YEAR)
					|| tmpCalendar.isSet(Calendar.MONTH)
					|| tmpCalendar.isSet(Calendar.DAY_OF_MONTH)) {
				datum = new Date(tmpCalendar.getTimeInMillis());
			} else {
				datum = null;
			}

			int nummerHess;
			try {
				nummerHess = Integer.parseInt(strings[2]);
			} catch (NumberFormatException e) {
				nummerHess = 0;
			}
			// Insert persons with ID from source file
			insertPerson(personID, Integer.parseInt(strings[1]), nummerHess,
					strings[29], strings[17], datum, calendarFieldsSet,
					strings[46], strings[63], strings[78], anredeTradID,
					fakultaetenID, fundortID, titelTradID);

			if (!strings[6].equals("")) {
				vornameNormID = insertIntoTableOneAttributeNoForeignKeys(
						"vorname_norm", strings[6]);
			}
			// Insert all different variations of given name
			currentSourceID = 0;
			for (int i = 5; i <= 16; i++) {
				if (i != 6) {
					if (!strings[i].equals("")) {
						vornameTradID = insertIntoTableOneAttributeOneForeignKey(
								"vorname_trad", strings[i], vornameNormID);
						insertIntoTableNoAttributesThreeForeignKeys(
								"vorname_info", vornameTradID,
								quellenID[currentSourceID], personID);
					}
					currentSourceID++;
				}
			}

			if (!strings[18].equals("")) {
				nameNormID = insertIntoTableOneAttributeNoForeignKeys(
						"name_norm", strings[18]);
			}
			// Insert all different variations of name
			currentSourceID = 0;
			for (int i = 18; i <= 28; i++) {
				if (!strings[i].equals("")) {
					nameTradID = insertIntoTableOneAttributeOneForeignKey(
							"name_trad", strings[i], nameNormID);
					insertIntoTableNoAttributesThreeForeignKeys("name_info",
							nameTradID, quellenID[currentSourceID], personID);
				}
				currentSourceID++;
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
			currentSourceID = 0;
			for (int i = 30; i <= 40; i++) {
				if (!strings[i].equals("")) {
					int ortTradID = insertIntoTableOneAttributeOneForeignKey(
							"ort_trad", strings[i], ortNormID);

					insertIntoTableNoAttributesThreeForeignKeys("ort_info",
							ortTradID, quellenID[currentSourceID], personID);
				}
				currentSourceID++;
			}

			if (!strings[49].equals("")) {
				fachNormID = insertIntoTableOneAttributeNoForeignKeys(
						"fach_norm", strings[49]);
			}

			int[] tmpStudienfach = { 48, 50 };
			currentSourceID = 0;
			for (int i : tmpStudienfach) {
				if (!strings[i].isEmpty()) {
					fachTradID = insertIntoTableOneAttributeOneForeignKey(
							"fach_trad", strings[i], fachNormID);
					insertIntoTableNoAttributesThreeForeignKeys("fach_info",
							fachTradID, quellenID[currentSourceID], personID);
				}
				if (i == 48) {
					currentSourceID++;
				}
			}

			if (!strings[52].equals("")) {
				wirtschaftslageNormID = insertIntoTableOneAttributeNoForeignKeys(
						"wirtschaftslage_norm", strings[52]);
			}

			int[] tmpWirtschaftslage = { 51, 53, 54, 55 };
			currentSourceID = 0;
			for (int i : tmpWirtschaftslage) {
				if (!strings[i].equals("")) {
					wirtschaftslageTradID = insertIntoTableOneAttributeOneForeignKey(
							"wirtschaftslage_trad", strings[i],
							wirtschaftslageNormID);
					insertIntoTableNoAttributesThreeForeignKeys(
							"wirtschaftslage_info", wirtschaftslageTradID,
							quellenID[currentSourceID], personID);

				}
				if (i == 51) {
					currentSourceID = 1;
				} else if (i == 53) {
					currentSourceID = 3;
				} else if (i == 54) {
					currentSourceID = 9;
				}
			}

			if (!strings[57].equals("")) {
				seminarNormID = insertIntoTableOneAttributeNoForeignKeys(
						"seminar_norm", strings[57]);
			}

			currentSourceID = 0;
			int[] tmpSeminar = { 56, 58, 59, 60, 61, 62 };
			for (int i : tmpSeminar) {
				if (!strings[i].equals("")) {
					seminarTradID = insertIntoTableOneAttributeOneForeignKey(
							"seminar_trad", strings[i], seminarNormID);
					insertIntoTableNoAttributesThreeForeignKeys("seminar_info",
							seminarTradID, quellenID[currentSourceID], personID);
				}
				if (i == 56) {
					currentSourceID = 1;
				} else if (i == 58) {
					currentSourceID = 3;
				} else if (i == 59) {
					currentSourceID = 4;
				} else if (i >= 60) {
					currentSourceID++;
				}
			}

			currentSourceID = 0;
			for (int i = 66; i <= 76; i++) {
				if (!strings[i].equals("")) {
					int zusaetzeID = insertIntoTableOneAttributeNoForeignKeys(
							"zusaetze", strings[i]);
					insertIntoTableNoAttributesThreeForeignKeys(
							"zusaetze_info", zusaetzeID,
							quellenID[currentSourceID], personID);
				}
				currentSourceID++;
			}

		}
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
			String jesuit, String adlig, Date datum, String calendarFieldsSet,
			String studienjahr, String graduiert, String anmerkung,
			int anredeTradID, int fakultaetenID, int fundortID, int titelTradID)
			throws ImportException {

		String sqlQuery = "INSERT INTO hylleblomst.person values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		int indexOf1Or2InStudienjahr;
		if (studienjahr.indexOf("2") != -1) {
			indexOf1Or2InStudienjahr = Math.min(studienjahr.indexOf("1"),
					studienjahr.indexOf("2"));
		} else {
			indexOf1Or2InStudienjahr = studienjahr.indexOf("1");
		}

		int studienjahrInt = Integer.parseInt(studienjahr.substring(
				indexOf1Or2InStudienjahr, indexOf1Or2InStudienjahr + 4));

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
				stmt.setString(7, calendarFieldsSet);
				stmt.setString(8, studienjahr);
				stmt.setInt(9, studienjahrInt);
				stmt.setString(10, graduiert);
				stmt.setString(11, anmerkung);
				if (anredeTradID == 0) {
					stmt.setNull(12, Types.INTEGER);
				} else {
					stmt.setInt(12, anredeTradID);
				}
				if (fakultaetenID == 0) {
					stmt.setNull(13, Types.INTEGER);
				} else {
					stmt.setInt(13, fakultaetenID);
				}
				if (fundortID == 0) {
					stmt.setNull(14, Types.INTEGER);
				} else {
					stmt.setInt(14, fundortID);
				}
				if (titelTradID == 0) {
					stmt.setNull(15, Types.INTEGER);
				} else {
					stmt.setInt(15, titelTradID);
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
}
