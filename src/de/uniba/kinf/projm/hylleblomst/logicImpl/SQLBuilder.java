package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;
import de.uniba.kinf.projm.hylleblomst.logic.UserQuery;

/**
 * This class builds a SQL statement for all inputs the user made. It presumes
 * that a {@link PreparedStatement} is used. For further information consult the
 * respective Javadoc of the methods.
 * 
 * @author Johannes
 *
 */
public class SQLBuilder {

	private Collection<UserQuery> queryCollection;
	private ArrayList<Object> inputs = new ArrayList<Object>();;
	private StringBuilder sqlStatement = new StringBuilder();
	private Boolean whereIsEmpty = true;
	private Boolean needsStandardFields = true;
	private Boolean hasDate = false;
	private Boolean hasStudyYear = false;
	private String standardSelection = ColumnNameKeys.PERSON_ID + ", " + ColumnNameKeys.VORNAME_NORM + ", "
			+ ColumnNameKeys.NAME_NORM + ", " + ColumnNameKeys.ORT_NORM + ", " + ColumnNameKeys.FAKULTAETEN_NORM;

	/**
	 * With this constructor, the sql-Statement to execute a search hit by the
	 * search mask is created.
	 * 
	 * @param queryCollection
	 * @throws SQLException
	 */
	public SQLBuilder(Collection<UserQuery> userQuery) throws SQLException {
		if (userQuery == null || userQuery.isEmpty()) {
			throw new IllegalArgumentException("Die übergebene Collection darf nicht leer bzw. null sein.");
		}
		this.queryCollection = userQuery;
		buildSearchMask();
		print();
	}

	/*
	 * Use this to see the finished SQL statements (with the commented line 48
	 * and 76 (more or less))
	 */
	private void print() {
		System.out.println(sqlStatement);
	}

	/**
	 * With this constructor, the sql-Statement to execute a search of a person
	 * or a notation of a specific search field related to a person
	 * 
	 * @param queryCollection
	 * @throws SQLException
	 */
	public SQLBuilder(UserQuery userQuery) throws SQLException {
		if (userQuery == null || userQuery.getInput() == null) {
			throw new IllegalArgumentException("Das übergebene Query und dessen input dürfen nicht null sein.");
		}
		inputs.add(userQuery.getInput());
		if (userQuery.isPersonSearch()) {
			buildPersonSearch();
		} else {
			buildNotationSearch(userQuery);
		}
		// print();
	}

	public String getSQLStatement() {
		return sqlStatement.toString();
	}

	public List<Object> getInputs() {
		return inputs;
	}

	/*
	 * Builds the SQL-statement for a search hit by the search mask
	 */
	private void buildSearchMask() throws SQLException {

		StringBuilder sqlSelect = new StringBuilder();
		StringBuilder sqlWhere = new StringBuilder();
		StringBuilder sqlNestedSelect = new StringBuilder();
		StringBuilder sqlGroupBy = new StringBuilder();
		iterateOverQueries(sqlSelect, sqlWhere, sqlNestedSelect, sqlGroupBy);

		sqlStatement.append("SELECT DISTINCT " + standardSelection).append(sqlSelect).append(" FROM (SELECT DISTINCT ")
				.append(sqlNestedSelect).append(" FROM " + buildFrom()).append(" WHERE ").append(sqlWhere)
				.append(") T ").append(" GROUP BY " + standardSelection).append(sqlGroupBy);

		resetBooleans();
	}

	/*
	 * Builds the whole SQL-statement needed to find all available information
	 * of a person by searching with the ID of this person.
	 */
	private void buildPersonSearch() {
		sqlStatement.append("SELECT DISTINCT " + buildSelectPersonDetails()).append(" FROM " + buildFrom())
				.append(" WHERE " + TableNameKeys.PERSON).append("." + ColumnNameKeys.PERSON_ID).append(" = ?");
	}

