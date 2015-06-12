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

	private String insertSql = "INSERT into ";

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
	public void importData(List<String[]> rows) throws ImportException {
		System.out.println("Kommt in ImportDatabaseImpl an" + rows);

		rows = rows.subList(1, rows.size());

		for (String[] strings : rows) {

			if (!strings[4].equals("")) {
				System.out.println(strings[4]);
				insertAnredeNorm(strings[4]);
			}
		}
	}

	private boolean insertAnredeNorm(String anredeNorm) throws ImportException {
		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement();) {

			String table = "hylleblomst.Anrede_Norm";
			int id = getMaxID(table) + 1;

			String insertStmt = String.format("%1$s %2$s values(%3$d, '%4$s')",
					insertSql, table, id, anredeNorm);

			stmt.execute(insertStmt);
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new ImportException("AnredeNorm could not be inserted", e);
		}
		return false;
	}

	/**
	 * Returns the highest ID already taken in a table of the schema HYLLEBLOMST
	 * 
	 * @param tableName
	 *            The table in which to look for highest given ID Must be in the
	 *            format <code>HYLLEBLOMST.table_name</code>
	 * @return The maximum ID as <code>int</code>
	 */
	private int getMaxID(String tableName) {
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

			con.setAutoCommit(false);

			rs.first();
			result += rs.getInt(1);

			con.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
