package de.uniba.kinf.projm.hylleblomst.logic;

public class QueriesImpl implements Queries {
	DBAccess db;

	@Override
	public void extendedSearch(TableKeys[] columns, Object[] input,
			int[] source) {

	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {
		db = new DBAccess("jdbc:derby:db/MyDB", "admin", "password");
	}

	private void startQuery() {

	}

}
