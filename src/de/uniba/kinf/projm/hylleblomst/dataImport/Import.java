package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Import {

	public static void addImport(String pathS) throws ImportException {
		try {
			Path path = Paths.get(pathS);
			CsvHelper csvhelper = new CsvHelper(path);
			List<String[]> test = csvhelper.getAllLines();
			System.out.println(test);
		} catch (IOException e) {
			throw new ImportException(e.getMessage());
		}
	}

}
