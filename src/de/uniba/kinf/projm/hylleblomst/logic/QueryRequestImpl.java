package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryRequestImpl implements QueryRequest {
	private SearchFieldKeys searchField;
	private String column;
	private Object input;
	private int source;
	private String table;
	private String sqlStatement;

	public QueryRequestImpl(SearchFieldKeys searchField, Object input,
			int source) {
		setSearchField(searchField);
		setInput(input);
		setSource(source);
		searchFieldKeyToDatabaseData();
	}

	QueryRequestImpl() {
	}

	@Override
	public SearchFieldKeys getSearchField() {
		return searchField;
	}

	@Override
	public void setSearchField(SearchFieldKeys searchField) {
		this.searchField = searchField;
		searchFieldKeyToDatabaseData();
	}

	@Override
	public Object getInput() {
		if ("Jesuit".equals(column) || "Adelig".equals(column)) {
			return "<>\"\"";
		}
		return input;
	}

	@Override
	public void setInput(Object input) {
		if ("Jesuit".equals(column) || "Adelig".equals(column)) {
			this.input = "<>\"\"";
		} // STUDIENJAHR!
		else {
			this.input = input.toString();
		}
	}

	@Override
	public int getSource() {
		return source;
	}

	@Override
	public void setSource(int source) {
		this.source = source;
	}

	@Override
	public String getTable() {
		return table;
	}

	@Override
	public String getColumn() {
		return column;
	}

	@Override
	public String getSQLStatement() {
		return sqlStatement;
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
	private void searchFieldKeyToDatabaseData() {
		if (source == SourceKeys.ORT_NORM_AB) {
			if (searchField.equals(SearchFieldKeys.ORT)) {
				// TODO Hier muss dann auch die Anmerkung mit zurückgegeben
				// werden.
				table = "ORT_ABWEICHUNG_NORM";
			}
			column = getColumnName(table, 2);
			sqlStatement = new SQLShred(table, column, input).getJoin();
		} else if (source > SourceKeys.bottom && source < SourceKeys.top) {
			switch (searchField) {
			case ADLIG:
				table = "PERSON";
				column = getColumnName(table, 5);
				break;
			case JESUIT:
				table = "PERSON";
				column = getColumnName(table, 4);
				break;
			case STUDIENJAHR:
				table = "PERSON";
				column = getColumnName(table, 7);
				if (input instanceof Integer) {
					input = new SQLShred(table, column, input)
							.getDate((int[]) input);
				}
				break;
			case EINSCHREIBEDATUM:
				// TODO Das muss noch implementiert werden.
				table = "PERSON";
				column = getColumnName(table, 6);
				if (input instanceof Integer) {
					input = new SQLShred(table, column, input)
							.getDate((int[]) input);
				}
				break;
			case ANMERKUNGEN:
				table = "PERSON";
				column = getColumnName(table, 9);
				break;
			case NUMMER:
				table = "PERSON";
				column = getColumnName(table, 1);
				break;
			case SEITE_ORIGINALE:
				table = "PERSON";
				column = getColumnName(table, 2);
				break;
			case NUMMER_HESS:
				table = "PERSON";
				column = getColumnName(table, 3);
				break;
			case ANREDE:
				if (source == SourceKeys.NORM) {
					table = "ANREDE_NORM";
				} else {
					table = "ANREDE_TRAD";
				}
				column = getColumnName(table, 2);
				break;
			case TITEL:
				if (source == SourceKeys.NORM) {
					table = "TITEL_NORM";
				} else {
					table = "TITEL_TRAD";
				}
				column = getColumnName(table, 2);
				break;
			case VORNAME:
				if (source == SourceKeys.NORM) {
					table = "VORNAME_NORM";
				} else {
					table = "VORNAME_TRAD";
				}
				column = getColumnName(table, 2);
				break;
			case NACHNAME:
				if (source == SourceKeys.NORM) {
					table = "NAME_NORM";
				} else {
					table = "NAME_TRAD";
				}
				column = getColumnName(table, 2);
				break;
			case WIRTSCHAFTSLAGE:
				if (source == SourceKeys.NORM) {
					table = "WIRTSCHAFTSLAGE_NORM";
				} else {
					table = "WIRTSCHAFTSLAGE_TRAD";
				}
				column = getColumnName(table, 2);
				break;
			case ORT:
				if (source == SourceKeys.NORM) {
					table = "ORT_NORM";
				} else {
					table = "ORT_TRAD";
				}
				break;
			case FACH:
				if (source == SourceKeys.NORM) {
					table = "FACH_NORM";
				} else {
					table = "FACH_TRAD";
				}
				column = getColumnName(table, 2);
				break;
			case FAKULTAETEN:
				table = "FAKULTAETEN";
				column = getColumnName(table, 2);
				break;
			case SEMINAR:
				if (source == SourceKeys.NORM) {
					table = "SEMINAR_NORM";
				} else {
					table = "SEMINAR_TRAD";
				}
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
						"Das zugehörige Tabellenelement für Suchfeld "
								+ searchField.name() + " ist nicht definiert.");
			}
			sqlStatement = new SQLShred(table, column, input).getJoin();
		} else {
			throw new IllegalArgumentException("Die Werte für Suchfeld "
					+ searchField.toString() + " und Quelle " + source
					+ " konnten keiner Tabelle und Spalte zugeordnet werden.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uniba.kinf.projm.hylleblomst.logic.QueryRequest#getColumnName(java
	 * .lang.String, int)
	 */
	@Override
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
