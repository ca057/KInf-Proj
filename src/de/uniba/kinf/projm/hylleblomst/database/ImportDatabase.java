package de.uniba.kinf.projm.hylleblomst.database;

import java.util.List;

/**
 * Adds data to the database.
 * 
 * @author Hannes
 * 
 * @see {@link de.uniba.kinf.projm.hylleblomst.dataImport.ImportData}
 *
 */
public interface ImportDatabase {

	public void importData(List<String[]> rows) throws Exception;

}
