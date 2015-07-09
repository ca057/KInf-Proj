package de.uniba.kinf.projm.hylleblomst.dataImport;

import java.io.File;
import java.nio.file.Paths;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

public class CsvFormatVerifier {

	public File csvFile;
	CsvHelper referenceFile;

	public CsvFormatVerifier(File file) {
		this.csvFile = file;
		referenceFile = new CsvHelper(Paths.get("./doc/reference.csv"));
	}

	public boolean verifyCsv() {
		CsvHelper fileToVerify = new CsvHelper(csvFile.toPath());

		try {
			return fileToVerify.getLine(1) == referenceFile.getLine(1);
		} catch (ImportException e) {
			return false;
		}
	}
}
