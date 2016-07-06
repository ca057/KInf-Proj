package de.uniba.kinf.projm.hylleblomst.dataExport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import com.opencsv.CSVWriter;

import de.uniba.kinf.projm.hylleblomst.exceptions.ExportException;

/**
 * This classes provides methods to export data to files. Currently, only
 * Csv-format is supported.
 * 
 * @author Simon
 *
 */
public class ExportDataToFile {

	/**
	 * Export ResultSets to a CSV-file in file system
	 * 
	 * @param file
	 *            The location in the file system
	 * @param crs
	 *            The CachedRowSet containing the result of a query
	 * @throws ExportException
	 *             If the file could not be created in the location or if the
	 *             file could not be written to
	 */
	public void exportToCsv(File file, CachedRowSet crs) throws ExportException {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new ExportException("File could not be created: "
						+ e.getMessage());
			}
		}

		try (ResultSet rs = crs.getOriginal();
				Writer writer = new OutputStreamWriter(new FileOutputStream(
						file), StandardCharsets.UTF_8);
				CSVWriter csvWriter = new CSVWriter(writer);) {

			csvWriter.writeAll(rs, true);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExportException(e.getErrorCode()
					+ ": Could not handle this result: " + e.getMessage());
		} catch (IOException e) {
			throw new ExportException("Could not handle this file: "
					+ e.getMessage());
		}
	}
}
