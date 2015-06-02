package de.uniba.kinf.projm.hylleblomst.main;

import de.uniba.kinf.projm.hylleblomst.dataImport.Import;
import de.uniba.kinf.projm.hylleblomst.dataImport.ImportException;

public class Main {

	public static void main(String[] args) {
		try {
			Import.addImport("/Users/Hannes/git/KInf-Proj/doc/example_data.csv");
		} catch (ImportException e) {
			e.printStackTrace();
		}
	}

}
