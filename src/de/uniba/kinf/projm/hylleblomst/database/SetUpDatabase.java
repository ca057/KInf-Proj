package de.uniba.kinf.projm.hylleblomst.database;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.keys.DBUserKeys;

/**
 * @author Simon
 *
 */
public class SetUpDatabase {

	public void run(String dbURL) throws SetUpException {
		if (!Files.isDirectory(Paths.get(dbURL))) {
			throw new SetUpException("Path is no directory");
		}

		// StringBuilder urlStatement = new StringBuilder(dbURL);
		// urlStatement.append("; create=true");
		// urlStatement.append("; user=");
		// urlStatement.append(DBUserKeys.adminUser);
		// urlStatement.append("; password=");
		// urlStatement.append(DBUserKeys.adminPassword);

		try (Connection con = DriverManager.getConnection(dbURL,
				DBUserKeys.adminUser, DBUserKeys.adminPassword)) {
			// Create users
			createUsers(con);

			// Set access
			setUserAccess(con);

			// Set no access as default
			// setDefaultAccess(con);

		} catch (SetUpException e) {
			throw new SetUpException(e.getMessage());
		} catch (SQLException e1) {
			throw new SetUpException("Connection refused: " + e1.getMessage());
		}
	}

	private void createUsers(Connection con) throws SetUpException {
		try {
			PreparedStatement stmt;
			stmt = con
					.prepareStatement("CALL SYSCS_UTIL.SYSCS_CREATE_USER(?, ?)");
			createAdminUser(stmt);
			createGuestUser(stmt);
		} catch (SQLException | SetUpException e) {
			throw new SetUpException(e.getMessage());
		}
	}

	private void createAdminUser(PreparedStatement stmt) throws SetUpException {
		try {
			stmt.setString(1, DBUserKeys.adminUser);
			stmt.setString(2, DBUserKeys.adminPassword);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException("Admin user could not be created: "
					+ e.getMessage());
		}
	}

	private void createGuestUser(PreparedStatement stmt) throws SetUpException {
		try {
			stmt.setString(1, DBUserKeys.guestUser2);
			stmt.setString(2, DBUserKeys.guestPassword2);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException("Admin user could not be created: "
					+ e.getMessage());
		}
	}

	private void setUserAccess(Connection con) throws SetUpException {
		try {
			PreparedStatement stmt;
			stmt = con
					.prepareStatement("CALL SYSCS_UTIL.SYSCS_SET_USER_ACCESS (?, ?)");
			setAdminUser(stmt);
			setGuestUser(stmt);
		} catch (SQLException | SetUpException e) {
			throw new SetUpException(e.getMessage());
		}
	}

	private void setAdminUser(PreparedStatement stmt) throws SetUpException {
		try {
			stmt.setString(1, DBUserKeys.adminUser);
			stmt.setString(2, "FULLACCESS");
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException("Access for admin user could not be set: "
					+ e.getMessage());
		}
	}

	private void setGuestUser(PreparedStatement stmt) throws SetUpException {
		try {
			stmt.setString(1, DBUserKeys.guestUser2);
			stmt.setString(2, "READONLYACCESS");
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException("Access for guest user could not be set: "
					+ e.getMessage());
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
}
