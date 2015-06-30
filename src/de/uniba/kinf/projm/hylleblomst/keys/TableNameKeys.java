package de.uniba.kinf.projm.hylleblomst.keys;

public class TableNameKeys {
	public static final String schemaName = "hylleblomst";

	public static final String anredeNorm = schemaName + ".anrede_norm";
	public static final String anredeTrad = schemaName + ".anrede_trad";
	public static final String quellen = schemaName + ".quellen";
	public static final String fundorte = schemaName + ".fundorte";
	public static final String fakultaeten = schemaName + ".fakultaeten";
	public static final String titelNorm = schemaName + ".tiel_norm";
	public static final String titelTrad = schemaName + ".titel_trad";
	public static final String person = schemaName + ".person";
	public static final String fachNorm = schemaName + ".fach_norm";
	public static final String fachTrad = schemaName + ".fach_trad";
	public static final String fachInfo = schemaName + ".fach_info";
	public static final String nameNorm = schemaName + ".name_norm";
	public static final String nameTrad = schemaName + ".name_trad";
	public static final String nameInfo = schemaName + ".name_info";
	public static final String vornameNorm = schemaName + ".vorname_norm";
	public static final String vornameTrad = schemaName + ".vorname_trad";
	public static final String vornameInfo = schemaName + ".vorname_info";
	public static final String ortAbweichungNorm = schemaName
			+ ".ort_abweichung_norm";
	public static final String ortNorm = schemaName + ".ort_norm";
	public static final String ortTrad = schemaName + ".ort_trad";
	public static final String ortInfo = schemaName + ".ort_info";
	public static final String seminarNorm = schemaName + ".seminar_norm";
	public static final String seminarTrad = schemaName + ".seminar_trad";
	public static final String seminarInfo = schemaName + ".seminar_info";
	public static final String wirtschaftslageNorm = schemaName
			+ ".wirtschaftslage_norm";
	public static final String wirtschaftslageTrad = schemaName
			+ ".wirtschaftslage_trad";
	public static final String wirtschaftslageInfo = schemaName
			+ ".wirtschaftslage_info";
	public static final String zusaetze = schemaName + ".zusaetze";
	public static final String zusaetzeInfo = schemaName + ".zusaetze_info";

	public static String[] getAllTableNames() {
		String[] allTables = { anredeNorm, anredeTrad, quellen, fundorte,
				fakultaeten, titelNorm, titelTrad, person, fachNorm, fachTrad,
				fachInfo, nameNorm, nameTrad, nameInfo, vornameNorm,
				vornameTrad, vornameInfo, ortAbweichungNorm, ortNorm, ortTrad,
				ortInfo, seminarNorm, seminarTrad, seminarInfo,
				wirtschaftslageNorm, wirtschaftslageTrad, wirtschaftslageInfo,
				zusaetze, zusaetzeInfo };
		return allTables;
	}
}
