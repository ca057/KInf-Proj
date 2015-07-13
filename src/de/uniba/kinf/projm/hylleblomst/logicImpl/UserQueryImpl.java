package de.uniba.kinf.projm.hylleblomst.logicImpl;

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
	private int numberOfInputs = 0;
	private Boolean isOR = false;
	private Boolean isOpenSearch = false;
	private Boolean isPersonSearch = false;
	private Boolean isInt = false;

	/**
	 * Use this constructor to
	 * 
	 * @param searchField
	 *            the {@link SearchFieldKeys} to describe which input field was
	 *            used
	 * @param input
	 *            the user generated input to the {@code searchField}
	 * @param source
	 *            an {@code int} value from {@link SourceKeys} to describe the
	 *            choice of source the user made
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
	 * This constructor initiates a UserQuery to find all information about a
	 * person.
	 * 
	 * @param personID
	 *            the ID of the person.
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
	 * This constructor initiates a UserQuery to find information for a person
	 * about a specific search field with specific source.
	 * 
	 * @param personID
	 *            the ID of the person
	 * @param searchField
	 *            the relevant search field
	 * @param source
	 *            the source wanted as {@code int} value of {@link SourceKeys}
	 */
	public UserQueryImpl(SearchFieldKeys searchField, String personID, int source) {
		setSearchField(searchField);
		setInput(personID);
		setSource(source);
		searchFieldKeyToDatabaseData();
		sqlWhere = buildSQLWhereNotation();
	}

	public void setSearchField(SearchFieldKeys searchField) {
		if (searchField == null) {
			throw new IllegalArgumentException("Das übergebene searchField darf nicht null sein.");
		}
		this.searchField = searchField;
	}

	public void setInput(String input) {
		if (input == null) {
			throw new IllegalArgumentException("Der übergebene Wert für input darf nicht null sein.");
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
			throw new IllegalArgumentException("Der übergebene Wert für source muss einem SourceKey entsprechen.");
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
	public Object getInput() {
		return input;
	}

	@Override
	public Boolean isInt() {
		return isInt;
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

	@Override
	public int getNumberOfInputs() {
		return numberOfInputs;
	}

	/*
	 * Sets the name of the table and column the {@code SearchFieldKey} key
	 * belongs to. Furthermore, it sets the WHERE part of a SQL statement. This
	 * happens here to avoid further iterations.
	 */
	private void searchFieldKeyToDatabaseData() {
		if (source < SourceKeys.bottom && source > SourceKeys.top) {
			throw new IllegalArgumentException("Die Werte für Suchfeld " + searchField.toString() + " und Quelle "
					+ source + " konnten keiner Tabelle und Spalte zugeordnet werden.");
		} else {
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
				validateInputForYear();
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.STUDIENJAHR_INT;
				sqlWhere = String.format("%s.%s >= ?", table, column);
				numberOfInputs++;
				isOpenSearch = false;
				isInt = true;
				break;
			case STUDIENJAHR_BIS:
				validateInputForYear();
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.STUDIENJAHR_INT;
				sqlWhere = String.format("%s.%s <= ?", table, column);
				numberOfInputs++;
				isOpenSearch = false;
				isInt = true;
				break;
			case EINSCHREIBEDATUM_VON:
				validateInputForYear();
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.DATUM;
				isOpenSearch = false;
				isInt = true;
				sqlWhere = String.format("%s.%s >= ?", table, column);
				numberOfInputs++;
				if (input.contains("mm-dd")) {
					input = input.substring(0, input.indexOf("-", 1)) + "-01-01";
				} else if (input.contains("mm")) {
					input = input.substring(0, input.indexOf("-", 3)) + "-01-" + input.substring(8, 9);
				} else if (input.contains("dd")) {
					input = input.substring(0, input.lastIndexOf("-")) + "-01";
				}
				break;
			case EINSCHREIBEDATUM_BIS:
				validateInputForYear();
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.DATUM;
				isOpenSearch = false;
				isInt = true;
				sqlWhere = String.format("%s.%s <= ?", table, column);
				numberOfInputs++;
				if (input.contains("mm-dd")) {
					input = input.substring(0, input.indexOf("-", 1)) + "-12-31";
				} else if (input.contains("mm")) {
					input = input.substring(0, input.indexOf("-", 3)) + "-12-" + input.substring(8, 9);
				} else if (input.contains("dd")) {
					input = input.substring(0, input.lastIndexOf("-")) + "-31";
				}
				break;
			case ANMERKUNGEN:
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.ANMERKUNG;
				sqlWhere = buildSQLWhere();
				break;
			case NUMMER:
				isOpenSearch = false;
				isInt = true;
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.PERSON_ID;
				sqlWhere = buildSQLWhere();
				break;
			case SEITE_ORIGINALE:
				isOpenSearch = false;
				isInt = true;
				table = TableNameKeys.PERSON;
				column = ColumnNameKeys.SEITE_ORIGINAL;
				sqlWhere = buildSQLWhere();
				break;
			case NUMMER_HESS:
				isOpenSearch = false;
				isInt = true;
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
				sqlWhere = buildSQLWhere();
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
		}
		if (isOpenSearch) {
			updateInputForOpenSearch();
		}
	}

	/*
	 * Builds the WHERE part of the SQL statement, depending on which fields are
	 * necessary. For example, if the user carried out no selection, the table
	 * with normalized data has to be included. Assumed special case "Ort",
	 * "Ort_Abweichung_Norm" has to be included in the search as well.
	 */
	private String buildSQLWhere() {
		numberOfInputs++;
		StringBuilder result = new StringBuilder();
		result.append("(");
		String upperFront = "";
		String upperEnd = "";
		if (!isInt) {
			upperFront = "UPPER(";
			upperEnd = ")";
		}
		result.append(String.format("%s%s.%s%s", upperFront, table, column, upperEnd)).append(getEquationSymbol())
				.append(String.format("%s?%s", upperFront, upperEnd));
		if ((source == SourceKeys.NO_SELECTION || source == SourceKeys.ORT_NORM_AB)
				&& !SearchFieldKeys.ZUSAETZE.equals(searchField)) {
			result.append(String.format(" OR UPPER(%s_norm.%snorm) ", table.substring(0, table.indexOf("_")),
					column.substring(0, column.length() - 4))).append(getEquationSymbol()).append("UPPER(?)");
			numberOfInputs++;
		}
		result.append(")");

		if (searchField == SearchFieldKeys.ORT && source == SourceKeys.NORM) {
			numberOfInputs++;
			return result
					.append(String.format(" OR UPPER(%s.%s)", TableNameKeys.ORT_ABWEICHUNG_NORM,
							ColumnNameKeys.ORT_ABWEICHUNG_NORM))
					.append(getEquationSymbol()).append("UPPER(?)").toString();
		}

		if (!(source == SourceKeys.NORM || source == SourceKeys.NO_SOURCE || source == SourceKeys.NO_SELECTION
				|| source == SourceKeys.ORT_NORM_AB || SearchFieldKeys.ANREDE.equals(searchField)
				|| SearchFieldKeys.TITEL.equals(searchField) || SearchFieldKeys.ZUSAETZE.equals(searchField))) {
			result.append(String.format(" AND %1s_info.%s = %s", table.substring(0, table.indexOf("_")),
					ColumnNameKeys.QUELLEN_ID, source));
		}
		return result.toString();
	}

	/*
	 * Sets the WHERE part, if a notation of a field (i.e. name) is searched.
	 */
	private String buildSQLWhereNotation() {
		StringBuilder result = new StringBuilder();
		result.append(String.format("%s.%s = ?", TableNameKeys.PERSON, ColumnNameKeys.PERSON_ID));
		numberOfInputs++;
		if (SearchFieldKeys.ZUSAETZE.equals(searchField)) {
			result.append(String.format(" AND %s_info.%s = %s", table, ColumnNameKeys.QUELLEN_ID, source));
		} else if (!(source == SourceKeys.NORM || source == SourceKeys.NO_SOURCE || source == SourceKeys.NO_SELECTION
				|| source == SourceKeys.ORT_NORM_AB || SearchFieldKeys.ANREDE.equals(searchField)
				|| SearchFieldKeys.TITEL.equals(searchField))) {
			result.append(String.format(" AND %s_info.%s = %s", table.substring(0, table.indexOf("_")),
					ColumnNameKeys.QUELLEN_ID, source));
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
	private void updateInputForOpenSearch() {
		StringBuilder newInput = new StringBuilder().append(input.substring(0, 1));
		for (int i = 1; i < input.length(); i++) {
			newInput.append("%" + input.substring(i, i + 1));
		}
		this.input = "%" + newInput + "%";
	}

	private void validateInputForYear() {
		try {
			Integer.parseInt(input.substring(0, 4));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Der Input in einem Datumsfeld muss aus Zahlen bestehen");
		}
	}
}
