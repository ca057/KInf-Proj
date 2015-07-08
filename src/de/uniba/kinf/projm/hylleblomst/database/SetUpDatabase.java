package de.uniba.kinf.projm.hylleblomst.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.keys.DBUserKeys;

public class SetUpDatabase {

	public void run(File file) throws Exception {
		if (!file.isDirectory()) {
			throw new Exception("No directory");
		}

		String dbURL = "jdbc:derby:" + file.getAbsolutePath() + "/MyDB"
				+ "; create=true; user=" + DBUserKeys.adminUser + "; password="
				+ DBUserKeys.adminPassword;

		try (Connection con = DriverManager.getConnection(dbURL)) {
			// Create users
			createUsers(con);

			// Set access
			setUserAccess(con);

			// Set no access as default
			// setDefaultAccess(con);

		} catch (SetUpException e) {
			throw new SetUpException(e.getMessage());
		}
	}

	private void setDefaultAccess(Connection con) throws SetUpException {
		try {
			PreparedStatement stmt;
			stmt = con
					.prepareStatement("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.defaultConnectionMode','readOnlyAccess')");
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException(e.getSQLState() + "\n" + e.getMessage());
		}
	}

	private void setUserAccess(Connection con) throws SetUpException {
		try {
			PreparedStatement stmt;
			stmt = con
					.prepareStatement("CALL SYSCS_UTIL.SYSCS_SET_USER_ACCESS (?, ?)");
			stmt.setString(1, DBUserKeys.adminUser);
			stmt.setString(2, "FULLACCESS");
			stmt.executeUpdate();
			stmt.setString(1, DBUserKeys.guestUser2);
			stmt.setString(2, "READONLYACCESS");
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException(e.getSQLState() + "\n" + e.getMessage());
		}
	}

	private void createUsers(Connection con) throws SetUpException {
		try {
			PreparedStatement stmt;
			stmt = con
					.prepareStatement("CALL SYSCS_UTIL.SYSCS_CREATE_USER(?, ?)");
			stmt.setString(1, DBUserKeys.adminUser);
			stmt.setString(2, DBUserKeys.adminPassword);
			stmt.executeUpdate();
			stmt.setString(1, DBUserKeys.guestUser2);
			stmt.setString(2, DBUserKeys.guestPassword2);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException(e.getSQLState() + "\n" + e.getMessage());
		}
	}
}
