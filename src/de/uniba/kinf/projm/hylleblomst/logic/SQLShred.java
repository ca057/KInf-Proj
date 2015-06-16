package de.uniba.kinf.projm.hylleblomst.logic;

public class SQLShred {

	String getPersonJoin(String table, String column, int source) {
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
		} else {
			table = "Hylleblomst." + table;
			// FIXME ON passt nicht.
			return table + " INNER JOIN " + "Hylleblomst.PERSON ON " + table
					+ "." + column + " = " + "Hylleblomst.PERSON.";
		}
		return null;
	}

	String getDate(int[] input) {
		return null;
	}
}
