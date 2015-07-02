package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Hannes
 *
 */
/**
 * @author Hannes
 *
 */
public interface Queries {

	/**
	 * <b>Preconditions</b>:
	 * <ul>
	 * <li>
	 * <li>
	 * </ul>
	 * 
	 * <b>Postconditions</b>: <br/>
	 * The return value resultSet contains
	 * <ul>
	 * <li>Person.PersonID AS PersonID
	 * <li>Normalized name AS vorname_norm
	 * <li>Normalized surname AS nachname_norm
	 * <li>Normalized place AS ort_norm
	 * <li>Normalized faculty AS fakultaet_norm
	 * <li>All fields with search entries AS their specific
	 * {@code SearchFieldKey}
	 * </ul>
	 * 
	 * @param queryRequest
	 *            A {@code Collection} of {@link QueryRequest} with all
	 * @return A {@Code ResultSet} with the outcome ot the search
	 * @throws SQLException
	 */
	ArrayList<PersonItem> search(Collection<QueryRequest> queryRequest) throws SQLException;

	/**
	 * Gets all information of a person.
	 * 
	 * @param id
	 *            The ID of the person
	 * @return
	 * @throws SQLException
	 */
	ResultSet searchPerson(String id) throws SQLException;
}
