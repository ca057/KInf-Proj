package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.util.InputMismatchException;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;
import de.uniba.kinf.projm.hylleblomst.logic.UserQuery;

/**
 * This implementation of {@link UserQuery} processes the user input to a SQL
 * statement.
 * 
 * @author Johannes
 * 
 *
 */
public class UserQueryImpl implements UserQuery {
	private SearchFieldKeys searchField;
	private String column;
	private String input;
	private int source;
	private String table;
	private String sqlWhere;
	private Boolean isOR = false;
	private Boolean isOpenSearch = false;
	private Boolean isPersonSearch = false;
	private Boolean isNotationSearch = false;

	/**
	 * 
	 * 
	 * @param searchField
	 *            the {@link SearchFieldKeys} to describe which input field was
	 *            used
	 * @param input
	 *            the user generated input to the {@code searchField}
	 * @param source
	 *            the {@link SourceKeys} to describe the choice of source the
	 *            user made
	 * @param isOr
	 *            a {@code boolean} whether open search is desired
	 * @param isOpenSearch
	 *            a {@code boolean} whether open search is desired
	 */
	public UserQueryImpl(SearchFieldKeys searchField, String input, int source, Boolean isOr, Boolean isOpenSearch) {
		this.isOR = isOr;
		this.isOpenSearch = isOpenSearch;
		setInput(input);
		setSource(source);
		setSearchField(searchField);
		searchFieldKeyToDatabaseData();
	}

	/**
	 * @param personID
	 */
	public UserQueryImpl(String personID) {
		isPersonSearch = true;
		setInput(personID);
		source = SourceKeys.NO_SOURCE;
		table = TableNameKeys.PERSON;
		column = ColumnNameKeys.PERSON_ID;
		sqlWhere = buildSQLWhere();
	}

	/**
	 * @param personID
	 * @param searchField
	 * @param source
	 */
	public UserQueryImpl(SearchFieldKeys searchField, String personID, int source) {
		isNotationSearch = true;
		setSearchField(searchField);
		setInput(personID);
		setSource(source);
		searchFieldKeyToDatabaseData();
		sqlWhere = buildSQLWhere();
	}

	public void setSearchField(SearchFieldKeys searchField) {
		if (searchField == null) {
			throw new InputMismatchException("Das übergebene searchField darf nicht null sein.");
		}
		this.searchField = searchField;
	}

	public void setInput(String input) {
		if (input == null) {
			throw new InputMismatchException("Der übergebene Wert für input darf nicht null sein.");
		}
		this.input = input;
	}

	@Override
	public SearchFieldKeys getSearchField() {
		return searchField;
	}

	@Override
	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		if (source < SourceKeys.bottom || source > SourceKeys.top) {
			throw new InputMismatchException("Der übergebene Wert für source muss einem SourceKey entsprechen.");
		}
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
	public String getInput() {
		return input;
	}

	@Override
	public Boolean isPersonSearch() {
		return isPersonSearch;
	}

	@Override
	public Boolean isOpenSearch() {
		return isOpenSearch;
	}

	@Override
	public Boolean isOrCondition() {
		return isOR;
	}

