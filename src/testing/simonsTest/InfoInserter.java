package testing.simonsTest;

import java.util.List;

public class InfoInserter {
	public InfoInserter() {
		// TODO Auto-generated constructor stub
	}

	String composeSQLInsertStmt(String table, int id, List<String> values) {
		if (verifyTable(table)) {
			String sql = "INSERT into " + table + " values (" + id + ", "
					+ composeValuesIntoString(values) + ")";
			System.out.println(sql);
			return sql;
		}
		return null;
	}

	private boolean verifyTable(String table) {
		// TODO Verify table is already existing in database, else ask if table
		// shall be created

		return false;
	}

	private String composeValuesIntoString(List<String> values) {
		String valuelist = "";
		for (String string : values) {
			valuelist += "\'" + string + "\'" + ", ";
		}
		valuelist = valuelist.substring(0, valuelist.length() - 2);
		System.out.println(valuelist);
		return valuelist;
	}
}
