package de.uniba.kinf.projm.hylleblomst.dataExport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.opencsv.CSVWriter;

import de.uniba.kinf.projm.hylleblomst.exceptions.ExportException;

/**
 * @author Simon
 *
 */
public class ExportToCSV {

	public ExportToCSV() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Export ResultSets to a CSV-file in file system
	 * 
	 * @param file
	 * @param crs
	 * @throws ExportException
	 */
	public void run(File file, CachedRowSet crs) throws ExportException {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new ExportException("File could not be created: "
						+ e.getMessage());
			}
		}

		try (ResultSet rs = crs.getOriginal();
				CSVWriter writer = new CSVWriter(new FileWriter(
						file.getAbsolutePath()));) {

			writer.writeAll(rs, true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExportException(e.getErrorCode()
					+ ": Could not handle this result: " + e.getMessage());
		} catch (IOException e) {
			throw new ExportException("Could not handle this file: "
					+ e.getMessage());
		}
	}
}
