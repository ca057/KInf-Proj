package de.uniba.kinf.projm.hylleblomst.dataImport;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

/**
 * Adds new entries to the database.
 * 
 * @author Hannes
 *
 */
public interface Import {

	// FIXME @see Link funktioniert nicht
	/**
	 * Takes the passed {@code String} path to the file and starts the import.
	 * First, the {@code String} is validated, then the single rows are given to
	 * the database-import.
	 * 
	 * @param path
	 *            The path to the file as {@code String}
	 * @return true - if import was successful
	 * @return false - if import failed
	 * @throws ImportException
	 * 
	 * @see {@link database.Import}
	 */
	public void addData(String path) throws ImportException;

}
