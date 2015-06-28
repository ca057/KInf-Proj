package de.uniba.kinf.projm.hylleblomst.logic;

/**
 * @author Hannes
 *
 */
public interface QueryRequest {

	SearchFieldKeys getSearchField();

	Object getInput();

	int getSource();

	String getTable();

	String getColumn();

	/**
	 * @return
	 */
	String getWhere();

}