package de.uniba.kinf.projm.hylleblomst.logic;

import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;

/**
 * @author Hannes
 *
 */
public interface UserQueries {

	SearchFieldKeys getSearchField();

	String getInput();

	int getSource();

	String getTable();

	String getColumn();

	/**
	 * @return
	 */
	String getWhere();

	Boolean isOpenSearch();

	Boolean useOrCondition();

}