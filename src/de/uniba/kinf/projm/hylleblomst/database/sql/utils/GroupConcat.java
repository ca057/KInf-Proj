package de.uniba.kinf.projm.hylleblomst.database.sql.utils;

/**
 * @author Simon
 *
 */
public class GroupConcat {
	/**
	 * This function returns a concatenation of the given Strings separated by
	 * the given separator
	 * 
	 * @param separator
	 * @param args
	 * @return A String in the format "arg0, arg1, ..., argN"
	 */
	public String groupConcat(String separator, String... args) {
		StringBuilder result = new StringBuilder("");

		for (String arg : args) {
			result.append(arg + separator + " ");
		}
		result.delete(result.length() - 2, result.length());

		return result.toString();
	}
}
