package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.UserKeys;
import de.uniba.kinf.projm.hylleblomst.logic.DBAccess;
import de.uniba.kinf.projm.hylleblomst.logic.PersonItem;
import de.uniba.kinf.projm.hylleblomst.logic.Queries;
import de.uniba.kinf.projm.hylleblomst.logic.QueryRequest;

public class QueriesImpl implements Queries {

	DBAccess db = new DBAccess(UserKeys.dbURL, UserKeys.adminUser, UserKeys.adminPassword);

	@Override
	public ArrayList<PersonItem> search(Collection<QueryRequest> queryRequests) throws SQLException {

		ResultSet test = startQuery(queryRequests);
		return new ArrayList<PersonItem>();
	}

	private ResultSet startQuery(Collection<QueryRequest> queryRequests) throws SQLException {
		Boolean hasSource = false;
		ArrayList<String> inputs = new ArrayList<String>();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT ").append(getSelect());
		StringBuilder sqlWhere = new StringBuilder();
		StringBuilder sqlFrom = new StringBuilder();
		sqlFrom.append(getFrom());
		for (QueryRequest qr : queryRequests) {
			if (qr.getSource() != SourceKeys.NO_SOURCE) {
				hasSource = true;
			}
			if (sqlWhere.length() == 0) {
				sqlWhere.append(qr.getWhere());
			} else {
				sqlWhere.append(" AND " + qr.getWhere());
			}
			if (!("true".equals(qr.getInput()))) {
				inputs.add(qr.getInput());
			}

			sqlQuery.append(", " + qr.getTable() + "." + qr.getColumn() + " AS " + qr.getSearchField());
			if (qr.getSource() == SourceKeys.ORT_NORM_AB) {
				sqlQuery.append(", " + qr.getTable() + "." + ColumnNameKeys.ANMERKUNG);
			}
		}
		if (hasSource) {
			sqlFrom.append(", " + TableNameKeys.QUELLEN);
		}
		sqlQuery.append(" FROM ").append(sqlFrom).append(" WHERE ").append(sqlWhere);
		System.out.println(sqlQuery);
		return db.startQuery(sqlQuery.toString(), inputs);
	}

	@Override
	public ResultSet searchPerson(String string) throws SQLException {
		ArrayList<String> inputs = new ArrayList<String>();
		inputs.add("" + string);
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(getSelectAll()).append(getFrom()).append(" WHERE Person.PersonID = ?");
		db = new DBAccess("jdbc:derby:./db/MyDB;create=true", "admin", "password");
		return db.startQuery(sqlQuery.toString(), inputs);
	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {

	}

	private String getFrom() {

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
				+ " LEFT OUTER JOIN " + fundorte;
	}

	private String getSelect() {
		return "DISTINCT " + TableNameKeys.PERSON + "." + ColumnNameKeys.PERSON_ID + " AS PersonID, "
				+ TableNameKeys.VORNAME_NORM + "." + ColumnNameKeys.VORNAME_NORM + " AS vorname_norm, "
				+ TableNameKeys.NAME_NORM + "." + ColumnNameKeys.NAME_NORM + " AS nachname_norm, "
				+ TableNameKeys.ORT_NORM + "." + ColumnNameKeys.ORT_NORM + " AS ort_norm, " + TableNameKeys.FAKULTAETEN
				+ "." + ColumnNameKeys.FAKULTAETEN_NORM + " AS fakultaet_norm";
	}

	private String getSelectAll() {
		return "DISTINCT Hylleblomst.Person.PersonID AS PersonID, Hylleblomst.vorname_norm.name AS vorname_norm, Hylleblomst.name_norm.name AS nachname_norm, Hylleblomst.ort_norm.ortNorm AS ort_norm, Hylleblomst.fakultaeten.name AS fakultaet_norm";
	}
}