	/*
	 * Builds the SQL-statement needed to find a notation of a person data with
	 * specific source.
	 */
	private void buildNotationSearch(UserQuery query) {
		needsStandardFields = false;
		sqlStatement.append("SELECT DISTINCT " + buildSelectNotation(query)).append(" FROM " + buildFrom())
				.append(" WHERE ").append(query.getWhere());
	}

	/*
	 * This method iterates over a query collection and searches all relevant
	 * information
	 */
	private void iterateOverQueries(StringBuilder sqlSelect, StringBuilder sqlWhere, StringBuilder sqlNestedSelect,
			StringBuilder sqlGroupBy) {
		for (UserQuery query : queryCollection) {
			sqlWhere.append(buildWhere(query));
			sqlNestedSelect.append(buildNestedSelect(query));
			if (!query.isInt()) {
				sqlSelect.append(", " + TableNameKeys.SCHEMA_NAME + ".AGGREGATE_VARCHAR(' ' || ");
			}
			if (ColumnNameKeys.DATUM.equals(query.getColumn()) && !hasDate) {
				sqlSelect.append(", " + query.getColumn()).append(", " + ColumnNameKeys.DATUMS_FELDER_GESETZT);
				sqlGroupBy.append(", " + query.getColumn()).append(", " + ColumnNameKeys.DATUMS_FELDER_GESETZT);
				hasDate = true;
			} else if (ColumnNameKeys.STUDIENJAHR_INT.equals(query.getColumn()) && !hasStudyYear) {
				sqlSelect.append(", " + ColumnNameKeys.STUDIENJAHR);
				sqlGroupBy.append(", " + ColumnNameKeys.STUDIENJAHR);
				hasStudyYear = true;
			} else if (query.isInt()) {
				sqlGroupBy.append(", " + query.getColumn());
			} else if (!(ColumnNameKeys.DATUM.equals(query.getColumn())
					|| ColumnNameKeys.STUDIENJAHR_INT.equals(query.getColumn()))) {
				sqlSelect.append(query.getColumn());
			}
			if (!query.isInt()) {
				sqlSelect.append(") AS " + query.getColumn());
			}
			for (int i = 1; i <= query.getNumberOfInputs(); i++) {
				inputs.add(query.getInput());
			}
		}
	}

	/*
	 * Builds the SELECT part of the SQL statement needed for queries hit by the
	 * search mask.
	 */
	private String buildNestedSelect(UserQuery userQuery) {
		String result = "";
		if (needsStandardFields) {
			result = addStandardFieldsASColumnName();
			needsStandardFields = false;
		}
		if (ColumnNameKeys.STUDIENJAHR_INT.equals(userQuery.getColumn()) && !hasStudyYear) {
			result += ", " + userQuery.getTable() + "." + ColumnNameKeys.STUDIENJAHR + " AS "
					+ ColumnNameKeys.STUDIENJAHR + ", " + userQuery.getTable() + "." + userQuery.getColumn();
		} else if (ColumnNameKeys.DATUM.equals(userQuery.getColumn()) && !hasDate) {
			result += ", " + userQuery.getTable() + "." + ColumnNameKeys.DATUM + " AS " + ColumnNameKeys.DATUM + ", "
					+ userQuery.getTable() + "." + ColumnNameKeys.DATUMS_FELDER_GESETZT + " AS "
					+ ColumnNameKeys.DATUMS_FELDER_GESETZT;
		} else if (!(isStandardField(userQuery.getColumn()) || ColumnNameKeys.DATUM.equals(userQuery.getColumn())
				|| ColumnNameKeys.STUDIENJAHR_INT.equals(userQuery.getColumn()))) {
			result += ", " + userQuery.getTable() + "." + userQuery.getColumn() + " AS " + userQuery.getColumn();
		}
		if (userQuery.getSource() == SourceKeys.ORT_NORM_AB || (SearchFieldKeys.ORT.equals(userQuery.getSearchField())
				&& userQuery.getSource() == SourceKeys.NORM)) {
			result += ", " + TableNameKeys.ORT_ABWEICHUNG_NORM + "." + ColumnNameKeys.ORT_ABWEICHUNG_NORM + " AS "
					+ ColumnNameKeys.ORT_ABWEICHUNG_NORM;
		}
		return result;
	}

