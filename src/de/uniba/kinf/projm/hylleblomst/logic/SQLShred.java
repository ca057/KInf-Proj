package de.uniba.kinf.projm.hylleblomst.logic;

public class SQLShred {

	String getPersonJoin(String table, String column, int source) {

		// String tableName = ;
		// if(source == SourceKeys.STANDARD) {
		// return "Hylleblomst." vorname_norm, Hylleblomst.vorname_trad,
		// Hylleblomst.vorname_info, Hylleblomst.quellen, Hylleblomst.person
		// WHERE Hylleblomst.vorname_trad.VornameTradID =
		// Hylleblomst.vorname_info.VornameTradID AND
		// Hylleblomst.vorname_norm.VornameNormID =
		// Hylleblomst.vorname_trad.VornameNormID AND
		// Hylleblomst.vorname_info.QuellenID = Hylleblomst.quellen.QuellenID
		// AND Hylleblomst.vorname_info.PersonID = Hylleblomst.person.PersonID";
		// }
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
		return "Hylleblomst.vorname_norm, Hylleblomst.vorname_trad, Hylleblomst.vorname_info, Hylleblomst.quellen, Hylleblomst.person WHERE Hylleblomst.vorname_trad.VornameTradID = Hylleblomst.vorname_info.VornameTradID AND Hylleblomst.vorname_norm.VornameNormID = Hylleblomst.vorname_trad.VornameNormID AND Hylleblomst.vorname_info.QuellenID = Hylleblomst.quellen.QuellenID AND Hylleblomst.vorname_info.PersonID = Hylleblomst.person.PersonID";

	}

	String getDate(int[] input) {
		return null;
	}
}
