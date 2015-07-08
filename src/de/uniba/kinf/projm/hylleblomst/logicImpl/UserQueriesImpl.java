package de.uniba.kinf.projm.hylleblomst.logicImpl;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;
import de.uniba.kinf.projm.hylleblomst.logic.UserQueries;

public class UserQueriesImpl implements UserQueries {
	private SearchFieldKeys searchField;
	private String column;
	private String input;
	private int source;
	private String table;

	public UserQueriesImpl(SearchFieldKeys searchField, String input, int source) {
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
			switch (searchField) {
			case ADLIG:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.ADLIG;
				break;
			case JESUIT:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.JESUIT;
				break;
			case STUDIENJAHR_VON:
			case STUDIENJAHR_BIS:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.STUDIENJAHR_INT;
				break;
			case EINSCHREIBEDATUM_VON:
			case EINSCHREIBEDATUM_BIS:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.DATUM;
				break;
			case ANMERKUNGEN:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.ANMERKUNG;
				break;
			case NUMMER:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.PERSON_ID;
				break;
			case SEITE_ORIGINALE:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.SEITE_ORIGINAL;
				break;
			case NUMMER_HESS:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.NUMMER_HESS;
				break;
			case ANREDE:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.ANREDE_NORM;
					column = ColumnNameKeys.ANREDE_NORM;
				} else {
					table = TableNameKeys.ANREDE_TRAD;
					column = ColumnNameKeys.ANREDE_TRAD;
				}
				break;
			case TITEL:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.TITEL_NORM;
					column = ColumnNameKeys.TITEL_NORM;
				} else {
					table = TableNameKeys.TITEL_TRAD;
					column = ColumnNameKeys.TITEL_TRAD;
				}
				break;
			case VORNAME:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.VORNAME_NORM;
					column = ColumnNameKeys.VORNAME_NORM;
				} else {
					table = TableNameKeys.VORNAME_TRAD;
					column = ColumnNameKeys.VORNAME_TRAD;
				}
				break;
			case NACHNAME:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.NAME_NORM;
					column = ColumnNameKeys.NAME_NORM;
				} else {
					table = TableNameKeys.NAME_TRAD;
					column = ColumnNameKeys.NAME_TRAD;
				}
				break;
			case WIRTSCHAFTSLAGE:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.WIRTSCHAFTSLAGE_NORM;
					column = ColumnNameKeys.WIRTSCHAFTSLAGE_NORM;
				} else {
					table = TableNameKeys.WIRTSCHAFTSLAGE_TRAD;
					column = ColumnNameKeys.WIRTSCHAFTSLAGE_TRAD;
				}
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
				break;
			case FACH:
				if (source == SourceKeys.NORM) {
					table = TableNameKeys.FACH_NORM;
					column = ColumnNameKeys.FACH_NORM;
				} else {
					table = TableNameKeys.FACH_TRAD;
					column = ColumnNameKeys.FACH_TRAD;
				}
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
				break;
			case GRADUIERT:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.GRADUIERT;
				break;
			case ZUSAETZE:
				table = TableNameKeys.ZUSAETZE;
				column = ColumnNameKeys.ZUSAETZE;
				break;
			case FUNDORTE:
				table = TableNameKeys.FUNDORTE;
				column = ColumnNameKeys.FUNDORTE_NORM;
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

	@Override
	public String getWhere() {
		if (source == SourceKeys.NORM) {
			return String.format("%s_norm.%s LIKE ?", table.substring(0, table.indexOf("_")), column);
		}
		if (source == SourceKeys.NO_SOURCE) {
			if (searchField == SearchFieldKeys.ADLIG || searchField == SearchFieldKeys.JESUIT
					|| searchField == SearchFieldKeys.GRADUIERT) {
				return String.format("%s.%s <> ''", table, column);
			}
			if (searchField == SearchFieldKeys.STUDIENJAHR_VON) {
				return String.format("%s.%s > ?", table, column);
			}
			if (searchField == SearchFieldKeys.STUDIENJAHR_BIS) {
				return String.format("%s.%s < ?", table, column);
			}
			if (searchField == SearchFieldKeys.EINSCHREIBEDATUM_BIS) {
				if (input.contains("mm-dd")) {
					input = input.substring(0, 3) + "-12-31";
				} else if (input.contains("mm")) {
					input = input.substring(0, 3) + "-12-" + input.substring(8, 9);
				} else if (input.contains("dd")) {
					input = input.substring(0, 6) + "-31";
				}
				return String.format("%s.%s < ?", table, column);
			}
			if (searchField == SearchFieldKeys.EINSCHREIBEDATUM_VON) {
				if (input.contains("mm-dd")) {
					input = input.substring(0, input.indexOf("-", 1)) + "-01-01";
				} else if (input.contains("mm")) {
					input = input.substring(0, input.indexOf("-", 2)) + "-01-" + input.substring(8, 9);
				} else if (input.contains("dd")) {
					input = input.substring(0, input.indexOf("-", 3)) + "-01";
				}
				return String.format("%s.%s > ?", table, column);
			}
		}
		if (!(source == SourceKeys.NO_SELECTION || searchField == SearchFieldKeys.ANREDE
				|| searchField == SearchFieldKeys.TITEL)) {
			return String.format("%s.%s LIKE ? AND %s_info.%s = %s", table, column,
					table.substring(0, table.indexOf("_")), ColumnNameKeys.QUELLEN_ID, source);
		}
		return String.format("%s.%s LIKE ? ", table, column);
	}
}
