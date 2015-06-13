package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.SQLException;

public interface Queries {

	public void fullTextSearch(String query) throws SQLException;

	/**
	 * 
	 * <b>Preconditions:</b>
	 * <ul>
	 * <li>key: The row
	 * <li>object: The user input
	 * </ul>
	 * 
	 * @param map
	 */
	void extendedSearch(ColumnKeys[] colums, Object[] input, int[] source);
}
