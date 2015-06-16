package de.uniba.kinf.projm.hylleblomst.database;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class has the sole purpose of setting up a database with all tables
 * required to import project-specific data.
 * 
 * @author Simon
 *
 */
public class SetUpMatrikelDatabase {
	private Path path;

	private String dbURL;

	/**
	 * Sets up a new SetUpMatrikelDatabase
	 * 
	 * @param directory
	 */
	public SetUpMatrikelDatabase(String directory) {
		this.path = Paths.get(directory);
	}

	/**
	 * Starts the main method of the class SetUpMatrikelDatabase that will
	 * initiate a database in the specified location (if not already present).
	 * Will throw no exceptions.
	 * 
	 * @return <code>True</code> if database was created successfully,
	 *         <code>false</code> if the database already existed or could not
	 *         be created.
	 */
	public boolean run() {
		// TODO implement method

		return false;
	}
}
