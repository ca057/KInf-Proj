package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.logic.QueryRequest;

public class QueryRequestImpl implements QueryRequest {
	private SearchFieldKeys searchField;
	private String column;
	private String input;
	private int source;
	private String table;

	public QueryRequestImpl(SearchFieldKeys searchField, String input,
			int source) {
		setInput(input);
		setSource(source);
		setSearchField(searchField);
		searchFieldKeyToDatabaseData();
	}

	@Override
	public SearchFieldKeys getSearchField() {
		return searchField;
	}

	public void setSearchField(SearchFieldKeys searchField) {
		this.searchField = searchField;
		searchFieldKeyToDatabaseData();
	}

	@Override
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	@Override
	public int getSource() {
		return source;
	}

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

	/**
	 * Returns the name of the table the {@code SearchFieldKey} key belongs to.
	 * 
	 * @param key
	 * @return
	 */
	private void searchFieldKeyToDatabaseData() {
		if (source > SourceKeys.bottom && source < SourceKeys.top) {
			// TODO Spaltentitel statt getColumnName()
			switch (searchField) {
			case ADLIG:
				table = "PERSON";
				column = ColumnNameKeys.ADLIG;
				break;
			case JESUIT:
				table = "PERSON";
				column = ColumnNameKeys.JESUIT;
				break;
			case STUDIENJAHR_VON:
			case STUDIENJAHR_BIS:
				// TODO Int-Wert!?
				table = "PERSON";
				column = ColumnNameKeys.STUDIENJAHR;
				break;
			case EINSCHREIBEDATUM_VON:
			case EINSCHREIBEDATUM_BIS:
				// TODO Felder gesetzt?!
				table = "PERSON";
				column = ColumnNameKeys.DATUM;
				break;
			case ANMERKUNGEN:
				table = "PERSON";
				column = ColumnNameKeys.ANMERKUNG;
				break;
			case NUMMER:
				table = "PERSON";
				column = getColumnName(table, 1);
				break;
			case SEITE_ORIGINALE:
				table = "PERSON";
				column = ColumnNameKeys.SEITE_ORIGINAL;
				break;
			case NUMMER_HESS:
				table = "PERSON";
				column = ColumnNameKeys.NUMMER_HESS;
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
				} else if (source == SourceKeys.ORT_NORM_AB) {
					table = "ORT_ABWEICHUNG_NORM";
				} else {
					table = "ORT_TRAD";
				}
				column = getColumnName(table, 2);
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
		} else {
			throw new IllegalArgumentException("Die Werte für Suchfeld "
					+ searchField.toString() + " und Quelle " + source
					+ " konnten keiner Tabelle und Spalte zugeordnet werden.");
		}
	}

	@Override
	public String getWhere() {
		if (source == SourceKeys.NO_SOURCE) {
			if (searchField == SearchFieldKeys.ADLIG
					|| searchField == SearchFieldKeys.JESUIT
					|| searchField == SearchFieldKeys.GRADUIERT) {
				return String.format("Hylleblomst.%s.%s <> ''", table, column);
			}
			if (searchField == SearchFieldKeys.EINSCHREIBEDATUM_VON) {
				return String.format("Hylleblomst.%s.%s > ?", table, column);
			}
			if (searchField == SearchFieldKeys.EINSCHREIBEDATUM_BIS) {
				return String.format("Hylleblomst.%s.%s < ?", table, column);
			}
			// TODO Jahr
		} else if (source == SourceKeys.NORM) {
			return String.format("Hylleblomst.%s_norm.%s LIKE ?",
					table.substring(0, table.indexOf("_")), column);
		} else if (!(source == SourceKeys.NO_SELECTION || searchField == SearchFieldKeys.ANREDE)) {
			return String
					.format("Hylleblomst.%s.%s LIKE ? AND Hylleblomst.%s_info.QuellenID = %s",
							table, column,
							table.substring(0, table.indexOf("_")), source);
		}
		return String.format("Hylleblomst.%s.%s LIKE ?", table, column);
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
