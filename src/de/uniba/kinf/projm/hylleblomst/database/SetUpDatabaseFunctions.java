package de.uniba.kinf.projm.hylleblomst.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;

/**
 * This class allows the incorporation of user defined functions into a
 * database.
 * 
 * @author Simon
 *
 */
public class SetUpDatabaseFunctions {
	/**
	 * Sets up all defined functions in this class to enhance functionality of
	 * the chosen database.
	 * 
	 * @param con
	 *            The connection to the database
	 * @throws SetUpException
	 *             If one or more of the functions could not be set up.
	 */
	public void setUpUserDefinedFunctions(Connection con) throws SetUpException {
		setUpGroupConcat(con);
	}

	/**
	 * This method sets up a standard aggregation function for Derby.
	 * 
	 * @param con
	 *            The connection to the database
	 * @throws SetUpException
	 */
	void setUpGroupConcat(Connection con) throws SetUpException {
		Path pathToInstallationJar = Paths.get("./lib/groupconcat.jar");
		String installationPath = "";

		try {
			installationPath = con.getMetaData().getURL()
					.replaceFirst("jdbc:derby:", "").replaceFirst("/MyDB", "");
			installationPath += "/groupconcat.jar";
			if (!Files.exists(Paths.get(installationPath))) {
				Files.copy(pathToInstallationJar, Paths.get(installationPath));
			}
		} catch (SQLException e) {
			if (e.getErrorCode() != 30000) {
				throw new SetUpException(e);
			}
		} catch (IOException e) {
			throw new SetUpException(e);
		}

		String sqlGroupConcat = "CREATE DERBY AGGREGATE HYLLEBLOMST.AGGREGATE_VARCHAR FOR VARCHAR(255) "
				+ "RETURNS VARCHAR(2000) "
				+ "EXTERNAL NAME 'de.uniba.kinf.projm.hylleblomst.database.utils.GroupConcat'";
		String sqlCall = "CALL SQLJ.INSTALL_JAR ('" + installationPath
				+ "','groupconcat',0)";

		try (PreparedStatement stmt = con.prepareStatement(sqlGroupConcat);
				PreparedStatement stmtCall = con.prepareCall(sqlCall);) {
			stmt.executeUpdate();
			stmtCall.executeUpdate();
			Files.delete(Paths.get(installationPath));
		} catch (SQLException e) {
			if (e.getErrorCode() != 30000) {
				throw new SetUpException(e.getErrorCode()
						+ ": Function AGGREGATE_VARCHAR could not be set up: "
						+ e.getMessage(), e);
			}
		} catch (IOException e) {
			throw new SetUpException(
					"Function AGGREGATE_VARCHAR could not be set up: "
							+ e.getMessage(), e);
		}
	}
}
