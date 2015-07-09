package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.io.File;
import java.nio.file.Paths;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

public class CsvFormatVerifier {

	public File csvFile;
	CsvHelper referenceFile;

	public CsvFormatVerifier(File file) throws ImportException {
		if (file.isFile() && file.getAbsolutePath().endsWith(".csv")) {
			this.csvFile = file;
			referenceFile = new CsvHelper(Paths.get("./doc/reference.csv"));
		} else {
			throw new ImportException(
					"Given path doesn't point to a regular csv-file");
		}
	}

	public boolean verifyCsv() {
		CsvHelper fileToVerify = new CsvHelper(csvFile.toPath());

		try {
			String[] tmp1 = fileToVerify.getLine(-1);
			String[] tmp2 = referenceFile.getLine(-1);

			for (int i = 0; i < Math.min(tmp1.length, tmp2.length); i++) {
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
