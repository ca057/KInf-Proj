package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.SQLException;
import java.util.Collection;

import javax.sql.rowset.CachedRowSet;

/**
 * @author Johannes
 *
 */
public interface SearchInitiator {

	/**
	 * Processes userQueries to a sqlStatement, initiates the search and returns
	 * the outcome.
	 * <p>
	 * 
	 * <b>Preconditions</b>:
	 * <ul>
	 * <li>Have to be specified of the respective implementation
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
	 * <li>All fields with user input AS their specific {@code SearchFieldKey}
	 * </ul>
	 * 
	 * @param userQuery
	 *            A {@code Collection} of {@link UserQuery} with all
	 * @return A {@Code ChachedRowSet} with the outcome of the search
	 * @throws SQLException
	 */
	CachedRowSet search(Collection<UserQuery> userQuery) throws SQLException;

	/**
	 * Gets all information of a person.
	 * 
	 * @param id
	 *            The ID of the person
	 * @return A {@CachedRowSet} with the outcome of the search
	 * @throws SQLException
	 */
	CachedRowSet searchPersonOrNotation(UserQuery userQuery) throws SQLException;
}
