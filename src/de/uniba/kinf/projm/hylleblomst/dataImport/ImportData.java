package de.uniba.kinf.projm.hylleblomst.dataImport;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;
import de.uniba.kinf.projm.hylleblomst.keys.DatabaseKeys;

/**
 * Passes rows to
 * {@link de.uniba.kinf.projm.hylleblomst.database.ImportDatabase}.
 * 
 * @author Hannes
 * 
 * @see {@link de.uniba.kinf.projm.hylleblomst.database.ImportDatabase}
 *
 */
public interface ImportData {

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
	 * @throws Exception
	 * 
	 * @see {@link ImportData.Import}
	 */
	public void addData(DatabaseKeys db, String path) throws ImportException,
			Exception;

}