	/*
	 * Sets the name of the table and column the {@code SearchFieldKey} key
	 * belongs to. Furthermore, it sets the WHERE part of a SQL statement. This
	 * happens here to avoid further iterations.
	 */
	private void searchFieldKeyToDatabaseData() {
		if (source >= SourceKeys.bottom && source <= SourceKeys.top) {
			switch (searchField) {
			case ADLIG:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.ADLIG;
				sqlWhere = String.format("%s.%s <> ''", table, column);
				break;
			case JESUIT:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.JESUIT;
				sqlWhere = String.format("%s.%s <> ''", table, column);
				break;
			case STUDIENJAHR_VON:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.STUDIENJAHR_INT;
				sqlWhere = String.format("%s.%s >= ?", table, column);
				isOpenSearch = false;
				break;
			case STUDIENJAHR_BIS:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.STUDIENJAHR_INT;
				sqlWhere = String.format("%s.%s <= ?", table, column);
				isOpenSearch = false;
				break;
			case EINSCHREIBEDATUM_VON:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.DATUM;
				isOpenSearch = false;
				sqlWhere = String.format("%s.%s >= ?", table, column);
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
				isOpenSearch = false;
				sqlWhere = String.format("%s.%s <= ?", table, column);
				if (input.contains("mm-dd")) {
					input = input.substring(0, input.indexOf("-", 1)) + "-12-31";
				} else if (input.contains("mm")) {
					input = input.substring(0, input.indexOf("-", 2)) + "-12-" + input.substring(8, 9);
				} else if (input.contains("dd")) {
					input = input.substring(0, input.indexOf("-", 3)) + "-31";
				}
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
				isOpenSearch = false;
				break;
			case SEITE_ORIGINALE:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.SEITE_ORIGINAL;
				sqlWhere = buildSQLWhere();
				isOpenSearch = false;
				break;
			case NUMMER_HESS:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.NUMMER_HESS;
				sqlWhere = buildSQLWhere();
				isOpenSearch = false;
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
		if (isOpenSearch) {
			this.input = updateInputForOpenSearch(input);
		}
	}

	/*
	 * Builds the WHERE part of the SQL statement, depending on which fields are
	 * necessary. For example, if the user carried out no selection, the table
	 * with normalized data has to be included. Assumed special case "Ort",
	 * "Ort_Abweichung_Norm" has to be included in the search as well.
	 */
	private String buildSQLWhere() {
		StringBuilder result = new StringBuilder();
		if (!isNotationSearch) {
			result.append(String.format("UPPER(%s.%s)", table, column)).append(getEquationSymbol()).append("UPPER(?)");

			if (source == SourceKeys.NO_SELECTION || source == SourceKeys.ORT_NORM_AB) {
				result.append(String.format(" OR UPPER(%s_norm.%snorm) ", table.substring(0, table.indexOf("_")),
						column.substring(0, column.length() - 4))).append(getEquationSymbol()).append("UPPER(?)");
			}

			if (searchField == SearchFieldKeys.ORT && source == SourceKeys.NORM) {
				return result
						.append(String.format("OR UPPER(%s.%s)", TableNameKeys.ORT_ABWEICHUNG_NORM,
								ColumnNameKeys.ORT_ABWEICHUNG_NORM))
						.append(getEquationSymbol()).append("UPPER(?)").toString();
			}

			if (!(source == SourceKeys.NORM || source == SourceKeys.NO_SOURCE || source == SourceKeys.NO_SELECTION
					|| SearchFieldKeys.ANREDE.equals(searchField) || SearchFieldKeys.TITEL.equals(searchField))) {
				result.append(String.format(" AND %1s_info.%s = %s", table.substring(0, table.indexOf("_")),
						ColumnNameKeys.QUELLEN_ID, source));
			}
		} else {
			if (!(source == SourceKeys.NORM || source == SourceKeys.NO_SOURCE || source == SourceKeys.NO_SELECTION
					|| SearchFieldKeys.ANREDE.equals(searchField) || SearchFieldKeys.TITEL.equals(searchField))) {
				result.append(
						String.format(" %s_info.%s = %s AND %1$s_info.%s = ?", table.substring(0, table.indexOf("_")),
								ColumnNameKeys.QUELLEN_ID, source, ColumnNameKeys.PERSON_ID));
			}
		}
		return result.toString();

	}

	/*
	 * Depending on openSearch, "=" or "like" is chosen for the equation.
	 */
	private String getEquationSymbol() {
		if (isOpenSearch) {
			return (" LIKE ");
		} else {
			return " = ";
		}
	}

	/*
	 * This method updates the input, if open search is selected. It inserts "%"
	 * before/after every character.
	 */
	private String updateInputForOpenSearch(String input) {
		StringBuilder newInput = new StringBuilder().append(input.substring(0, 1));
		for (int i = 1; i < input.length(); i++) {
			newInput.append("%" + input.substring(i, i + 1));
		}
		return "%" + newInput + "%";
	}
}
