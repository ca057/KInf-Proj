package de.uniba.kinf.projm.hylleblomst.dataImport.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.dataImport.CsvHelper;
import de.uniba.kinf.projm.hylleblomst.dataImport.ImportData;
import de.uniba.kinf.projm.hylleblomst.dataImport.ImportDatabase;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;
import de.uniba.kinf.projm.hylleblomst.keys.DBUserKeys;

/**
 * @author Johannes, Simon
 *
 */
public class ImportDataImpl implements ImportData {

	@Override
	public void addData(String databaseURL, String path) throws ImportException {
		if (path == null || path.isEmpty()) {
			throw new IllegalArgumentException("Der übergebene String ist leer oder {@code null}");
		}
		if (!(Files.isRegularFile(Paths.get(path)))) {
			throw new ImportException("Der übergebene Pfad führt nicht zu einer auswertbaren Datei.");
		}
		if (path.endsWith("csv")) {
			addCSV(databaseURL, Paths.get(path));
		} else {
			throw new ImportException("Dieser Dateityp kann leider nicht verarbeitet werden.");
		}
	}

	/**
	 * Adds data of a file to the database.
	 * <p>
	 * 
	 * Reads a CSV-file and passes on a <code>List</code> of
	 * <code>String[]</code>.
	 * 
	 * @param path
	 *            The path of the file
	 * @throws ImportException
	 * 
	 */
	private void addCSV(String databaseURL, Path path) throws ImportException {
		CsvHelper csvhelper = new CsvHelper(path);
		List<String[]> rows = csvhelper.getAllLines();
		try {
			ImportDatabase database = new ImportDatabaseImpl(databaseURL, DBUserKeys.adminUser,
					DBUserKeys.adminPassword);
			database.importData(rows);
		} catch (ImportException e) {
			throw new ImportException(e.getMessage());
		}
	}
}
