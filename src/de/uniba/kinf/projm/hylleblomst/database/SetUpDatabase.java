package de.uniba.kinf.projm.hylleblomst.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.keys.UserKeys;

public class SetUpDatabase {

	public void run(File file) throws Exception {
		if (!file.isDirectory()) {
			throw new Exception("No directory");
		}

		String dbURL = "jdbc:derby:" + file.getAbsolutePath() + "/MyDB"
				+ "; create=true; user=" + UserKeys.adminUser + "; password="
				+ UserKeys.adminPassword;

		PreparedStatement stmt;
		try (Connection con = DriverManager.getConnection(dbURL)) {

			// Create users
			stmt = con
					.prepareStatement("CALL SYSCS_UTIL.SYSCS_CREATE_USER(?, ?)");
			stmt.setString(1, UserKeys.adminUser);
			stmt.setString(2, UserKeys.adminPassword);
			stmt.executeUpdate();
			stmt.setString(1, UserKeys.guestUser);
			stmt.setString(2, UserKeys.guestPassword);
			stmt.executeUpdate();

			// Set access
			stmt = con
					.prepareStatement("CALL SYSCS_UTIL.SYSCS_SET_USER_ACCESS (?, ?)");
			stmt.setString(1, UserKeys.adminUser);
			stmt.setString(2, "FULLACCESS");
			stmt.executeUpdate();
			stmt.setString(1, UserKeys.guestUser);
			stmt.setString(2, "READONLYACCESS");

			// Set no access as default
			stmt = con
					.prepareStatement("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.defaultConnectionMode','readOnlyAccess')");
			stmt.executeUpdate();

			// TODO possible ressource leak
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
