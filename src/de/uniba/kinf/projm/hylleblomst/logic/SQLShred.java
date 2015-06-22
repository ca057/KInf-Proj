package de.uniba.kinf.projm.hylleblomst.logic;

public class SQLShred extends QueryRequestImpl {
	private String table;
	private String tableName;
	private String column;
	private Object input;

	public SQLShred(String table, String column, Object input) {
		this.table = table;
		if (table.contains("_")) {
			this.tableName = table.substring(0, table.indexOf("_"));
		} else {
			this.tableName = table;
		}
		this.column = column;
		this.input = input;
	}

	String getJoin() {
		if (tableName.startsWith("Person")) {
			return "Hylleblomst." + tableName + " WHERE " + getInputToQuery();
		} else if (tableName.startsWith("Ort")) {
			return "Hylleblomst." + tableName
					+ "_abweichung_norm, Hylleblomst." + getStandardFromJoin()
					+ getStandardWhereJoin() + "AND Hylleblomst." + tableName
					+ "_abweichung_norm." + tableName
					+ "AbweichungNormID = Hylleblomst." + tableName + "_norm."
					+ tableName + "AbweichungNormID + AND " + getInputToQuery();
		} else if (tableName.startsWith("ANREDE")) {
			return "Hylleblomst." + tableName + "_norm, Hylleblomst."
					+ tableName + "_trad, Hylleblomst.Person"
					+ " WHERE Hylleblomst." + tableName + "_norm." + tableName
					+ "NormID = Hylleblomst." + tableName + "_trad."
					+ tableName + "normID AND Hylleblomst." + tableName
					+ "_trad." + tableName + "tradID = Hylleblomst.Person."
					+ tableName + "ID" + " AND " + getInputToQuery();
		} else if (tableName.startsWith("Fakul")
				|| tableName.startsWith("Fund")) {
			return "Hylleblomst." + tableName + ", Hylleblomst.Person"
					+ " WHERE Hylleblomst." + tableName + "." + tableName
					+ "ID = Hylleblomst.Person." + tableName + "ID AND "
					+ getInputToQuery();
		} else {
			return getStandardFromJoin() + getStandardWhereJoin()
					+ getInputToQuery();
		}
	}

	String getStandardFromJoin() {
		return "Hylleblomst." + tableName + "_norm, Hylleblomst." + tableName
				+ "_trad, Hylleblomst." + tableName
				+ "_info, Hylleblomst.quellen, Hylleblomst.person";
	}

	String getStandardWhereJoin() {
		return " WHERE Hylleblomst."
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
				+ tableName + "_info.QuellenID = " + this.getSource();
	}

	String getInputToQuery() {
		return "Hylleblomst." + table + "." + column + " LIKE " + "'%" + input
				+ "%'";
	}

	String getDate(int[] input) {
		return null;
	}

}
