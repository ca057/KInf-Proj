package de.uniba.kinf.projm.hylleblomst.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;

public class SetUpDatabaseFunctions {
	/**
	 * Sets up all defined functions in this class to enhance functionality of
	 * the chosen database.
	 * 
	 * @param dbURL
	 *            The URL to the database which contains the driver definition
	 *            and further options.
	 * @param user
	 *            The user, typically the admin.
	 * @param password
	 *            The password of the user.
	 * @throws SetUpException
	 *             If one or more of the functions could not be set up.
	 */
	public void setUpUserDefinedFunctions(String dbURL, String user,
			String password) throws SetUpException {
		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);) {

			setUpGroupConcat(con);

		} catch (SQLException e) {
			throw new SetUpException(e.getErrorCode()
					+ ": Functions could not be set up: " + e.getMessage(), e);
		}
	}

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
