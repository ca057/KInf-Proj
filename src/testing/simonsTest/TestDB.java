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
		String dbURL = "jdbc:derby:./db/MyDB;create=true";
		String user = "test";
		String password = "test";

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement();) {
			if (con != null) {
				System.out.println("Connected to DB");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}