package de.uniba.kinf.projm.hylleblomst.main;

import de.uniba.kinf.projm.hylleblomst.dataImport.Import;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

public class Main {

	public static void main(String[] args) {
		try {
			new Import()
					.addCSV("/Users/Hannes/git/KInf-Proj/doc/example_data.csv");
		} catch (ImportException e) {
			e.printStackTrace();
		}
	}

}
