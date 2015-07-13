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
		String sqlGroupConcat = "CREATE DERBY AGGREGATE HYLLEBLOMST.AGGREGATE_VARCHAR FOR VARCHAR(255) "
				+ "RETURNS VARCHAR(2000) "
				+ "EXTERNAL NAME 'de.uniba.kinf.projm.hylleblomst.database.utils.GroupConcat'";

		Path file = Paths.get("./lib/groupconcat.jar");
		String dbLocation = "";

		try {
			dbLocation = con.getMetaData().getURL()
					.replaceFirst("jdbc:derby:", "").replaceFirst("MyDB", "");
			dbLocation += "/groupconcat.jar";
			// Files.copy(file, Paths.get(dbLocation));
		} catch (SQLException e) {
			if (e.getErrorCode() != 30000) {
				throw new SetUpException(e);
			}
			// } catch (IOException e) {
			// throw new SetUpException(e);
		}

		String sqlCall = "CALL SQLJ.INSTALL_JAR ('" + dbLocation
				+ "','groupconcat',0)";

		try (PreparedStatement stmt = con.prepareStatement(sqlGroupConcat);
				PreparedStatement stmtCall = con.prepareCall(sqlCall);) {
			stmt.executeUpdate();
			stmtCall.executeUpdate();
			Files.delete(Paths.get(dbLocation));
		} catch (SQLException e) {
			if (e.getErrorCode() != 30000) {
				throw new SetUpException(e.getErrorCode()
						+ ": Function Group_Concat could not be set up: "
						+ e.getMessage(), e);
			}
		} catch (IOException e) {
			throw new SetUpException(
					"Function Group_Concat could not be set up: "
							+ e.getMessage(), e);
		}
	}
}
