package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryRequest {
	private SearchFieldKeys searchField;
	private String column;
	private Object input;
	private int source;
	private String table;
	private String personJoin;
	private SQLShred sqlShred;

	public QueryRequest(SearchFieldKeys columns, Object input, int source) {
		setSearchField(columns);
		setInput(input);
		setSource(source);
		searchFieldKeyToDatabaseData(columns);
	}

	QueryRequest() {
	}

	public SearchFieldKeys getSearchField() {
		return searchField;
	}

	public void setSearchField(SearchFieldKeys columns) {
		this.searchField = columns;
		searchFieldKeyToDatabaseData(columns);
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

	public String getPersonJoin() {
		return personJoin;
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
	private void searchFieldKeyToDatabaseData(SearchFieldKeys key) {
		switch (key) {
		case ADLIG:
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
			if (input instanceof Integer) {
				sqlShred.getDate((int[]) input);
			}
			break;
		case EINSCHREIBEDATUM:
			// TODO Das muss am besten vorher in ein Datum umgewandelt werden.
			// Oder übergibt Christian hier eh keine Einzeldaten?
			table = "PERSON";
			column = "";
			if (input instanceof Integer) {
				sqlShred.getDate((int[]) input);
			}
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
		case ANREDE:
			table = "ANREDE_NORM";
			column = getColumnName(table, 2);
			break;
		case TITEL:
			table = "TITEL_NORM";
			column = getColumnName(table, 2);
			break;
		case VORNAME:
			table = "VORNAME_NORM";
			column = getColumnName(table, 2);
			break;
		case NACHNAME:
			table = "NAME_TRAD";
			column = getColumnName(table, 2);
			break;
		case WIRTSCHAFTSLAGE:
			table = "WIRTSCHAFTSLAGE_TRAD";
			column = getColumnName(table, 2);
			break;
		case ORT:
			table = "ORT_TRAD";
			column = getColumnName(table, 2);
			break;
		case FACH:
			table = "FACH_TRAD";
			column = getColumnName(table, 2);
			break;
		case FAKULTAETEN:
			table = "FAKULTAETEN";
			column = getColumnName(table, 2);
			break;
		case SEMINAR:
			table = "SEMINAR_TRAD";
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
		personJoin = sqlShred.getPersonJoin(table);
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
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(String.format(
						"SELECT * FROM hylleblomst.%s", table));) {
			ResultSetMetaData rsmd = rs.getMetaData();
			result = rsmd.getColumnName(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
}
