package de.uniba.kinf.projm.hylleblomst.dataExport;

import java.io.File;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.exceptions.ExportException;

public class ExportToCSV {

	public ExportToCSV() {
		// TODO Auto-generated constructor stub
	}

	public void run(File file, CachedRowSet crs) throws ExportException {
		if (file != null) {

		} else {
			throw new ExportException("No suitable file to save to");
		}
	}

}
