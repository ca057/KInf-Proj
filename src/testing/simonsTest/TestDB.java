package testing.simonsTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Formulate some statements to interact with Derby-database
 * 
 * @author Simon
 *
 */
public class TestDB {
	void test() {
		String dbURL = "jdbc:derby:C:/Users/Simon/git/KInf-Projekt/db/MyDB;create=true";
		String user = "test";
		String password = "test";

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement();) {
			if (con != null) {
				System.out.println("Connected to DB");
			}
			// stmt.execute("CREATE SCHEMA table_test");
			stmt.execute("CREATE TABLE table_test.test1 (id integer PRIMARY KEY NOT NULL, jahr integer NOT NULL)");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
