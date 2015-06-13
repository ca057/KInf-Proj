package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.database.ImportDatabase;
import de.uniba.kinf.projm.hylleblomst.database.ImportDatabaseImpl;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

public class ImportDataImpl implements ImportData {

	@Override
	public void addData(String path) throws Exception {
		if (path == null || path.isEmpty()) {
			throw new IllegalArgumentException(
					"Der übergebene String ist leer oder {@code null}");
		}
		if (!(Files.isRegularFile(Paths.get(path)))) {
			throw new ImportException(
					"Der übergebene Pfad führt nicht zu einer auswertbaren Datei.");
		}
		if (path.endsWith("csv")) {
			addCSV(Paths.get(path));
		} else {
			throw new ImportException("");
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
	 * @throws Exception
	 */
	private void addCSV(Path path) throws Exception {
		CsvHelper csvhelper = new CsvHelper(path);
		List<String[]> rows = csvhelper.getAllLines();
		try {
			ImportDatabase database = new ImportDatabaseImpl(
					"jdbc:derby:./db/MyDB;create=true", "admin", "password");
			database.importData(rows);
		} catch (ImportException e) {
			// TODO gefangene Exception genauer definieren, wenn in Database
			// passiert
			throw new ImportException(e.getMessage());
		}
	}
}
