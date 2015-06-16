package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.SQLException;
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
	 * @throws SQLException
	 */
	void search(Collection<QueryRequest> queryRequest) throws SQLException;

	void searchPerson(int id);

	void setDatabase(String dbURL, String user, String password);

}
