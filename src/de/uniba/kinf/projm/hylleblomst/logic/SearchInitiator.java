package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.SQLException;
import java.util.Collection;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.DatabaseKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;

/**
 * A SearchInitiator is the interface between UI and database. It receives all
 * relevant data from the Ui and returns the results of a query. Also, it sets
 * the login to the database.
 * 
 * @author Johannes
 *
 */
public interface SearchInitiator {

	/**
	 * 
	 * @param user
	 */
	void setDbUser(String user);

	/**
	 * @param password
	 */
	void setDbPassword(String password);

	/**
	 * @param dbKey
	 */
	void setDbKey(DatabaseKeys dbKey);

	/**
	 * Processes userQueries to a sqlStatement, initiates the search and returns
	 * the outcome.
	 * <p>
	 * 
	 * <b>Preconditions</b>:
	 * <ul>
	 * <li>Have to be specified by the respective implementation
	 * <li>If a dates are analyzed, missing information must be filled
	 * correctly.<br/>
	 * <ul>
	 * <li>Example1: User searches for date later than 1720. The passed input
	 * has to be 1720-01-01.
	 * <li>Example2: User fills fields for date earlier than March 1720. The
	 * passed input has to be 1720-03-31.
	 * </ul>
	 * </ul>
	 * 
	 * <b>Postconditions</b>: <br/>
	 * The returned CachedRowSet contains
	 * <ul>
	 * <li>All fields AS their specific value of {@link ColumnNameKeys}
	 * <li>If searched for {@link SearchFieldKeys#EINSCHREIBEDATUM_BIS} or
	 * {@link SearchFieldKeys#EINSCHREIBEDATUM_VON}, the column
	 * {@link ColumnNameKeys#DATUMS_FELDER_GESETZT} is passed as well. More
	 * information can be found there.
	 * </ul>
	 * 
	 * @param userQuery
	 *            A {@code Collection} of {@link UserQuery} with all inputs the
	 *            user made.
	 * @return A {@Code ChachedRowSet} with the outcome of the search
	 * @throws SQLException
	 */
	CachedRowSet search(Collection<UserQuery> userQuery) throws SQLException;

	/**
	 * Gets all information of a person or of a specific field in a specific
	 * source and returns it.
	 * <P>
	 * 
	 * <b>Preconditions:</b><br/>
	 * <ul>
	 * <li>Have to be specified by the respective implementation.
	 * <ul>
	 * 
	 * <b>Postconditions<b><br/>
	 * <ul>
	 * <li>
	 * </ul>
	 * 
	 * @param userQuery
	 *            with information to build the SQL Statement
	 * @return A {@CachedRowSet} with the outcome of the search
	 * @throws SQLException
	 */
	CachedRowSet searchPersonOrNotation(UserQuery userQuery) throws SQLException;

}
