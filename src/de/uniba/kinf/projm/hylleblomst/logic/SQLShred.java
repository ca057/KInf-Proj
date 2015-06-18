package de.uniba.kinf.projm.hylleblomst.logic;

public class SQLShred {

	String getPersonJoin(String table, String column, int source) {

		String tableName;
		if (source == SourceKeys.NORM) {
			if (table.startsWith("ANREDE")) {
				String table2 = "Hylleblomst."
						+ table.substring(0, table.indexOf("_"));
				String table3 = table2 + "_TRAD";
				return "Hylleblomst." + table + " INNER JOIN " + table3
						+ " INNER JOIN Hylleblomst.PERSON";

			}
			if (table.endsWith("NORM")) {

				String table2 = "Hylleblomst."
						+ table.substring(0, table.indexOf("_"));
				String table3 = table2 + "_TRAD";
				String table4 = table2 + "_INFO";
				return "Hylleblomst." + table + " INNER JOIN " + table3
						+ " INNER JOIN " + table4
						+ " INNER JOIN Hylleblomst.PERSON";
			}
		} else if (source == SourceKeys.ORT_NORM_AB) {
			// TODO implement this
			System.out
					.println("Join-Version für ORT_NORM_AB noch nicht implementiert.");
			return null;
		}
		table = "Hylleblomst." + table;
		// FIXME ON passt nicht.
		return "Hylleblomst.vorname_norm LEFT OUTER JOIN Hylleblomst.vorname_trad ON Hylleblomst.vorname_norm.VornameNormID = Hylleblomst.vorname_trad.VornameNormID Left OUTER JOIN Hylleblomst.vorname_info ON Hylleblomst.vorname_trad.VornameTradID = Hylleblomst.vorname_info.VornameTradID Left OUTER JOIN Hylleblomst.quellen ON Hylleblomst.vorname_info.QuellenID = Hylleblomst.quellen.QuellenID Left OUTER JOIN Hylleblomst.person ON Hylleblomst.vorname_trad.PersonID = Hylleblomst.person.PersonID";

	}

	String getDate(int[] input) {
		return null;
	}
}
