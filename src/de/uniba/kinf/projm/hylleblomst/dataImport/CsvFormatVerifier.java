package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.io.File;
import java.nio.file.Paths;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

/**
 * Verifies the format of a csv to match with a referenced csv. This aims to
 * ensure type compatibility for the data import.
 * 
 * @author Simon
 *
 */
public class CsvFormatVerifier {

	public static boolean verifyCsv(File csvFile) throws ImportException {
		if (!csvFile.isFile() || !csvFile.getAbsolutePath().endsWith(".csv")) {
			throw new ImportException(
					"Der übergebene Pfad führt nicht zu einer regulären csv-Datei");
		}

		CsvHelper referenceFile = new CsvHelper(
				Paths.get("./doc/reference.csv"));
		CsvHelper fileToVerify = new CsvHelper(csvFile.toPath());

		try {
			String[] tmp1 = fileToVerify.getLine(-1);
			String[] tmp2 = referenceFile.getLine(-1);

			for (int i = 0; i < Math.max(tmp1.length, tmp2.length); i++) {
				if (!tmp1[i].equals(tmp2[i])) {
					return false;
				}
			}
		} catch (ImportException e) {
			return false;
		}
		return true;
	}
}
