package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

public class ImportDatabaseImpl implements ImportDatabase {

	private String dbURL;
	private String user;
	private String password;

	public ImportDatabaseImpl(String dbURL, String user, String password)
			throws ImportException {
		this.dbURL = dbURL;
		if (!user.equals("admin")) {
			throw new ImportException("No authorization for importing data");
		} else {
			this.user = user;
		}
		this.password = password;
	}

	// TODO Exception genauer definieren
	@Override
	public void importData(List<String[]> rows) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Kommt in ImportDatabseImpl an" + rows);
	}

	private int maxID(String tableName) {
		int result = 0;

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {

			String columnName = (tableName.replace("_", "") + "ID").replace(
					"hylleblomst.", "");
			String getMaxIDSQL = String.format("SELECT max(%s) FROM %s",
					columnName, tableName);
			ResultSet rs = stmt.executeQuery(getMaxIDSQL);

			result = rs.getInt(columnName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
