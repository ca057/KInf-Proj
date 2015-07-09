package de.uniba.kinf.projm.hylleblomst.logic;

import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;

/**
 * @author Hannes
 *
 */
public interface UserQueries {

	/**
	 * @return
	 */
	SearchFieldKeys getSearchField();

	/**
	 * @return
	 */
	String getInput();

	/**
	 * @return
	 */
	int getSource();

	/**
	 * @return
	 */
	String getTable();

	/**
	 * @return
	 */
	String getColumn();

	/**
	 * @return
	 */
	String getWhere();

	/**
	 * @return
	 */
	Boolean isOpenSearch();

	/**
	 * @return
	 */
	Boolean useOrCondition();

}