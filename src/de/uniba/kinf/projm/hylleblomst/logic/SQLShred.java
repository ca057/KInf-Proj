package de.uniba.kinf.projm.hylleblomst.logic;

public class SQLShred {

	String getPersonJoin(String table, String column, int source) {

		String tableName = table.substring(0, table.indexOf("_"));
		if (table.startsWith("Ort")) {
			return "Hylleblomst."
					+ tableName
					+ "_abweichung_norm, Hylleblomst."
					+ tableName
					+ "_norm, Hylleblomst."
					+ tableName
					+ "_trad, Hylleblomst."
					+ tableName
					+ "_info, Hylleblomst.quellen, Hylleblomst.person "
					+ "WHERE Hylleblomst."
					+ tableName
					+ "_trad."
					+ tableName
					+ "TradID = Hylleblomst."
					+ tableName
					+ "_info."
					+ tableName
					+ "TradID AND Hylleblomst."
					+ tableName
					+ "_norm."
					+ tableName
					+ "NormID = Hylleblomst."
					+ tableName
					+ "_trad."
					+ tableName
					+ "NormID AND Hylleblomst."
					+ tableName
					+ "_abweichung_norm."
					+ tableName
					+ "AbweichungNormID = Hylleblomst."
					+ tableName
					+ "_norm."
					+ tableName
					+ "NormID AND Hylleblomst."
					+ tableName
					+ "_info.QuellenID = Hylleblomst.quellen.QuellenID AND Hylleblomst."
					+ tableName
					+ "_info.PersonID = Hylleblomst.person.PersonID AND Hylleblomst."
					+ tableName + "_info.QuellenID = " + source;
		}
		if (table.startsWith("ANREDE")) {
			return "Hylleblomst." + tableName + "_norm, Hylleblomst."
					+ tableName + "_trad, Hylleblomst.Person "
					+ "WHERE Hylleblomst." + tableName + "_norm." + tableName
					+ "NormID = Hylleblomst." + tableName + "_trad."
					+ tableName + "normID AND Hylleblomst." + tableName
					+ "_trad." + tableName + "tradID = Hylleblomst.Person."
					+ tableName + "ID";
		}
		return "Hylleblomst."
				+ tableName
				+ "_norm, Hylleblomst."
				+ tableName
				+ "_trad, Hylleblomst."
				+ tableName
				+ "_info, Hylleblomst.quellen, Hylleblomst.person WHERE Hylleblomst."
				+ tableName
				+ "_trad."
				+ tableName
				+ "TradID = Hylleblomst."
				+ tableName
				+ "_info."
				+ tableName
				+ "TradID AND Hylleblomst."
				+ tableName
				+ "_norm."
				+ tableName
				+ "NormID = Hylleblomst."
				+ tableName
				+ "_trad."
				+ tableName
				+ "NormID AND Hylleblomst."
				+ tableName
				+ "_info.QuellenID = Hylleblomst.quellen.QuellenID AND Hylleblomst."
				+ tableName
				+ "_info.PersonID = Hylleblomst.person.PersonID AND Hylleblomst."
				+ tableName + "_info.QuellenID = " + source;
	}

	String getDate(int[] input) {
		return null;
	}
}
