package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.nio.file.Files;
import java.nio.file.Path;

public class Validation {

	/**
	 * Checks if an ID is already in use in the database.
	 * 
	 * @return true if taken <br/>
	 *         false if new ID
	 */
	public static boolean isTakenID() {
		// TODO Implement: If person number is taken, return true. (via
		// SQL-query)
		return false;
	}

	public static boolean isValidCSV(Path path) {
		if (path.toString().endsWith(".csv") && Files.isRegularFile(path)) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Object object) {
		if (object == null) {
			return false;
		} else {
			return true;
		}
	}

}
