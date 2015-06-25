package de.uniba.kinf.projm.hylleblomst.logic;

/**
 * @author Hannes
 *
 */
public interface QueryRequest {

	/**
	 * @return The search field of the request.
	 */
	public abstract SearchFieldKeys getSearchField();

	/**
	 * @return The input of the request.
	 */
	public abstract Object getInput();

	/**
	 * Sets the search field of the query request. The search field states in
	 * which field of a GUI the user entered a query. Those fields are labelled
	 * as {@code Enum} in {@link SearchFieldKeys}.
	 * 
	 * @param searchField
	 *            The search field of the request.
	 * 
	 * @see SearchFieldKeys
	 */
	public void setSearchField(SearchFieldKeys searchField);

	/**
	 * Sets the input of the query. This is the original entry of a user in a
	 * specific search field.
	 * 
	 * <b> Preconditions</b>
	 * <ul>
	 * <li>input must not be null
	 * </ul>
	 * 
	 * @param input
	 *            The input of the request.
	 */
	public abstract void setInput(Object input);

	/**
	 * @return
	 */
	public abstract int getSource();

	/**
	 * @param source
	 */
	public abstract void setSource(int source);

	/**
	 * @return
	 */
	public abstract String getTable();

	/**
	 * @return
	 */
	public abstract String getColumn();

	/**
	 * @param table
	 * @param i
	 * @return
	 */
	public abstract String getColumnName(String table, int i);

}