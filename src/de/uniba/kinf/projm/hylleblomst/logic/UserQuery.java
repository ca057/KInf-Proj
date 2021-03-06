package de.uniba.kinf.projm.hylleblomst.logic;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;

/**
 * A UserQuery represents one input the user made. It is used to process this
 * data to a SQL statement, so all necessary information has to be given this
 * userQuery. For further information, please consult the specific
 * implementation of this interface.
 * 
 * @author Johannes
 *
 */
public interface UserQuery {

	/**
	 * @return the {@link SearchFieldKeys} of this UserQuery
	 */
	SearchFieldKeys getSearchField();

	/**
	 * @return the input of the user
	 */
	Object getInput();

	/**
	 * @return the {@link SourceKeys} of this UserQuery
	 */
	int getSource();

	/**
	 * @return the {@link TableNameKeys} of this UserQuery
	 */
	String getTable();

	/**
	 * @return the {@link ColumnNameKeys} of this UserQuery
	 */
	String getColumn();

	/**
	 * @return the WHERE part of a SQL statement
	 */
	String getWhere();

	/**
	 * Returns the number of "?" which have to be filled in with the query input
	 * in the prepared statement
	 * 
	 * @return
	 */
	int getNumberOfInputs();

	/**
	 * @return {@code true} - if the database column has the value {@code int}
	 */
	Boolean isInt();

	/**
	 * @return {@code true} - if the user wishes less exact search with more
	 *         results.
	 * 
	 */
	Boolean isOpenSearch();

	/**
	 * @return {@code true} - if the UserQuery was made to find all information
	 *         of a person.
	 */
	Boolean isPersonSearch();

	/**
	 * @return {@code true} - if the user wishes to combine the inputs with OR
	 *         instead of AND
	 */
	Boolean isOrCondition();
}