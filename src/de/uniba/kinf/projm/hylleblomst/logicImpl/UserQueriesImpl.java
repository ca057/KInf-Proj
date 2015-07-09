package de.uniba.kinf.projm.hylleblomst.logicImpl;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;
import de.uniba.kinf.projm.hylleblomst.logic.UserQueries;

/**
 * @author Johannes
 *
 */
public class UserQueriesImpl implements UserQueries {
	private SearchFieldKeys searchField;
	private String column;
	private String input;
	private int source;
	private String table;
	private String sqlWhere;
	private Boolean isOR = false;
	private Boolean isOpenSearch = false;

	/**
	 * @param searchField
	 * @param input
	 * @param source
	 * @param isOr
	 * @param isOpenSearch
	 */
	public UserQueriesImpl(SearchFieldKeys searchField, String input, int source, Boolean isOr, Boolean isOpenSearch) {
		this.isOR = isOr;
		this.isOpenSearch = isOpenSearch;
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
		// searchFieldKeyToDatabaseData();
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

	@Override
	public String getWhere() {
		return sqlWhere;
	}

	@Override
	public Boolean isOpenSearch() {
		return isOpenSearch;
	}

	@Override
	public Boolean useOrCondition() {
		return isOR;
	}

	/*
	 * Returns the name of the table the {@code SearchFieldKey} key belongs to.
	 */
	private void searchFieldKeyToDatabaseData() {
		if (source > SourceKeys.bottom && source < SourceKeys.top) {
			switch (searchField) {
			case ADLIG:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.ADLIG;
				sqlWhere = String.format("%s.%s <> ''", table, column);
				// source = SourceKeys.NO_SOURCE;
				break;
			case JESUIT:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.JESUIT;
				sqlWhere = String.format("%s.%s <> ''", table, column);
				break;
			case STUDIENJAHR_VON:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.STUDIENJAHR_INT;
				sqlWhere = String.format("%s.%s => ?", table, column);
				break;
			case STUDIENJAHR_BIS:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.STUDIENJAHR_INT;
				sqlWhere = String.format("%s.%s <= ?", table, column);
				break;
			case EINSCHREIBEDATUM_VON:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.DATUM;
				sqlWhere = String.format("%s.%s => ?", table, column);
				if (input.contains("mm-dd")) {
					input = input.substring(0, input.indexOf("-", 1)) + "-01-01";
				} else if (input.contains("mm")) {
					input = input.substring(0, input.indexOf("-", 2)) + "-01-" + input.substring(8, 9);
				} else if (input.contains("dd")) {
					input = input.substring(0, input.indexOf("-", 3)) + "-01";
				}
				break;
			case EINSCHREIBEDATUM_BIS:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.DATUM;
				sqlWhere = String.format("%s.%s <= ?", table, column);
				if (input.contains("mm-dd")) {
					input = input.substring(0, input.indexOf("-", 1)) + "-12-31";
				} else if (input.contains("mm")) {
					input = input.substring(0, input.indexOf("-", 2)) + "-12-" + input.substring(8, 9);
				} else if (input.contains("dd")) {
					input = input.substring(0, input.indexOf("-", 3)) + "-31";
				}
				System.out.println(input);
				break;
			case ANMERKUNGEN:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.ANMERKUNG;
				sqlWhere = buildSQLWhere();
				break;
			case NUMMER:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.PERSON_ID;
				sqlWhere = buildSQLWhere();
				break;
			case SEITE_ORIGINALE:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.SEITE_ORIGINAL;
				sqlWhere = buildSQLWhere();
				break;
			case NUMMER_HESS:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.NUMMER_HESS;
				sqlWhere = buildSQLWhere();
				break;
			case ANREDE:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.ANREDE_NORM;
					column = ColumnNameKeys.ANREDE_NORM;
				} else {
					table = TableNameKeys.ANREDE_TRAD;
					column = ColumnNameKeys.ANREDE_TRAD;
				}
				sqlWhere = buildSQLWhere();
				break;
			case TITEL:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.TITEL_NORM;
					column = ColumnNameKeys.TITEL_NORM;
				} else {
					table = TableNameKeys.TITEL_TRAD;
					column = ColumnNameKeys.TITEL_TRAD;
				}
				sqlWhere = buildSQLWhere();
				break;
			case VORNAME:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.VORNAME_NORM;
					column = ColumnNameKeys.VORNAME_NORM;
				} else {
					table = TableNameKeys.VORNAME_TRAD;
					column = ColumnNameKeys.VORNAME_TRAD;
				}
				sqlWhere = buildSQLWhere();
				break;
			case NACHNAME:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.NAME_NORM;
					column = ColumnNameKeys.NAME_NORM;
				} else {
					table = TableNameKeys.NAME_TRAD;
					column = ColumnNameKeys.NAME_TRAD;
				}
				sqlWhere = buildSQLWhere();
				break;
			case WIRTSCHAFTSLAGE:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.WIRTSCHAFTSLAGE_NORM;
					column = ColumnNameKeys.WIRTSCHAFTSLAGE_NORM;
				} else {
					table = TableNameKeys.WIRTSCHAFTSLAGE_TRAD;
					column = ColumnNameKeys.WIRTSCHAFTSLAGE_TRAD;
				}
				sqlWhere = buildSQLWhere();
				break;
			case ORT:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.ORT_NORM;
					column = ColumnNameKeys.ORT_NORM;
				} else if (source == SourceKeys.ORT_NORM_AB) {
					table = TableNameKeys.ORT_ABWEICHUNG_NORM;
					column = ColumnNameKeys.ORT_ABWEICHUNG_NORM;
				} else {
					table = TableNameKeys.ORT_TRAD;
					column = ColumnNameKeys.ORT_TRAD;
				}
				sqlWhere = buildSQLWhere();
				break;
			case FACH:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.FACH_NORM;
					column = ColumnNameKeys.FACH_NORM;
				} else {
					table = TableNameKeys.FACH_TRAD;
					column = ColumnNameKeys.FACH_TRAD;
				}
				sqlWhere = buildSQLWhere();
				break;
			case FAKULTAETEN:
				table = TableNameKeys.FAKULTAETEN;
				column = ColumnNameKeys.FAKULTAETEN_NORM;
				break;
			case SEMINAR:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.SEMINAR_NORM;
					column = ColumnNameKeys.SEMINAR_NORM;
				} else {
					table = TableNameKeys.SEMINAR_TRAD;
					column = ColumnNameKeys.SEMINAR_TRAD;
				}
				sqlWhere = buildSQLWhere();
				break;
			case GRADUIERT:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.GRADUIERT;
				sqlWhere = String.format("%s.%s <> ''", table, column);
				break;
			case ZUSAETZE:
				table = TableNameKeys.ZUSAETZE;
				column = ColumnNameKeys.ZUSAETZE;
				sqlWhere = buildSQLWhere();
				break;
			case FUNDORTE:
				table = TableNameKeys.FUNDORTE;
				column = ColumnNameKeys.FUNDORTE_NORM;
				sqlWhere = buildSQLWhere();
				break;
			default:
				throw new IllegalArgumentException(
						"Das zugehörige Tabellenelement für Suchfeld " + searchField.name() + " ist nicht definiert.");
			}
		} else {
			throw new IllegalArgumentException("Die Werte für Suchfeld " + searchField.toString() + " und Quelle "
					+ source + " konnten keiner Tabelle und Spalte zugeordnet werden.");
		}
	}

	/*
	 * 
	 */
	private String buildSQLWhere() {
		if (isOpenSearch) {

			updateInputForOpenSearch();

			if (source == SourceKeys.NO_SELECTION) {
				return String.format("UPPER(%s.%s) LIKE UPPER(?) OR UPPER(%s_norm.%snorm) LIKE UPPER(?)", table, column,
						table.substring(0, table.indexOf("_")), column.substring(0, column.lastIndexOf("t")));
			}
			if (source == SourceKeys.NO_SOURCE) {
				// || searchField == SearchFieldKeys.ANREDE || searchField ==
				// SearchFieldKeys.TITEL) {
				return String.format("UPPER(%s.%s) LIKE UPPER(?)", table, column);
			}
			if (source == SourceKeys.NORM) {
				return String.format("UPPER(%s_norm.%s) LIKE UPPER(?)", table.substring(0, table.indexOf("_")), column);
			}
			return String.format("UPPER(%s.%s) LIKE UPPER(?) AND %1s_info.%s = %s", table, column,
					table.substring(0, table.indexOf("_")), ColumnNameKeys.QUELLEN_ID, source);

		} else {
			if (source == SourceKeys.NO_SELECTION) {
				return String.format("UPPER(%s.%s) = UPPER(?) OR UPPER(%s_norm.%snorm) = UPPER(?)", table, column,
						table.substring(0, table.indexOf("_")), column.substring(0, column.lastIndexOf("t")));
			}
			if (source == SourceKeys.NO_SOURCE) {
				// || searchField == SearchFieldKeys.ANREDE || searchField ==
				// SearchFieldKeys.TITEL) {
				return String.format("UPPER(%s.%s) = UPPER(?) ", table, column);
			}
			if (source == SourceKeys.NORM) {
				return String.format("UPPER(%s_norm.%s) = UPPER(?) ", table.substring(0, table.indexOf("_")), column,
						"%");
			}

			return String.format("UPPER(%s.%s) = UPPER(?) AND %1s_info.%s = %s", table, column,
					table.substring(0, table.indexOf("_")), ColumnNameKeys.QUELLEN_ID, source);
		}
	}

	/*
	 * 
	 */
	private void updateInputForOpenSearch() {
		StringBuilder newInput = new StringBuilder().append(input.substring(0, 1));
		for (int i = 1; i < input.length(); i++) {
			newInput.append("%" + input.substring(i, i + 1));
			System.out.println(newInput);
		}
		input = "%" + newInput + "%";
	}
}
