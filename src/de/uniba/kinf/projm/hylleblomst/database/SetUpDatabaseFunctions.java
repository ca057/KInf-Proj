package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;

public class SetUpDatabaseFunctions {

	public void run(String dbURL, String user, String password)
			throws SetUpException {
		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				PreparedStatement stmt = con
						.prepareStatement("CREATE SCHEMA SQL_UTIL");) {
			stmt.executeUpdate();

			setUpGroupConcat(con);

		} catch (SQLException e) {
			throw new SetUpException(e.getErrorCode()
					+ ": Functions could not be set up: " + e.getMessage(), e);
		}
	}

	void setUpGroupConcat(Connection con) throws SetUpException {
		// TODO
		String sqlGroupConcat = "CREATE FUNCTION SQL_UTIL.GROUP_CONCAT " + "()";

		try (PreparedStatement stmt = con.prepareStatement(sqlGroupConcat);) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException(e.getErrorCode()
					+ ": Function Group_Concat could not be set up: "
					+ e.getMessage(), e);
		}
	}
}
