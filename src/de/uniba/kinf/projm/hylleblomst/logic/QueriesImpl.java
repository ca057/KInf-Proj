package de.uniba.kinf.projm.hylleblomst.logic;

public class QueriesImpl implements Queries {
	DBAccess db;

	@Override
	public void extendedSearch(TableKeys[] columns, Object[] input, int[] source) {
		String select = "SELECT *";
		String from = "FROM ";
		String where = "WHERE ";
		for (int i = 0; i < columns.length; i++) {
			from += columns[i] + " ";
			where += columns[i] + "." + columns[i].toString() + " = "
					+ input[i];
		}
		System.out.println(select + from + where);
	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {
		db = new DBAccess("jdbc:derby:db/MyDB", "admin", "password");
	}

	private void startQuery() {

	}

}
