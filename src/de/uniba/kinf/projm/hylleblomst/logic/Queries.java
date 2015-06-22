package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Hannes
 *
 */
public interface Queries {

	/**
	 * 
	 * <b>Preconditions</b>:
	 * <ul>
	 * <li>
	 * <li>
	 * </ul>
	 * 
	 * @param queryRequest
	 * @throws SQLException
	 */
	void search(Collection<QueryRequestImpl> queryRequest) throws SQLException;

	/**
	 * Gets all information of a person.
	 * 
	 * @param id
	 *            The ID of the person
	 */
	void searchPerson(int id);

	/**
	 * 
	 * @param dbURL
	 * @param user
	 * @param password
	 */
	void setDatabase(String dbURL, String user, String password);

}
