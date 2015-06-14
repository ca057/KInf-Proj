package de.uniba.kinf.projm.hylleblomst.logic;

public class QueriesImpl implements Queries {
	DBAccess db;

	@Override
	public void extendedSearch(ColumnKeys[] columns, Object[] input,
			int[] source) {
		getColumns(columns);
		startQuery();
	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {
		db = new DBAccess("jdbc:derby:db/MyDB", "admin", "password");
	}

	private void getColumns(ColumnKeys[] columns) {
		String[] result = new String[columns.length];
		for (int i = 0; i < columns.length - 1; i++) {
			if (ColumnKeys.ANREDE.equals(columns[i])) {
				result[i] = "";
			} else if (ColumnKeys.ANREDE_NORM.equals(columns[i])) {
				result[i] = null;
			} else if (ColumnKeys.TITEL.equals(columns[i])) {
				result[i] = "846c00a0-014d-ee95-e094-0000074d4de8";
			} else if (ColumnKeys.TITEL_NORM.equals(columns[i])) {
				result[i] = "295dc08b-014d-ee95-e094-0000074d4de8";
			}
		}
	}

	private void startQuery() {

	}

}
