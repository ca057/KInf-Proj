package de.uniba.kinf.projm.hylleblomst.logic;

public class QueriesImpl implements Queries {
	DBAccess db;

	@Override
	public void extendedSearch(ColumnKeys[] columns, Object[] input,
			int[] source) {
		startQuery();
	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {
		db = new DBAccess("jdbc:derby:db/MyDB", "admin", "password");
	}

	private void startQuery() {

	}

}
