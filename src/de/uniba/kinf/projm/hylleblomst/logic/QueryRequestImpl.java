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
	private String sqlFrom;
	private String sqlWhere;
	private String dbName = "Hylleblomst";
	private String tableName;

	public QueryRequestImpl(SearchFieldKeys searchField, Object input,
			int source) {
		setSearchField(searchField);
		setInput(input);
		setSource(source);
		searchFieldKeyToDatabaseData();
		this.dbName = dbName;
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
		return input;
	}

	public String getSqlFrom() {
		return sqlFrom;
	}

	public void setSqlFrom(String sqlFrom) {
		this.sqlFrom = sqlFrom;
	}

	public String getSqlWhere() {
		return sqlWhere;
	}

	public void setSqlWhere(String sqlWhere) {
		this.sqlWhere = sqlWhere;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public void setInput(Object input) {
		this.input = input;
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
		return sqlFrom;
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
					input = getDate((int[]) input);
				}
				break;
			case EINSCHREIBEDATUM:
				// TODO Das muss noch implementiert werden.
				table = "PERSON";
				column = getColumnName(table, 6);
				if (input instanceof Integer) {
					input = getDate((int[]) input);
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
			if (table.contains("_")) {
				this.tableName = table.substring(0, table.indexOf("_"));
			} else {
				this.tableName = table;
			}
			sqlFrom = getFrom();
			sqlWhere = getWhere();
		} else {
			throw new IllegalArgumentException("Die Werte für Suchfeld "
					+ searchField.toString() + " und Quelle " + source
					+ " konnten keiner Tabelle und Spalte zugeordnet werden.");
		}
	}

	String getFrom() {
		if (tableName.toUpperCase().startsWith("PERSON")) {
			// return String.format("%s.%s", dbName, tableName);
			return "";
		}
		if (tableName.toUpperCase().startsWith("FAKUL")
				|| tableName.startsWith("FUND")) {
			return String.format("%s.%s"
			// , %1$s.%s
					, dbName, tableName // , "PERSON"
					);
		}
		if (tableName.toUpperCase().startsWith("ZUSAE")) {
			return String.format("%s.%s, %1$s.%2$s%s"
			// + ", %1$s.%s"
					, dbName, tableName, "_info"// , "PERSON"
			);
		}
		String result = "";
		if (tableName.toUpperCase().startsWith("ORT")) {
			result += String.format("%s.%s%s, ", dbName, tableName,
					"_abweichung_norm");
		}
		result += String.format("%s.%s%s, %1$s.%2$s%s", dbName, tableName,
				"_norm", "_trad");
		if (!tableName.toUpperCase().startsWith("ANREDE")
				&& !(tableName.toUpperCase().startsWith("TITEL"))) {
			result += String.format(", %s.%s_info", dbName, tableName);
			if (!(source == SourceKeys.NO_SOURCE)) {
				result += String.format(", %s.%s", dbName, "Quellen");
			}
		}
		return result;
	}

	String getWhere() {
		if (input instanceof Boolean) {
			return String.format("%s.%s.%s %s", dbName, table, column, "<> ''");
		}
		if (tableName.toUpperCase().startsWith("PERSON")) {
			return String.format(" %s.%s.%s LIKE ?", dbName, table, column);
		}
		if (tableName.toUpperCase().startsWith("FAKUL")
				|| tableName.startsWith("FUND")) {
			return String.format(
					"%s.%s.%2$sID = %1$s.%s.%2$sID AND %1$s.%s.%s LIKE ?",
					dbName, tableName, "PERSON", table, column);
		}
		if (tableName.toUpperCase().startsWith("ZUSAE")) {
			return String
					.format("%s.%s_info.%2$sInfoID = %1$s.%2$s_trad.%2$sInfoID AND %1$s.%2$s_info.%sID = %1$s.%3$s.%3$sID",
							dbName, tableName, "Person");
		}

		String result = "";
		if (tableName.toUpperCase().startsWith("ORT")) {
			result += String
					.format("%s.%s_abweichung_norm.%2$sAbweichungNormID = %1$s.%2$s_norm.AbweichungNormID AND ",
							dbName, tableName);
		}
		result += String.format(
				"%s.%s_norm.%2$sNormID = %1$s.%2$s_trad.%2$sNormID ", dbName,
				tableName);
		if (tableName.toUpperCase().startsWith("ANREDE")
				|| tableName.toUpperCase().startsWith("TITEL")) {
			return result
					+ String.format(
							" AND %s.%s_trad.%2$sTradID = %1$s.Person.%2$sID AND %1$s.%s.%s LIKE ?",
							dbName, tableName, table, column);
		}
		if (source != SourceKeys.NO_SOURCE) {
			result += String.format(
					" AND %s.%s_trad.%2$sTradID = %1$s.%2$s_info.%2$sTradID",
					dbName, tableName);
			result += String.format(
					" AND %s.%s_info.QuellenID = %1$s.Quellen.QuellenID",
					dbName, tableName);
			if (source != SourceKeys.NO_SELECTION
					&& !(tableName.toUpperCase().startsWith("TITEL"))) {
				result += String.format(" AND %s.%s_info.QuellenID = %s",
						dbName, tableName, source);
			}

		}
		result += String.format(" AND %s.%s.%2$sID = %1$s.%s_info.%2$sID",
				dbName, "Person", tableName);
		result += String.format(" AND %s.%s.%s LIKE ?", dbName, table, column);
		return result;
	}

	private String getDate(int[] input) {
		// TODO implement this
		return null;
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
