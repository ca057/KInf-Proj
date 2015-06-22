package de.uniba.kinf.projm.hylleblomst.logic;

public class SQLShredTest {

	private String dbName;
	private String table;
	private String tableName;
	private String column;
	private Object input;

	public SQLShredTest(String dbName, String table, String column, Object input) {
		this.dbName = dbName;
		this.table = table;
		if (table.contains("_")) {
			this.tableName = table.substring(0, table.indexOf("_"));
		} else {
			this.tableName = table;
		}
		this.column = column;
		this.input = input;
	}

	String getFrom() {
		if (tableName.toUpperCase().startsWith("PERSON")) {
			return String.format("%s.%s", dbName, tableName);
		}
		if (tableName.toUpperCase().startsWith("FAKUL")
				|| tableName.startsWith("FUND")) {
			return String.format("%s.%s AND %1$s.%s", dbName, tableName,
					"PERSON");
		}
		if (tableName.toUpperCase().startsWith("ZUSAE")) {
			return String.format("%s.%s AND %1$s.%2$s%s AND %1$s.%s", dbName,
					tableName, "_info", "PERSON");
		}
		String result = "";
		if (tableName.toUpperCase().startsWith("ORT")) {
			result += String.format("%s.%s%s", dbName, tableName,
					"_abweichung_norm, ");
		}
		result += String.format("%s.%s%s AND %1$s.%2$s%s", dbName, tableName,
				"_norm", "_trad");
		if (!tableName.toUpperCase().startsWith("ANREDE")) {
			result += String.format(", %s.%s%s", dbName, tableName, "_info");
		}
		result += String.format(", %s.%s", dbName, "Quellen");
		return result;
	}

	String getWhere(int source) {
		if (tableName.toUpperCase().startsWith("FAKUL")
				|| tableName.startsWith("FUND")) {
			return String.format("%s.%s.%sID == %1$s.%s.%3$sID", dbName,
					tableName, tableName, "PERSON");
		}
		if (tableName.toUpperCase().startsWith("ZUSAE")) {
			return String
					.format("%s.%s_info.%2$sInfoID = %1$s.%2$s_trad.%2$sInfoID AND %1$s.%2$s_info.%sID = %1$s.%3$s.%3$sID",
							dbName, tableName, "Person");
		}

		String result = "";
		if (tableName.toUpperCase().startsWith("ORT")) {
			result += String
					.format("%s.%s_abweichung_norm.%2$sAbweichungNormID = %1$s.%2$s_norm.AbweichungNormID AND ",
							dbName, tableName);
		}
		result += String.format(
				"%s.%s_norm.%2$sNormID = %1$s.%2$s_trad.%2$sNormID ", dbName,
				tableName);
		if (!tableName.toUpperCase().startsWith("ANREDE")) {
			result += String.format(
					"%s.%s_trad.%2$sTradID = %1$s.%2$s_info.%2$sTradID ",
					dbName, tableName);
		}
		if (source != SourceKeys.NO_SELECTION) {
			result += String.format(
					" AND %s.%s%s.QuellenID = %1$s.Quellen.QuellenID", dbName,
					tableName, "_info");
		}
		if ("JESUIT".equals(column) || "ADLIG".equals(column)) {
			result += String.format(" AND %s.%s.%s = %s", dbName, table,
					column, input);
		} else {
			result += String.format(" AND %s.%s.%s LIKE '%s %s %4$s'", dbName,
					table, column, "%", input);
		}
		return result;
	}
}
