package de.uniba.kinf.projm.hylleblomst.logic;

public class SQLShred {

	public String getPersonJoin(String table) {
		if (table.endsWith("ANREDE_NORM")) {
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
		return "";
	}
}
