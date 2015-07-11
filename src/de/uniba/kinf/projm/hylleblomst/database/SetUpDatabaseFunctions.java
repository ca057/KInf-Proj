package de.uniba.kinf.projm.hylleblomst.database;

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
		String sqlGroupConcat = "CREATE FUNCTION HYLLEBLOMST.GROUP_CONCAT ( SEPARATOR CHAR, ARGS VARCHAR(255) ... ) RETURNS VARCHAR(2000) PARAMETER STYLE DERBY NO SQL LANGUAGE JAVA EXTERNAL NAME 'de.uniba.kinf.projm.hylleblomst.database.utils.GroupConcat.groupConcat'";

		try (PreparedStatement stmt = con.prepareStatement(sqlGroupConcat);) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException(e.getErrorCode()
					+ ": Function Group_Concat could not be set up: "
					+ e.getMessage(), e);
		}
	}
}
