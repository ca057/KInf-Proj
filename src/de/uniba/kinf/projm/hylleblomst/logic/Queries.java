package de.uniba.kinf.projm.hylleblomst.logic;

import java.util.Collection;

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
	void search(Collection<QueryRequest> queryRequest);

	void setDatabase(String dbURL, String user, String password);

}
