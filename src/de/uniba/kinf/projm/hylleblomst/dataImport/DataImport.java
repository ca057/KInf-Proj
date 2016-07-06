package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.util.List;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

/**
 * Adds data to the database.
 * 
 * @author Hannes, Simon
 * 
 */
public interface DataImport {

	/**
	 * Imports data from a List of String[] into a database. The strings inside
	 * the String[] should be in a specific order, as otherwise the database
	 * might receive non-sensical entries. The order of entries in the String[]
	 * will not be validated.
	 * 
	 * @param rows
	 * @throws ImportException
	 *             Will be thrown if any error occurs while transferring the
	 *             data into the database.
	 */
	public void importData(List<String[]> rows) throws ImportException;

}
