package de.uniba.kinf.projm.hylleblomst.logic;

import de.uniba.kinf.projm.hylleblomst.keys.ColumnNameKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;

/**
 * UserQuery 
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
	String getInput();

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
	 * @return {@code true} - if the user wishes less exact search with more
	 *         results.
	 */
	Boolean isOpenSearch();

	/**
	 * @return {@code true} - if the user wishes to combine the inputs with OR
	 *         instead of AND
	 */
	Boolean useOrCondition();

}