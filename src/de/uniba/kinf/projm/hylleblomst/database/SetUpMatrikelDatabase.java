package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.keys.UserKeys;

/**
 * This class has the sole purpose of setting up a database with all tables
 * required to import project-specific data.
 * 
 * @author Simon
 *
 */
public class SetUpMatrikelDatabase {

	public static void main(String[] args) {
		try {
			new SetUpMatrikelDatabase().run();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SetUpMatrikelDatabase() {
	}

	public boolean run() throws SQLException {
		try (Connection con = DriverManager.getConnection(UserKeys.dbURL,
				UserKeys.adminUser, UserKeys.adminPassword)) {

		} catch (SQLException e) {
			throw new SQLException("Database could not be build: ", e);
		}

		return false;
	}
}
