package de.uniba.kinf.projm.hylleblomst.logic;

public class MainLogic {
	public static void main(String[] args) {
		QueriesImpl test = new QueriesImpl();
		// ArrayList<QueryRequest> qr = new ArrayList<>();
		// test.setDatabase("jdbc:derby:db/MyDB", "admin", "password");

		System.out.println(test.getColumnName("hylleblomst.fach_trad"));
		System.out.println(test.getColumnName("hylleblomst.fach_info"));
		System.out.println(test.getColumnName("hylleblomst.vorname_info"));
		System.out.println(test.getColumnName("hylleblomst.name_trad"));
		System.out.println(test.getColumnName("hylleblomst.name_info"));
		System.out.println(test.getColumnName("hylleblomst.ort_norm"));
		System.out.println(test.getColumnName("hylleblomst.ort_trad"));
		System.out.println(test.getColumnName("hylleblomst.ort_info"));
		System.out.println(test.getColumnName("hylleblomst.seminar_trad"));
		System.out.println(test.getColumnName("hylleblomst.seminar_info"));
		System.out.println(test
				.getColumnName("hylleblomst.wirtschaftslage_trad"));
		System.out.println(test
				.getColumnName("hylleblomst.wirtschaftslage_info"));
		System.out.println(test.getColumnName("hylleblomst.zusaetze"));
		System.out.println(test.getColumnName("hylleblomst.zusaetze_info"));
		System.out.println(test.getColumnName("hylleblomst.anrede_norm"));
		System.out.println(test.getColumnName("hylleblomst.fach_norm"));
		System.out.println(test.getColumnName("hylleblomst.fakultaeten"));
		System.out.println(test.getColumnName("hylleblomst.fundorte"));
		System.out.println(test.getColumnName("hylleblomst.name_norm"));
		System.out.println(test
				.getColumnName("hylleblomst.ort_abweichung_norm"));
		System.out.println(test.getColumnName("hylleblomst.quellen"));
		System.out.println(test.getColumnName("hylleblomst.seminar_norm"));
		System.out.println(test.getColumnName("hylleblomst.titel_norm"));
		System.out.println(test
				.getColumnName("hylleblomst.wirtschaftslage_norm"));
		System.out.println(test.getColumnName("hylleblomst.anrede_trad"));
		System.out.println(test.getColumnName("hylleblomst.titel_trad"));
		System.out.println(test.getColumnName("hylleblomst.person"));

		System.out.println(test.set.toString());

	}
}