	/*
	 * If every normalized column (or other, without normalization) of the
	 * database is wanted, this method provides the suitable SELECT part.
	 */
	private String buildSelectPersonDetails() {
		return TableNameKeys.PERSON + "." + ColumnNameKeys.PERSON_ID + " AS " + ColumnNameKeys.PERSON_ID + ", "
				+ TableNameKeys.ANREDE_NORM + "." + ColumnNameKeys.ANREDE_NORM + " AS " + ColumnNameKeys.ANREDE_NORM
				+ ", " + TableNameKeys.TITEL_NORM + "." + ColumnNameKeys.TITEL_NORM + " AS " + ColumnNameKeys.TITEL_NORM
				+ ", " + TableNameKeys.VORNAME_NORM + "." + ColumnNameKeys.VORNAME_NORM + " AS "
				+ ColumnNameKeys.VORNAME_NORM + ", " + TableNameKeys.NAME_NORM + "." + ColumnNameKeys.NAME_NORM + " AS "
				+ ColumnNameKeys.NAME_NORM + ", " + TableNameKeys.PERSON + "." + ColumnNameKeys.ADLIG + " AS "
				+ ColumnNameKeys.ADLIG + ", " + TableNameKeys.PERSON + "." + ColumnNameKeys.JESUIT + " AS "
				+ ColumnNameKeys.JESUIT + ", " + TableNameKeys.WIRTSCHAFTSLAGE_NORM + "."
				+ ColumnNameKeys.WIRTSCHAFTSLAGE_NORM + " AS " + ColumnNameKeys.WIRTSCHAFTSLAGE_NORM + ", "
				+ TableNameKeys.ORT_NORM + "." + ColumnNameKeys.ORT_NORM + " AS " + ColumnNameKeys.ORT_NORM + ", "
				+ TableNameKeys.FACH_NORM + "." + ColumnNameKeys.FACH_NORM + " AS " + ColumnNameKeys.FACH_NORM + ", "
				+ TableNameKeys.FAKULTAETEN + "." + ColumnNameKeys.FAKULTAETEN_NORM + " AS "
				+ ColumnNameKeys.FAKULTAETEN_NORM + ", " + TableNameKeys.SEMINAR_NORM + "."
				+ ColumnNameKeys.SEMINAR_NORM + " AS " + ColumnNameKeys.SEMINAR_NORM + ", " + TableNameKeys.PERSON + "."
				+ ColumnNameKeys.GRADUIERT + " AS " + ColumnNameKeys.GRADUIERT + ", " + TableNameKeys.PERSON + "."
				+ ColumnNameKeys.STUDIENJAHR + " AS " + ColumnNameKeys.STUDIENJAHR + ", " + TableNameKeys.PERSON + "."
				+ ColumnNameKeys.DATUM + " AS " + ColumnNameKeys.DATUM + ", " + TableNameKeys.PERSON + "."
				+ ColumnNameKeys.DATUMS_FELDER_GESETZT + " AS " + ColumnNameKeys.DATUMS_FELDER_GESETZT + ", "
				+ TableNameKeys.ZUSAETZE + "." + ColumnNameKeys.ZUSAETZE + " AS " + ColumnNameKeys.ZUSAETZE + ", "
				+ TableNameKeys.FUNDORTE + "." + ColumnNameKeys.FUNDORTE_NORM + " AS " + ColumnNameKeys.FUNDORTE_NORM
				+ ", " + TableNameKeys.PERSON + "." + ColumnNameKeys.ANMERKUNG + " AS " + ColumnNameKeys.ANMERKUNG
				+ ", " + TableNameKeys.PERSON + "." + ColumnNameKeys.SEITE_ORIGINAL + " AS "
				+ ColumnNameKeys.SEITE_ORIGINAL + ", " + TableNameKeys.PERSON + "." + ColumnNameKeys.NUMMER_HESS;
	}

