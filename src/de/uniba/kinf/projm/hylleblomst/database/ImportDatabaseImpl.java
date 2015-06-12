package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

public class ImportDatabaseImpl implements ImportDatabase {

	private Validation validation;

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

		validation = new Validation(dbURL, user, password);
	}

	@Override
	public void importData(List<String[]> rows) throws ImportException {
		System.out.println("Kommt in ImportDatabaseImpl an" + rows);

		rows = rows.subList(1, rows.size());

		for (String[] strings : rows) {

			if (!strings[4].equals("")) {
				insertAnredeNorm(strings[4]);
			}
			if (!strings[6].equals("")) {
				insertVornameNorm(strings[6]);
			}
			if (!strings[18].equals("")) {
				insertNameNorm(strings[18]);
			}
			if (!strings[42].equals("")) {
				insertOrtAbweichungNorm(strings[42]);
			}
			if (!strings[47].equals("")) {
				// insertFakultaeten(strings[47]);
			}
			if (!strings[49].equals("")) {
				insertFachNorm(strings[49]);
			}
			if (!strings[52].equals("")) {
				// insertWirtschaftslageNorm(strings[52]);
			}
			if (!strings[57].equals("")) {
				// insertSeminarNorm(strings[57]);
			}
			if (!strings[65].equals("")) {
				// insertTitelNorm(strings[65]);
			}
			if (!strings[77].equals("")) {
				// insertFundorte(strings[77]);
			}
		}
	}

	// TODO Erweiterung um Anmerkungen (Spalte 78)
	private boolean insertOrtAbweichungNorm(String nameNorm)
			throws ImportException {
		String table = "hylleblomst.Ort_Abweichung_Norm";
		String column = "Name";

		if (!validation.entryAlreadyInDatabase(nameNorm, table, column)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password); Statement stmt = con.createStatement();) {

				int id = validation.getMaxID(table) + 1;

				String insertStmt = String.format(
						"%1$s %2$s values(%3$d, '%4$s')", insertSql, table, id,
						nameNorm);

				stmt.execute(insertStmt);

				return true;
			} catch (SQLException e) {
				throw new ImportException(
						"A AbweichungNorm could not be inserted", e);
			}
		}
		return false;
	}

	private boolean insertVornameNorm(String nameNorm) throws ImportException {
		String table = "hylleblomst.Vorname_Norm";
		String column = "Name";

		if (!validation.entryAlreadyInDatabase(nameNorm, table, column)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password); Statement stmt = con.createStatement();) {

				int id = validation.getMaxID(table) + 1;

				String insertStmt = String.format(
						"%1$s %2$s values(%3$d, '%4$s')", insertSql, table, id,
						nameNorm);

				stmt.execute(insertStmt);

				return true;
			} catch (SQLException e) {
				throw new ImportException(
						"A VornameNorm could not be inserted", e);
			}
		}
		return false;
	}

	private boolean insertNameNorm(String nameNorm) throws ImportException {
		String table = "hylleblomst.Name_Norm";
		String column = "Name";

		if (!validation.entryAlreadyInDatabase(nameNorm, table, column)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password); Statement stmt = con.createStatement();) {

				int id = validation.getMaxID(table) + 1;

				String insertStmt = String.format(
						"%1$s %2$s values(%3$d, '%4$s')", insertSql, table, id,
						nameNorm);

				stmt.execute(insertStmt);

				return true;
			} catch (SQLException e) {
				throw new ImportException("A NameNorm could not be inserted", e);
			}
		}
		return false;
	}

	private boolean insertFachNorm(String fachNorm) throws ImportException {
		String table = "hylleblomst.Fach_Norm";
		String column = "Name";

		if (!validation.entryAlreadyInDatabase(fachNorm, table, column)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password); Statement stmt = con.createStatement();) {

				int id = validation.getMaxID(table) + 1;

				String insertStmt = String.format(
						"%1$s %2$s values(%3$d, '%4$s')", insertSql, table, id,
						fachNorm);

				stmt.execute(insertStmt);

				return true;
			} catch (SQLException e) {
				throw new ImportException("A FachNorm could not be inserted", e);
			}
		}
		return false;
	}

	private boolean insertAnredeNorm(String anredeNorm) throws ImportException {
		String table = "hylleblomst.Anrede_Norm";
		String column = "Name";

		if (!validation.entryAlreadyInDatabase(anredeNorm, table, column)) {
			try (Connection con = DriverManager.getConnection(dbURL, user,
					password); Statement stmt = con.createStatement();) {

				int id = validation.getMaxID(table) + 1;

				String insertStmt = String.format(
						"%1$s %2$s values(%3$d, '%4$s')", insertSql, table, id,
						anredeNorm);

				stmt.execute(insertStmt);

				return true;
			} catch (SQLException e) {
				throw new ImportException("A AnredeNorm could not be inserted",
						e);
			}
		}
		return false;
	}
}
