package de.uniba.kinf.projm.hylleblomst.logic;

public class QueriesImpl implements Queries {
	DBAccess db;

	@Override
	public void extendedSearch(QueryRequest[] queryRequests) {

		buildQuery(queryRequests);
	}

	private void buildQuery(QueryRequest[] queryRequests) {
		StringBuffer query = new StringBuffer();
		for (int i = 0; i < queryRequests.length; i++) {
			// QueryRequest request : queryRequests) {
			if (i == 0) {
				query.append("SELECT: "
						+ searchFieldKeyToTable(queryRequests[i]
								.getSearchField()));
			} else {
				query.append(", "
						+ searchFieldKeyToTable(queryRequests[i]
								.getSearchField()));
			}
		}
		for (int i = 0; i < queryRequests.length; i++) {
			// QueryRequest request : queryRequests) {
			if (i == 0) {
				query.append("FROM: "
						+ searchFieldKeyToTable(queryRequests[i]
								.getSearchField()));
			} else {
				query.append(", "
						+ searchFieldKeyToTable(queryRequests[i]
								.getSearchField()));
			}
		}
		for (int i = 0; i < queryRequests.length; i++) {
			// QueryRequest request : queryRequests) {
			if (i == 0) {
				query.append("WHERE: " + searchFieldKeyToTable(null) + "="
						+ queryRequests[i].getInput());
			} else {
				query.append(", " + searchFieldKeyToTable(null) + "="
						+ queryRequests[i].getInput());
			}
		}
	}

	@Override
	public void setDatabase(String dbURL, String user, String password) {
		db = new DBAccess(dbURL, user, password);
	}

	/**
	 * Returns the name of the table the {@code SearchFieldKey} key belongs to.
	 * 
	 * @param key
	 * @return
	 */
	private String searchFieldKeyToTable(SearchFieldKeys key) {
		if (key.toString().equals("ADELIG")) {
			return "PERSON";
		} else if (key.toString().equals("JESUIT")) {
			return "PERSON";
		} else if (key.toString().equals("STUDIENJAHR")) {
			return "PERSON";
		} else if (key.toString().equals("STUDJAHR_VON")) {
			return "PERSON";
		} else if (key.toString().equals("STUDJAHR_BIS")) {
			return "PERSON";
		} else if (key.toString().equals("EINSCHREIBEDATUM_TAGE")) {
			return "PERSON";
		} else if (key.toString().equals("EINSCHREIBEDATUM_MONATE")) {
			return "PERSON";
		} else if (key.toString().equals("EINSCHREIBEDATUM_JAHRE")) {
			return "PERSON";
		} else if (key.toString().equals("ANMERKUNGEN")) {
			return "PERSON";
		} else if (key.toString().equals("NUMMER")) {
			return "PERSON";
		} else if (key.toString().equals("SEITE_ORIGINALE")) {
			return "PERSON";
		} else if (key.toString().equals("NUMMER_HESS")) {
			return "PERSON";
		} else {
			return key.name();
		}
	}

}