	/*
	 * Builds the SELECT part for a SQL statement which searches for a notation
	 * with a specific source
	 */
	private Object buildSelectNotation(UserQuery query) {
		return query.getTable() + "." + query.getColumn() + " AS " + query.getSearchField();
	}

	/*
	 * This is a helper method to build the FROM-part of every SQL-statement as
	 * string. It contains all tables of the database.
	 */
	private String buildFrom() {
		String vorname = TableNameKeys.PERSON + " LEFT OUTER JOIN " + TableNameKeys.VORNAME_INFO + " ON "
				+ TableNameKeys.PERSON + "." + ColumnNameKeys.PERSON_ID + " = " + TableNameKeys.VORNAME_INFO + "."
				+ ColumnNameKeys.PERSON_ID + " LEFT OUTER JOIN " + TableNameKeys.VORNAME_TRAD + " ON "
				+ TableNameKeys.VORNAME_TRAD + "." + ColumnNameKeys.VORNAME_TRAD_ID + " = " + TableNameKeys.VORNAME_INFO
				+ "." + ColumnNameKeys.VORNAME_TRAD_ID + " LEFT OUTER JOIN " + TableNameKeys.VORNAME_NORM + " ON "
				+ TableNameKeys.VORNAME_NORM + "." + ColumnNameKeys.VORNAME_NORM_ID + " = " + TableNameKeys.VORNAME_TRAD
				+ "." + ColumnNameKeys.VORNAME_NORM_ID;
		String name = TableNameKeys.NAME_INFO + " ON " + TableNameKeys.PERSON + "." + ColumnNameKeys.PERSON_ID + " = "
				+ TableNameKeys.NAME_INFO + "." + ColumnNameKeys.PERSON_ID + " LEFT OUTER JOIN "
				+ TableNameKeys.NAME_TRAD + " ON " + TableNameKeys.NAME_TRAD + "." + ColumnNameKeys.NAME_TRAD_ID + " = "
				+ TableNameKeys.NAME_INFO + "." + ColumnNameKeys.NAME_TRAD_ID + " LEFT OUTER JOIN "
				+ TableNameKeys.NAME_NORM + " ON " + TableNameKeys.NAME_NORM + "." + ColumnNameKeys.NAME_NORM_ID + " = "
				+ TableNameKeys.NAME_TRAD + "." + ColumnNameKeys.NAME_NORM_ID;
		String ort = TableNameKeys.ORT_INFO + " ON " + TableNameKeys.PERSON + "." + ColumnNameKeys.PERSON_ID + " = "
				+ TableNameKeys.ORT_INFO + "." + ColumnNameKeys.PERSON_ID + " LEFT OUTER JOIN " + TableNameKeys.ORT_TRAD
				+ " ON " + TableNameKeys.ORT_TRAD + "." + ColumnNameKeys.ORT_TRAD_ID + " = " + TableNameKeys.ORT_INFO
				+ "." + ColumnNameKeys.ORT_TRAD_ID + " LEFT OUTER JOIN " + TableNameKeys.ORT_NORM + " ON "
				+ TableNameKeys.ORT_NORM + "." + ColumnNameKeys.ORT_NORM_ID + " = " + TableNameKeys.ORT_TRAD + "."
				+ ColumnNameKeys.ORT_NORM_ID + " LEFT OUTER JOIN " + TableNameKeys.ORT_ABWEICHUNG_NORM + " ON "
				+ TableNameKeys.ORT_ABWEICHUNG_NORM + "." + ColumnNameKeys.ORT_ABWEICHUNG_NORM_ID + " = "
				+ TableNameKeys.ORT_NORM + "." + ColumnNameKeys.ORT_ABWEICHUNG_NORM_ID;
		String seminar = TableNameKeys.SEMINAR_INFO + " ON " + TableNameKeys.PERSON + "." + ColumnNameKeys.PERSON_ID
				+ " = " + TableNameKeys.SEMINAR_INFO + "." + ColumnNameKeys.PERSON_ID + " LEFT OUTER JOIN "
				+ TableNameKeys.SEMINAR_TRAD + " ON " + TableNameKeys.SEMINAR_TRAD + "."
				+ ColumnNameKeys.SEMINAR_TRAD_ID + " = " + TableNameKeys.SEMINAR_INFO + "."
				+ ColumnNameKeys.SEMINAR_TRAD_ID + " LEFT OUTER JOIN " + TableNameKeys.SEMINAR_NORM + " ON "
				+ TableNameKeys.SEMINAR_NORM + "." + ColumnNameKeys.SEMINAR_NORM_ID + " = " + TableNameKeys.SEMINAR_TRAD
				+ "." + ColumnNameKeys.SEMINAR_NORM_ID;
		String wirtschaftslage = TableNameKeys.WIRTSCHAFTSLAGE_INFO + " ON " + TableNameKeys.PERSON + "."
				+ ColumnNameKeys.PERSON_ID + " = " + TableNameKeys.WIRTSCHAFTSLAGE_INFO + "." + ColumnNameKeys.PERSON_ID
				+ " LEFT OUTER JOIN " + TableNameKeys.WIRTSCHAFTSLAGE_TRAD + " ON " + TableNameKeys.WIRTSCHAFTSLAGE_TRAD
				+ "." + ColumnNameKeys.WIRTSCHAFTSLAGE_TRAD_ID + " = " + TableNameKeys.WIRTSCHAFTSLAGE_INFO + "."
				+ ColumnNameKeys.WIRTSCHAFTSLAGE_TRAD_ID + " LEFT OUTER JOIN " + TableNameKeys.WIRTSCHAFTSLAGE_NORM
				+ " ON " + TableNameKeys.WIRTSCHAFTSLAGE_NORM + "." + ColumnNameKeys.WIRTSCHAFTSLAGE_NORM_ID + " = "
				+ TableNameKeys.WIRTSCHAFTSLAGE_TRAD + "." + ColumnNameKeys.WIRTSCHAFTSLAGE_NORM_ID;
		String zusaetze = TableNameKeys.ZUSAETZE_INFO + " ON " + TableNameKeys.PERSON + "." + ColumnNameKeys.PERSON_ID
				+ " = " + TableNameKeys.ZUSAETZE_INFO + "." + ColumnNameKeys.PERSON_ID + " LEFT OUTER JOIN "
				+ TableNameKeys.ZUSAETZE + " ON " + TableNameKeys.ZUSAETZE_INFO + "." + ColumnNameKeys.ZUSAETZE_ID
				+ " = " + TableNameKeys.ZUSAETZE + "." + ColumnNameKeys.ZUSAETZE_ID;
		String fach = TableNameKeys.FACH_INFO + " ON " + TableNameKeys.PERSON + "." + ColumnNameKeys.PERSON_ID + " = "
				+ TableNameKeys.FACH_INFO + "." + ColumnNameKeys.PERSON_ID + " LEFT OUTER JOIN "
				+ TableNameKeys.FACH_TRAD + " ON " + TableNameKeys.FACH_TRAD + "." + ColumnNameKeys.FACH_TRAD_ID + " = "
				+ TableNameKeys.FACH_INFO + "." + ColumnNameKeys.FACH_TRAD_ID + " LEFT OUTER JOIN "
				+ TableNameKeys.FACH_NORM + " ON " + TableNameKeys.FACH_NORM + "." + ColumnNameKeys.FACH_NORM_ID + " = "
				+ TableNameKeys.FACH_TRAD + "." + ColumnNameKeys.FACH_NORM_ID;
		String anrede = TableNameKeys.ANREDE_TRAD + " ON " + TableNameKeys.PERSON + "." + ColumnNameKeys.ANREDE_TRAD_ID
				+ " = " + TableNameKeys.ANREDE_TRAD + "." + ColumnNameKeys.ANREDE_TRAD_ID + " LEFT OUTER JOIN "
				+ TableNameKeys.ANREDE_NORM + " ON " + TableNameKeys.ANREDE_NORM + "." + ColumnNameKeys.ANREDE_NORM_ID
				+ " = " + TableNameKeys.ANREDE_TRAD + "." + ColumnNameKeys.ANREDE_NORM_ID;
		String titel = TableNameKeys.TITEL_TRAD + " ON " + TableNameKeys.PERSON + "." + ColumnNameKeys.TITEL_TRAD_ID
				+ " = " + TableNameKeys.TITEL_TRAD + "." + ColumnNameKeys.TITEL_TRAD_ID + " LEFT OUTER JOIN "
				+ TableNameKeys.TITEL_NORM + " ON " + TableNameKeys.TITEL_NORM + "." + ColumnNameKeys.TITEL_NORM_ID
				+ " = " + TableNameKeys.TITEL_TRAD + "." + ColumnNameKeys.TITEL_NORM_ID;
		String fakultaeten = TableNameKeys.FAKULTAETEN + " ON " + TableNameKeys.PERSON + "."
				+ ColumnNameKeys.FAKULTAETEN_ID + " = " + TableNameKeys.FAKULTAETEN + "."
				+ ColumnNameKeys.FAKULTAETEN_ID;
		String fundorte = TableNameKeys.FUNDORTE + " ON " + TableNameKeys.PERSON + "." + ColumnNameKeys.FUNDORTE_ID
				+ " = " + TableNameKeys.FUNDORTE + "." + ColumnNameKeys.FUNDORTE_ID;
		return vorname + " LEFT OUTER JOIN " + name + " LEFT OUTER JOIN " + ort + " LEFT OUTER JOIN " + seminar
				+ " LEFT OUTER JOIN " + wirtschaftslage + " LEFT OUTER JOIN " + zusaetze + " LEFT OUTER JOIN " + fach
				+ " LEFT OUTER JOIN " + anrede + " LEFT OUTER JOIN " + titel + " LEFT OUTER JOIN " + fakultaeten
				+ " LEFT OUTER JOIN " + fundorte + ", " + TableNameKeys.QUELLEN;
	}

