package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.SQLException;
import java.util.Map;

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
	void extendedSearch(Map<Enum<ColumnKeys>, Object> map);
}
