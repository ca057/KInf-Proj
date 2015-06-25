package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class QueriesImpl implements Queries {
	DBAccess db;
	ArrayList<Object> inputs = new ArrayList<Object>();

	@Override
	public void search(Collection<QueryRequestImpl> queryRequests)
			throws SQLException {
		startQuery(buildQuery(queryRequests));
	}

	private String buildQuery(Collection<QueryRequestImpl> queryRequests)
			throws SQLException {
		inputs.clear();
		String sqlQuery = "SELECT " + getSelect();
		StringBuilder sqlFrom = new StringBuilder();
		sqlFrom.append(getFrom());
		StringBuilder sqlWhere = new StringBuilder();
		for (QueryRequestImpl qr : queryRequests) {
			if (qr.getFrom().isPresent()) {
				sqlFrom.append(qr.getFrom().get());
			}
			if (sqlWhere.length() == 0) {
				sqlWhere.append(qr.getWhere());
			} else {
				sqlWhere.append(" AND " + qr.getWhere());
			}
			if (!(qr.getInput() instanceof Boolean)) {
				inputs.add(qr.getInput());
			}
		}
		sqlQuery += " FROM " + sqlFrom + " WHERE " + sqlWhere;
		System.out.println(sqlQuery);
		return sqlQuery;
	}

	private void startQuery(String sqlQuery) throws SQLException {
		db = new DBAccess("jdbc:derby:./db/MyDB;create=true", "admin",
				"password");
		db.startQuery(sqlQuery, inputs);
	}

	@Override
	public void searchPerson(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {

	}

	private String getFrom() {

		String vorname = "Hylleblomst.person LEFT OUTER JOIN Hylleblomst.vorname_info ON Hylleblomst.person.personID = Hylleblomst.vorname_info.personID LEFT OUTER JOIN Hylleblomst.vorname_Trad ON Hylleblomst.vorname_Trad.vornameTradID = Hylleblomst.vorname_Info.vornameTradID LEFT OUTER JOIN Hylleblomst.vorname_Norm ON Hylleblomst.vorname_Norm.vornameNormID = Hylleblomst.vorname_Trad.vornameNormID";
		String name = "Hylleblomst.name_Info ON Hylleblomst.person.personID = Hylleblomst.name_info.personID LEFT OUTER JOIN Hylleblomst.name_Trad ON Hylleblomst.name_Trad.nameTradID = Hylleblomst.name_Info.nameTradID LEFT OUTER JOIN Hylleblomst.name_Norm ON Hylleblomst.name_Norm.nameNormID = Hylleblomst.name_Trad.nameNormID";
		String ort = "Hylleblomst.ort_info ON Hylleblomst.person.personID = Hylleblomst.ort_info.personID LEFT OUTER JOIN Hylleblomst.ort_Trad ON Hylleblomst.ort_Trad.ortTradID = Hylleblomst.ort_Info.ortTradID LEFT OUTER JOIN Hylleblomst.ort_norm ON Hylleblomst.ort_Norm.ortNormID = Hylleblomst.ort_Trad.ortNormID LEFT OUTER JOIN Hylleblomst.ort_abweichung_Norm ON Hylleblomst.Ort_abweichung_norm.OrtAbweichungNormID = Hylleblomst.Ort_Norm.OrtAbweichungNormID";
		String seminar = "Hylleblomst.seminar_info ON Hylleblomst.person.personID = Hylleblomst.seminar_info.personID LEFT OUTER JOIN Hylleblomst.seminar_trad ON Hylleblomst.seminar_Trad.seminarTradID = Hylleblomst.seminar_Info.seminarID LEFT OUTER JOIN Hylleblomst.seminar_norm ON Hylleblomst.seminar_Norm.seminarNormID = Hylleblomst.seminar_Trad.seminarNormID";
		String wirtschaftslage = "Hylleblomst.wirtschaftslage_info ON Hylleblomst.person.personID = Hylleblomst.wirtschaftslage_info.personID LEFT OUTER JOIN Hylleblomst.wirtschaftslage_trad ON Hylleblomst.wirtschaftslage_Trad.wirtschaftslageTradID = Hylleblomst.wirtschaftslage_Info.wirtschaftslageTradID LEFT OUTER JOIN Hylleblomst.wirtschaftslage_Norm ON Hylleblomst.wirtschaftslage_Norm.wirtschaftslageNormID = Hylleblomst.wirtschaftslage_Trad.wirtschaftslageNormID ";
		String zusaetze = "Hylleblomst.zusaetze_info ON Hylleblomst.person.personID = Hylleblomst.zusaetze_info.personID LEFT OUTER JOIN Hylleblomst.zusaetze ON Hylleblomst.zusaetze_info.zusaetzeID = Hylleblomst.zusaetze.zusaetzeID";
		String fach = "Hylleblomst.fach_info ON Hylleblomst.person.personID = Hylleblomst.fach_info.personID LEFT OUTER JOIN Hylleblomst.fach_trad ON Hylleblomst.fach_trad.FachTradID = Hylleblomst.fach_info.fachTradID LEFT OUTER JOIN Hylleblomst.fach_norm ON Hylleblomst.fach_norm.fachNormID = Hylleblomst.fach_trad.fachNormID";
		String anrede = "Hylleblomst.anrede_Trad ON Hylleblomst.person.anredeID = Hylleblomst.anrede_trad.anredeTradID LEFT OUTER JOIN Hylleblomst.anrede_Norm ON Hylleblomst.anrede_Norm.anredeNormID = Hylleblomst.anrede_Trad.anredeNormID";
		String titel = "Hylleblomst.titel_trad ON Hylleblomst.person.titelID = Hylleblomst.titel_trad.titelTradID LEFT OUTER JOIN Hylleblomst.titel_Norm ON Hylleblomst.titel_Norm.titelNormID = Hylleblomst.titel_Trad.titelNormID";
		String fakultaeten = "Hylleblomst.Fakultaeten ON Hylleblomst.person.fakultaetenID = Hylleblomst.fakultaeten.fakultaetenID";
		String fundorte = "Hylleblomst.Fundorte ON Hylleblomst.person.fundortID = Hylleblomst.fundorte.fundorteID";
		return vorname + " LEFT OUTER JOIN " + name + " LEFT OUTER JOIN " + ort
				+ " LEFT OUTER JOIN " + seminar + " LEFT OUTER JOIN "
				+ wirtschaftslage + " LEFT OUTER JOIN " + zusaetze
				+ " LEFT OUTER JOIN " + fach + " LEFT OUTER JOIN " + anrede
				+ " LEFT OUTER JOIN " + titel + " LEFT OUTER JOIN "
				+ fakultaeten + " LEFT OUTER JOIN " + fundorte;
	}

	private String getSelect() {
		// TODO Ort-Abweichung-Norm: Hier muss dann auch die Anmerkung mit
		// zurückgegeben
		// werden.
		return "DISTINCT Hylleblomst.vorname_norm.name AS vorname_norm, Hylleblomst.name_norm.name AS nachname_norm, Hylleblomst.ort_norm.ortNorm AS ort_norm, Hylleblomst.fakultaeten.name AS fakultaet";
	}
}
