package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.nio.file.Files;
import java.nio.file.Path;

public class Validation {

	/**
	 * Checks whether a path leads to a valid CSV.
	 * 
	 * @param path
	 *            The examined path
	 * @return true if file is valid <br/>
	 *         false if file is not a regular file and ends with .csv
	 */
	public static boolean isValidCSV(Path path) {
		if (path.toString().endsWith(".csv") && Files.isRegularFile(path)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether a object is <code>null</code>.
	 * 
	 * @param object
	 *            The examined object
	 * @return true if object is not null <br/>
	 *         false if object is null
	 */
	public static boolean isNotNull(Object object) {
		if (object == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param list
	 */
	public static void checkIDs(String id) {

	}

}
