package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class QueriesImpl implements Queries {
	DBAccess db;

	@Override
	public void search(Collection<QueryRequest> queryRequests) {

		buildQuery(queryRequests);
	}

	private void buildQuery(Collection<QueryRequest> queryRequests) {
		// In WHERE auch SELECT möglich!!
		StringBuffer query = new StringBuffer();

		// TODO Spaltennamen herausfinden
		String dbURL = "jdbc:derby:Users/Hannes/git/KInf-Proj/db/MyDB";
		String user = "admin";
		String password = "password";
		db = new DBAccess(dbURL, user, password);
		// db = new DBAccess();
		// try {
		// db.startQuery("SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Tabellenname'");
		// } catch (SQLException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public String getColumnName(String table) {
		String dbURL = "jdbc:derby:db/MyDB";
		String user = "admin";
		String password = "password";
		String result = "";

		try (Connection con = DriverManager
				.getConnection("jdbc:derby:/Users/Hannes/git/KInf-Proj/db/MyDB; user=admin; password=password");
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			String querySql = String.format("SELECT * FROM %s", table);
			ResultSet rs = stmt.executeQuery(querySql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int size = rsmd.getColumnCount();
			for (int i = 0; i < size; i++) {
				result += rsmd.getColumnName(i) + "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {
		db = new DBAccess(dbURL, user, password);
	}

	/**
	 * Returns the name of the table the {@code SearchFieldKey} key belongs to.
	 * 
	 * @param key
	 * @return
	 */
	// TODO Collection/ List durchgehen, PreparedStatement mit so vielen ?
	// (person.vorname=?) wie Queries, dann nochmal durchgehen und fragezeichen
	// füllen. WICHTIG: Collection muss sortiert sein!
	private String searchFieldKeyToTable(SearchFieldKeys key) {
		// TODO In QueryRequest moven
		if (key.toString().equals("ADELIG")) {
			return "PERSON";
		} else if (key.toString().equals("JESUIT")) {
			return "PERSON";
		} else if (key.toString().equals("STUDIENJAHR")) {
			return "PERSON";
		} else if (key.toString().equals("STUDJAHR_VON")) {
			return "PERSON";
		} else if (key.toString().equals("STUDJAHR_BIS")) {
			return "PERSON";
		} else if (key.toString().equals("EINSCHREIBEDATUM_TAGE")) {
			return "PERSON";
		} else if (key.toString().equals("EINSCHREIBEDATUM_MONATE")) {
			return "PERSON";
		} else if (key.toString().equals("EINSCHREIBEDATUM_JAHRE")) {
			return "PERSON";
		} else if (key.toString().equals("ANMERKUNGEN")) {
			return "PERSON";
		} else if (key.toString().equals("NUMMER")) {
			return "PERSON";
		} else if (key.toString().equals("SEITE_ORIGINALE")) {
			return "PERSON";
		} else if (key.toString().equals("NUMMER_HESS")) {
			return "PERSON";
		} else {
			return key.name();
		}
	}

	private String searchFieldKeyToColumn(SearchFieldKeys key) {
		// TODO In QueryRequest moven
		if (key.toString().equals("ADELIG")) {
			return "PERSON";
		} else if (key.toString().equals("JESUIT")) {
			return "PERSON";
		} else if (key.toString().equals("STUDIENJAHR")) {
			return "PERSON";
		} else if (key.toString().equals("STUDJAHR_VON")) {
			return "PERSON";
		} else if (key.toString().equals("STUDJAHR_BIS")) {
			return "PERSON";
		} else if (key.toString().equals("EINSCHREIBEDATUM_TAGE")) {
			return "PERSON";
		} else if (key.toString().equals("EINSCHREIBEDATUM_MONATE")) {
			return "PERSON";
		} else if (key.toString().equals("EINSCHREIBEDATUM_JAHRE")) {
			return "PERSON";
		} else if (key.toString().equals("ANMERKUNGEN")) {
			return "PERSON";
		} else if (key.toString().equals("NUMMER")) {
			return "PERSON";
		} else if (key.toString().equals("SEITE_ORIGINALE")) {
			return "PERSON";
		} else if (key.toString().equals("NUMMER_HESS")) {
			return "PERSON";
		} else if (key.toString().equals("ANREDE_TRAD")) {
			return "";
		} else if (key.toString().equals("ANREDE_NORM")) {
			return "";
		} else if (key.toString().equals("TITEL_TRAD")) {
			return "";
		} else if (key.toString().equals("TITEL_NORM")) {
			return "";
		} else if (key.toString().equals("VORNAME_NORM")) {
			return "";
		} else if (key.toString().equals("NACHNAME_TRAD")) {
			return "";
		} else if (key.toString().equals("NACHNAME_NORM")) {
			return "";
		} else if (key.toString().equals("WIRTSCHAFTSLAGE_TRAD")) {
			return "";
		} else if (key.toString().equals("WIRTSCHAFTSLAGE_NORM")) {
			return "";
		} else if (key.toString().equals("ORT_TRAD")) {
			return "";
		} else if (key.toString().equals("ORT_NORM")) {
			return "";
		} else if (key.toString().equals("ORT_ABWEICHUNG_NORM")) {
			return "";
		} else if (key.toString().equals("FACH_TRAD")) {
			return "";
		} else if (key.toString().equals("FACH_NORM")) {
			return "";
		} else if (key.toString().equals("FAKULTAETEN")) {
			return "";
		} else if (key.toString().equals("SEMINAR_TRAD")) {
			return "";
		} else if (key.toString().equals("SEMINAR_NORM")) {
			return "";
		} else if (key.toString().equals("GRADUIERT")) {
			return "";
		} else if (key.toString().equals("ZUSAETZE")) {
			return "";
		} else if (key.toString().equals("FUNDORTE")) {
			return "PERSON";
		} else {
			throw new IllegalArgumentException(
					"Das zugehörige Tabellenelement für Suchfeld " + key.name()
							+ " ist nicht definiert.");
		}

	}

}
