package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.keys.DBUserKeys;

/**
 * This classes allows the creation of a database in a specified location and
 * creates standard users. By intention, the default access should be set to
 * READ_ONLY, but Derby seemed to have other plans. In later development, this
 * would be an opportunity for improvement.
 * 
 * @author Simon
 *
 */
public class SetUpDatabase {
	/**
	 * Set up the database and invoke subsequent methods for the standard
	 * configuration.
	 * 
	 * @param con
	 *            The current connection to a database.
	 * @throws SetUpException
	 *             If an error occurs during the SetUp, e.g. the location could
	 *             not be accessed due to limited user rights.
	 */
	public void setUpDatabase(Connection con) throws SetUpException {

		try {
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

	private void createUsers(Connection con) throws SetUpException {
		try (PreparedStatement stmt = con
				.prepareCall("CALL SYSCS_UTIL.SYSCS_CREATE_USER(?, ?)");) {
			createAdminUser(stmt);
			createGuestUser(stmt);
		} catch (SQLException e) {
			throw new SetUpException(e.getMessage());
		}
	}

	private void createAdminUser(PreparedStatement stmt) throws SetUpException {
		try {
			stmt.setString(1, DBUserKeys.adminUser);
			stmt.setString(2, DBUserKeys.adminPassword);
			stmt.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() != 30000) {
				throw new SetUpException(e.getErrorCode()
						+ ": Admin user could not be created: "
						+ e.getMessage());
			}
		}
	}

	private void createGuestUser(PreparedStatement stmt) throws SetUpException {
		try {
			stmt.setString(1, DBUserKeys.guestUser);
			stmt.setString(2, DBUserKeys.guestPassword);
			stmt.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() != 30000) {
				throw new SetUpException("Admin user could not be created: "
						+ e.getMessage());
			}
		}
	}

	private void setUserAccess(Connection con) throws SetUpException {
		try (PreparedStatement stmt = con
				.prepareCall("CALL SYSCS_UTIL.SYSCS_SET_USER_ACCESS (?, ?)");) {
			setAdminUser(stmt);
			setGuestUser(stmt);
		} catch (SQLException e) {
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
			stmt.setString(1, DBUserKeys.guestUser);
			stmt.setString(2, "READONLYACCESS");
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException("Access for guest user could not be set: "
					+ e.getMessage());
		}
	}

	private void setDefaultAccess(Connection con) throws SetUpException {
		try (PreparedStatement stmt = con
				.prepareCall("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.defaultConnectionMode','readOnlyAccess')");) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException(e.getSQLState() + "\n" + e.getMessage());
		}
	}
}
