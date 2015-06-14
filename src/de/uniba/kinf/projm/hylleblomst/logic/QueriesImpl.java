package de.uniba.kinf.projm.hylleblomst.logic;

public class QueriesImpl implements Queries {
	DBAccess db;

	@Override
	public void extendedSearch(QueryRequest[] queryRequest) {
		String select = "SELECT *";
		String from = "FROM ";
		String where = "WHERE ";
		for (int i = 0; i < queryRequest.length; i++) {

		}
		System.out.println(select + from + where);
	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {
		db = new DBAccess(dbURL, user, password);
	}

	private String searchFieldKeyToTable(SearchFieldKeys key) {
		if (key.toString().equals("ANREDE")) {
			return "ANREDE_TRAD";
		}
		return null;
	}

}
