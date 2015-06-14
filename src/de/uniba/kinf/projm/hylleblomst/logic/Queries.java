package de.uniba.kinf.projm.hylleblomst.logic;

public interface Queries {

	/**
	 * 
	 * <b>Preconditions:</b>
	 * <ul>
	 * <li>key: The row
	 * <li>object: The user input
	 * </ul>
	 * 
	 * @param map
	 */
	void extendedSearch(QueryRequest[] queryRequest);

	void setDatabase(String dbURL, String user, String password);

}
