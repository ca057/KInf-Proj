package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
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

	private Collection<UserQuery> userQuery;
	private ArrayList<String> inputs;
	private StringBuilder sqlStatement = new StringBuilder();
	private Boolean sqlStatementIsNotEmpty = false;
	private Boolean whereIsEmpty = true;

	/**
	 * @param userQuery
	 * @throws SQLException
	 */
	public SQLBuilder(Collection<UserQuery> userQuery) throws SQLException {
		inputs = new ArrayList<String>();
		if (userQuery == null || userQuery.isEmpty()) {
			throw new InputMismatchException("Die übergebene Collection darf nicht leer bzw. null sein.");
		}
		this.userQuery = userQuery;
		buildQuery();
	}

	/**
	 * @param personID
	 */
	public SQLBuilder(String personID) {
		inputs = new ArrayList<String>();
		if (personID != null) {
			buildPersonSearch(personID);
		} else {
			throw new InputMismatchException("Die übergebene Collection darf nicht null sein.");
		}
	}

	/**
	 * @return
	 */
	public String getSQLStatement() {
		return sqlStatement.toString();
	}

	/**
	 * @return
	 */
	public List<String> getInputs() {
		return inputs;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	void buildQuery() throws SQLException {
		// Boolean hasSource = false;
		StringBuilder sqlWhere = new StringBuilder();

		for (UserQuery qr : userQuery) {

			sqlStatement.append(buildSelect(qr));

			// if (qr.getSource() != SourceKeys.NO_SOURCE) {
			// hasSource = true;
			// }

			sqlWhere.append(buildWhere(qr));

			if (!("true".equals(qr.getInput()))) {
				inputs.add(qr.getInput());
				if (SourceKeys.NO_SELECTION.equals(qr.getSource())) {
					inputs.add(qr.getInput());
				}
			}
		}

		sqlStatement.append(buildFrom());
		// if (hasSource) {
		// sqlStatement.append(", " + TableNameKeys.QUELLEN);
		// }

		sqlStatement.append(" WHERE ").append(sqlWhere);
		System.out.println(sqlStatement);
	}

	/*
	 * Builds the WHERE part of a SQL-statement, depending on what operation is
	 * wanted.
	 */
	private String buildWhere(UserQuery qr) {
		if (whereIsEmpty) {
			whereIsEmpty = false;
			return qr.getWhere();
		} else if (qr.useOrCondition()) {
			return " OR " + qr.getWhere();
		} else {
			return " AND " + qr.getWhere();
		}
	}

	/*
	 * Builds the whole SQL-statement needed to find all available information
	 * of a person by searching with the ID of this person.
	 */
	private void buildPersonSearch(String personID) {
		StringBuilder sqlQuery = new StringBuilder();
		inputs.add(personID);
		sqlStatement = sqlQuery.append(buildSelectAll()).append(buildFrom()).append(" WHERE ")
				.append(TableNameKeys.PERSON).append("." + ColumnNameKeys.PERSON_ID + " = ?");
	}

	/*
	 * 
	 */
	private String buildSelect(UserQuery qr) {
		String result = "";
		if (!sqlStatementIsNotEmpty) {
			result = "SELECT DISTINCT " + TableNameKeys.PERSON + "." + ColumnNameKeys.PERSON_ID + " AS PersonID, "
					+ TableNameKeys.VORNAME_NORM + "." + ColumnNameKeys.VORNAME_NORM + " AS vorname_norm, "
					+ TableNameKeys.NAME_NORM + "." + ColumnNameKeys.NAME_NORM + " AS nachname_norm, "
					+ TableNameKeys.ORT_NORM + "." + ColumnNameKeys.ORT_NORM + " AS ort_norm, "
					+ TableNameKeys.FAKULTAETEN + "." + ColumnNameKeys.FAKULTAETEN_NORM + " AS fakultaet_norm";
			sqlStatementIsNotEmpty = true;
		}
		if (ColumnNameKeys.STUDIENJAHR_INT.equals(qr.getColumn())) {
			result += ", " + qr.getTable() + "." + ColumnNameKeys.STUDIENJAHR + " AS " + qr.getSearchField();
		} else if (ColumnNameKeys.DATUM.equals(qr.getColumn())) {
			result += ", " + qr.getTable() + "." + ColumnNameKeys.DATUM + ", " + qr.getTable() + "."
					+ ColumnNameKeys.DATUMS_FELDER_GESETZT;
		} else {
			result += ", " + qr.getTable() + "." + qr.getColumn() + " AS " + qr.getSearchField();
		}
		if (qr.getSource() == SourceKeys.ORT_NORM_AB) {
			result += ", " + qr.getTable() + "." + ColumnNameKeys.ANMERKUNG;
		}

		// FIXME Für Orte
		// result += ", ( SELECT " + qr.getTable() + "." + qr.getColumn() + "
		// FROM " + qr.getTable()
		// + " JOIN Hylleblomst.Ort_info ON " + qr.getTable() + "." +
		// qr.getColumn() + "="
		// + "Hylleblomst.Ort_info." + qr.getColumn()
		// + " WHERE Hylleblomst.Ort_info.PersonID =
		// Hylleblomst.person.personID";

		return result;
	}

	/*
	 * If every (usefull) column of the database is wanted, this method provides
	 * the suitable SELECT part.
	 */
	private String buildSelectAll() {
		return "SELECT DISTINCT " + TableNameKeys.PERSON + "." + ColumnNameKeys.PERSON_ID + " AS "
				+ ColumnNameKeys.PERSON_ID;
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

		return " FROM " + vorname + " LEFT OUTER JOIN " + name + " LEFT OUTER JOIN " + ort + " LEFT OUTER JOIN "
				+ seminar + " LEFT OUTER JOIN " + wirtschaftslage + " LEFT OUTER JOIN " + zusaetze + " LEFT OUTER JOIN "
				+ fach + " LEFT OUTER JOIN " + anrede + " LEFT OUTER JOIN " + titel + " LEFT OUTER JOIN " + fakultaeten
				+ " LEFT OUTER JOIN " + fundorte + ", " + TableNameKeys.QUELLEN;
	}

}