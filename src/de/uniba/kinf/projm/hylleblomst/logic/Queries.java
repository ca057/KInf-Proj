package de.uniba.kinf.projm.hylleblomst.logic;


public interface Queries {

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
	public void extendedSearch(TableKeys[] colums, Object[] input, int[] source);

	public void setDatabase(String dbURL, String user, String password);
}