	/*
	 * Builds the WHERE part of a SQL-statement, depending on what operation is
	 * wanted.
	 */
	private String buildWhere(UserQuery query) {
		if (whereIsEmpty) {
			whereIsEmpty = false;
			return query.getWhere();
		} else if (query.isOrCondition()) {
			return " OR " + query.getWhere();
		} else {
			return " AND " + query.getWhere();
		}
	}

	/*
	 * Resets the Booleans needed during search for the next query.
	 */
	private void resetBooleans() {
		needsStandardFields = true;
		hasDate = false;
		hasStudyYear = false;
	}

	/*
	 * Checks is a column belongs to the fields which always are set in SELECT
	 */
	private Boolean isStandardField(String column) {
		if (ColumnNameKeys.PERSON_ID.equals(column) || ColumnNameKeys.VORNAME_NORM.equals(column)
				|| ColumnNameKeys.NAME_NORM.equals(column) || ColumnNameKeys.ORT_NORM.equals(column)
				|| ColumnNameKeys.FAKULTAETEN_NORM.equals(column)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Helper method for a string representation of all standard fields
	 */
	private String addStandardFieldsASColumnName() {
		String result;
		result = TableNameKeys.PERSON + "." + ColumnNameKeys.PERSON_ID + " AS " + ColumnNameKeys.PERSON_ID + ", "
				+ TableNameKeys.VORNAME_NORM + "." + ColumnNameKeys.VORNAME_NORM + " AS " + ColumnNameKeys.VORNAME_NORM
				+ ", " + TableNameKeys.NAME_NORM + "." + ColumnNameKeys.NAME_NORM + " AS " + ColumnNameKeys.NAME_NORM
				+ ", " + TableNameKeys.ORT_NORM + "." + ColumnNameKeys.ORT_NORM + " AS " + ColumnNameKeys.ORT_NORM
				+ ", " + TableNameKeys.FAKULTAETEN + "." + ColumnNameKeys.FAKULTAETEN_NORM + " AS "
				+ ColumnNameKeys.FAKULTAETEN_NORM;
		return result;
	}

}