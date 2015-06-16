package de.uniba.kinf.projm.hylleblomst.logic;

public class SQLShred {

	String getPersonJoin(String table, int source) {
		if (source == SourceKeys.NORM) {
			if (table.startsWith("ANREDE")) {
				String table2 = "Hylleblomst."
						+ table.substring(0, table.indexOf("_"));
				String table3 = table2 + "_TRAD";
				return "Hylleblomst." + table + " CROSS JOIN " + table3
						+ " CROSS JOIN Hylleblomst.PERSON";

			}
			if (table.endsWith("NORM")) {
				String table2 = "Hylleblomst."
						+ table.substring(0, table.indexOf("_"));
				String table3 = table2 + "_TRAD";
				String table4 = table2 + "_INFO";
				return "Hylleblomst." + table + " CROSS JOIN " + table3
						+ " CROSS JOIN " + table4
						+ " CROSS JOIN Hylleblomst.PERSON";
			}
		} else if (source == SourceKeys.ORT_NORM_AB) {
			// TODO implement this
			System.out
					.println("Join-Version für ORT_NORM_AB noch nicht implementiert.");
			return null;
		} else {
			table = "Hylleblomst." + table;
			return table + " CROSS JOIN " + "Hylleblomst.PERSON";
		}
		return null;
	}

	String getDate(int[] input) {
		return null;
	}
}
