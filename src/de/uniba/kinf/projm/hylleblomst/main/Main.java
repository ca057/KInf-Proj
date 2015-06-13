package de.uniba.kinf.projm.hylleblomst.main;

import de.uniba.kinf.projm.hylleblomst.dataImport.ImportDataImpl;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

public class Main {

	public static void main(String[] args) {
		try {
			new ImportDataImpl().addData("doc/example_data.csv");
		} catch (ImportException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
