package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryRequest {
	private SearchFieldKeys searchField;
	private Object input;
	private int source;
	private String table;
	private String column;

	public QueryRequest(SearchFieldKeys columns, Object input, int source) {
		setSearchField(columns);
		setInput(input);
		setSource(source);
	}

	QueryRequest() {
	}

	public SearchFieldKeys getSearchField() {
		return searchField;
	}

	public void setSearchField(SearchFieldKeys columns) {
		this.searchField = columns;
	}

	public Object getInput() {
		return input;
	}

	public void setInput(Object input) {
		this.input = input;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getTable() {
		return table;
	}

	public String getColumn() {
		return column;
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

	public void searchFieldKeyToDatabaseData(SearchFieldKeys key) {
		switch (key) {
		case ADELIG:
			table = "PERSON";
			column = getColumnName(table, 2);
			break;
		case JESUIT:
			table = "PERSON";
			column = getColumnName(table, 2);
			break;
		case STUDIENJAHR:
			table = "PERSON";
			column = getColumnName(table, 2);
			break;
		case STUDJAHR_VON:
			// TODO bei von bis wird es ein Problem geben, oder?
			table = "PERSON";
			column = getColumnName(table, 2);
			break;
		case STUDJAHR_BIS:
			// TODO bei von bis wird es ein Problem geben, oder?
			table = "PERSON";
			column = getColumnName(table, 2);
			break;
		case EINSCHREIBEDATUM_TAGE:
			// TODO Das muss am besten vorher in ein Datum umgewandelt werden.
			// Oder übergibt Christian hier eh keine Einzeldaten?
			table = "PERSON";
			column = "";
			break;
		case EINSCHREIBEDATUM_MONATE:
			table = "PERSON";
			column = "";
			break;
		case EINSCHREIBEDATUM_JAHRE:
			table = "PERSON";
			column = "";
			break;
		case ANMERKUNGEN:
			table = "PERSON";
			column = "ANMKERUNG";
			break;
		case NUMMER:
			table = "PERSON";
			column = "PERSONID";
			break;
		case SEITE_ORIGINALE:
			table = "PERSON";
			column = "SEITEORIGINAL";
			break;
		case NUMMER_HESS:
			table = "PERSON";
			column = "NUMMERHESS";
			break;
		case ANREDE_TRAD:
			table = "ANREDE_TRAD";
			column = getColumnName(table, 2);
			break;
		case ANREDE_NORM:
			table = "ANREDE_NORM";
			column = getColumnName(table, 2);
			break;
		case TITEL_TRAD:
			table = "TITEL_TRAD";
			column = getColumnName(table, 2);
			break;
		case TITEL_NORM:
			table = "TITEL_NORM";
			column = getColumnName(table, 2);
			break;
		case VORNAME_TRAD:
			table = "VORNAME_TRAD";
			column = getColumnName(table, 2);
			break;
		case VORNAME_NORM:
			table = "VORNAME_NORM";
			column = getColumnName(table, 2);
			break;
		case NACHNAME_TRAD:
			table = "NAME_TRAD";
			column = getColumnName(table, 2);
			break;
		case NACHNAME_NORM:
			table = "NAME_TRAD";
			column = getColumnName(table, 2);
			break;
		case WIRTSCHAFTSLAGE_TRAD:
			table = "WIRTSCHAFTSLAGE_TRAD";
			column = getColumnName(table, 2);
			break;
		case WIRTSCHAFTSLAGE_NORM:
			table = "WIRTSCHAFTSLAGE_NORM";
			column = getColumnName(table, 2);
			break;
		case ORT_TRAD:
			table = "ORT_TRAD";
			column = getColumnName(table, 2);
			break;
		case ORT_NORM:
			table = "ORT_NORM";
			column = getColumnName(table, 2);
			break;
		case ORT_ABWEICHUNG_NORM:
			// TODO Hier muss dann auch die Anmerkung mit zurückgegeben werden.
			table = "ORT_ABWEICHUNG_NORM";
			column = getColumnName(table, 2);
			break;
		case FACH_TRAD:
			table = "FACH_TRAD";
			column = getColumnName(table, 2);
			break;
		case FACH_NORM:
			table = "FACH_NORM";
			column = getColumnName(table, 2);
			break;
		case FAKULTAETEN:
			table = "FAKULTAETEN";
			column = getColumnName(table, 2);
			break;
		case SEMINAR_TRAD:
			table = "SEMINAR_TRAD";
			column = getColumnName(table, 2);
			break;
		case SEMINAR_NORM:
			table = "SEMINAR_NORM";
			column = getColumnName(table, 2);
			break;
		case GRADUIERT:
			table = "PERSON";
			column = "GRADUIERT";
			break;
		case ZUSAETZE:
			table = "ZUSAETZE";
			column = getColumnName(table, 2);
			break;
		case FUNDORTE:
			table = "FUNDORTE";
			column = getColumnName(table, 2);
			break;
		default:
			throw new IllegalArgumentException(
					"Das zugehörige Tabellenelement für Suchfeld " + key.name()
							+ " ist nicht definiert.");
		}
	}

	public String getColumnName(String table, int i) {
		String dbURL = "jdbc:derby:db/MyDB";
		String user = "admin";
		String password = "password";
		String result = "";
		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			String querySql = String.format("SELECT * FROM hylleblomst.%s",
					table);
			ResultSet rs = stmt.executeQuery(querySql);
			ResultSetMetaData rsmd = rs.getMetaData();
			result = rsmd.getColumnName(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
}
