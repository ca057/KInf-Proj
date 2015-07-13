package de.uniba.kinf.projm.hylleblomst.keys;

/**
 * The name of all columns of the database. Mostly, they are self-explanatory.
 * If not, there is a separate explanation to find.
 * 
 * @author Simon
 *
 */
public interface ColumnNameKeys {

	public static final String ANREDE_NORM_ID = "AnredeNormID";
	public static final String ANREDE_NORM = "AnredeNorm";
	public static final String ANREDE_TRAD_ID = "AnredeTradID";
	public static final String ANREDE_TRAD = "AnredeTrad";
	public static final String FACH_NORM_ID = "FachNormID";
	public static final String FACH_NORM = "FachNorm";
	public static final String FACH_TRAD_ID = "FachTradID";
	public static final String FACH_TRAD = "FachTrad";
	public static final String FAKULTAETEN_ID = "FakultaetenID";
	public static final String FAKULTAETEN_NORM = "FakultaetenNorm";
	public static final String FUNDORTE_ID = "FundorteNormID";
	public static final String FUNDORTE_NORM = "FundorteNorm";
	public static final String NAME_NORM_ID = "NameNormID";
	public static final String NAME_NORM = "NameNorm";
	public static final String NAME_TRAD_ID = "NameTradID";
	public static final String NAME_TRAD = "NameTrad";
	public static final String ORT_ABWEICHUNG_NORM_ID = "OrtAbweichungNormID";
	public static final String ORT_ABWEICHUNG_NORM = "OrtAbweichungNorm";
	public static final String ORT_ABWEICHUNG_NORM_ANMERKUNG = "Anweichung";
	public static final String ORT_NORM_ID = "OrtNormID";
	public static final String ORT_NORM = "OrtNorm";
	public static final String ORT_TRAD_ID = "OrtTradID";
	public static final String ORT_TRAD = "OrtTrad";
	/**
	 * PersonID equals "Nummer" in GUI and the underlying csv.
	 */
	public static final String PERSON_ID = "PersonID";
	public static final String QUELLEN_ID = "QuellenID";
	public static final String QUELLEN_NAME = "QuellenName";
	public static final String SEMINAR_NORM_ID = "SeminarNormID";
	public static final String SEMINAR_NORM = "SeminarNorm";
	public static final String SEMINAR_TRAD_ID = "SeminarTradID";
	public static final String SEMINAR_TRAD = "SeminarTrad";
	public static final String TITEL_NORM_ID = "TitelNormID";
	public static final String TITEL_NORM = "TitelNorm";
	public static final String TITEL_TRAD_ID = "TitelTradID";
	public static final String TITEL_TRAD = "TitelTrad";
	public static final String VORNAME_NORM_ID = "VornameNormID";
	public static final String VORNAME_NORM = "VornameNorm";
	public static final String VORNAME_TRAD_ID = "VornameTradID";
	public static final String VORNAME_TRAD = "VornameTrad";
	public static final String WIRTSCHAFTSLAGE_NORM_ID = "WirtschaftslageNormID";
	public static final String WIRTSCHAFTSLAGE_NORM = "WirtschaftslageNorm";
	public static final String WIRTSCHAFTSLAGE_TRAD_ID = "WirtschaftslageTradID";
	public static final String WIRTSCHAFTSLAGE_TRAD = "WirtschaftslageTrad";
	public static final String ZUSAETZE_ID = "ZusaetzeID";
	public static final String ZUSAETZE = "Zusaetze";

	public static final String SEITE_ORIGINAL = "SeiteOriginal";
	public static final String NUMMER_HESS = "NummerHess";
	public static final String JESUIT = "Jesuit";
	public static final String ADLIG = "Adlig";
	public static final String DATUM = "Datum";
	/**
	 * This column shows which information of a date (year/month/day) was saved
	 * in {@link #STUDIENJAHR}. For example, "000" means that everything was
	 * set, while "011" means that only the year was set and month and year are
	 * dummy data and must not be shown to the user.
	 */
	public static final String DATUMS_FELDER_GESETZT = "DatumsFelderGesetzt";
	public static final String STUDIENJAHR = "Studienjahr";

	/**
	 * This is a column with the first year of {@link #STUDIENJAHR}. Because it
	 * is saved there as "yyyy/yy", this column is used for queries, while
	 * {@link #STUDIENJAHR} is shown to the user
	 */
	public static final String STUDIENJAHR_INT = "StudienjahrInt";
	public static final String GRADUIERT = "Graduiert";
	public static final String ANMERKUNG = "Anmerkung";

	public static String[][] getNormTableColumnNames() {
		String[][] result = { { ANREDE_NORM_ID, ANREDE_NORM }, { FACH_NORM_ID, FACH_NORM },
				{ FAKULTAETEN_ID, FAKULTAETEN_NORM }, { FUNDORTE_ID, FUNDORTE_NORM }, { VORNAME_NORM_ID, VORNAME_NORM },
				{ NAME_NORM_ID, NAME_NORM }, { QUELLEN_ID, QUELLEN_NAME }, { SEMINAR_NORM_ID, SEMINAR_NORM },
				{ TITEL_NORM_ID, TITEL_NORM }, { WIRTSCHAFTSLAGE_NORM_ID, WIRTSCHAFTSLAGE_NORM } };

		return result;
	}
}
