package de.uniba.kinf.projm.hylleblomst.keys;

public class TableNameKeys {
	public static final String SCHEMA_NAME = "hylleblomst";

	public static final String ANREDE_NORM = SCHEMA_NAME + ".anrede_norm";
	public static final String ANREDE_TRAD = SCHEMA_NAME + ".anrede_trad";
	public static final String QUELLEN = SCHEMA_NAME + ".quellen";
	public static final String FUNDORTE = SCHEMA_NAME + ".fundorte";
	public static final String FAKULTAETEN = SCHEMA_NAME + ".fakultaeten";
	public static final String TITEL_NORM = SCHEMA_NAME + ".titel_norm";
	public static final String TITEL_TRAD = SCHEMA_NAME + ".titel_trad";
	public static final String PERSON = SCHEMA_NAME + ".person";
	public static final String FACH_NORM = SCHEMA_NAME + ".fach_norm";
	public static final String FACH_TRAD = SCHEMA_NAME + ".fach_trad";
	public static final String FACH_INFO = SCHEMA_NAME + ".fach_info";
	public static final String NAME_NORM = SCHEMA_NAME + ".name_norm";
	public static final String NAME_TRAD = SCHEMA_NAME + ".name_trad";
	public static final String NAME_INFO = SCHEMA_NAME + ".name_info";
	public static final String VORNAME_NORM = SCHEMA_NAME + ".vorname_norm";
	public static final String VORNAME_TRAD = SCHEMA_NAME + ".vorname_trad";
	public static final String VORNAME_INFO = SCHEMA_NAME + ".vorname_info";
	public static final String ORT_ABWEICHUNG_NORM = SCHEMA_NAME
			+ ".ort_abweichung_norm";
	public static final String ORT_NORM = SCHEMA_NAME + ".ort_norm";
	public static final String ORT_TRAD = SCHEMA_NAME + ".ort_trad";
	public static final String ORT_INFO = SCHEMA_NAME + ".ort_info";
	public static final String SEMINAR_NORM = SCHEMA_NAME + ".seminar_norm";
	public static final String SEMINAR_TRAD = SCHEMA_NAME + ".seminar_trad";
	public static final String SEMINAR_INFO = SCHEMA_NAME + ".seminar_info";
	public static final String WIRTSCHAFTSLAGE_NORM = SCHEMA_NAME
			+ ".wirtschaftslage_norm";
	public static final String WIRTSCHAFTSLAGE_TRAD = SCHEMA_NAME
			+ ".wirtschaftslage_trad";
	public static final String WIRTSCHAFTSLAGE_INFO = SCHEMA_NAME
			+ ".wirtschaftslage_info";
	public static final String ZUSAETZE = SCHEMA_NAME + ".zusaetze";
	public static final String ZUSAETZE_INFO = SCHEMA_NAME + ".zusaetze_info";

	public static String[] getNormTableNames() {
		String[] normTables = { ANREDE_NORM, FACH_NORM, FAKULTAETEN, FUNDORTE,
				VORNAME_NORM, NAME_NORM, QUELLEN, SEMINAR_NORM, TITEL_NORM,
				WIRTSCHAFTSLAGE_NORM };
		return normTables;
	}

	public static String[] getAllTableNames() {
		String[] allTables = { ANREDE_NORM, ANREDE_TRAD, QUELLEN, FUNDORTE,
				FAKULTAETEN, TITEL_NORM, TITEL_TRAD, PERSON, FACH_NORM,
				FACH_TRAD, FACH_INFO, NAME_NORM, NAME_TRAD, NAME_INFO,
				VORNAME_NORM, VORNAME_TRAD, VORNAME_INFO, ORT_ABWEICHUNG_NORM,
				ORT_NORM, ORT_TRAD, ORT_INFO, SEMINAR_NORM, SEMINAR_TRAD,
				SEMINAR_INFO, WIRTSCHAFTSLAGE_NORM, WIRTSCHAFTSLAGE_TRAD,
				WIRTSCHAFTSLAGE_INFO, ZUSAETZE, ZUSAETZE_INFO };
		return allTables;
	}
}
