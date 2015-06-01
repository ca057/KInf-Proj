package testing.simonsTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Formulate some statements to interact with Derby-database
 * 
 * @author Simon
 *
 */
public class TestDB {

	void test() {
		String dbURL = "jdbc:derby:./db/MyTestingDB";
		String user = "test";
		String password = "test";

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement();) {
			if (con != null) {
				System.out.println("Connected to DB");
			}
			String sql = "INSERT into PRESENTATION.SEMINAR_NORM values (3, 'In vino est veritas')";

			System.out.println(sql);
			String table = "Presentation.seminar_norm";
			int id = 6;

			// Muy importante: varchar attributes müssen auch mit '' umgeben
			// werden
			String value1 = "\'In vino\'";

			sql = "INSERT INTO " + table + " values (" + id + ", " + value1
					+ ")";

			System.out.println(sql);
			// Test
			List<String> tmp = new LinkedList<>();
			tmp.add("In vino");
			sql = composeSQLInsertStmt("Presentation.seminar_norm", 6, tmp);

			stmt.execute(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getSQLState() + "\n" + e.getMessage());
		}
	}

	String composeSQLInsertStmt(String table, int id, List<String> values) {
		String sql = "INSERT into " + table + " values (" + id + ", "
				+ composeValuesIntoString(values) + ")";
		System.out.println(sql);
		return sql;
	}

	private String composeValuesIntoString(List<String> values) {
		String valuelist = "";
		for (String string : values) {
			valuelist += "\'" + string + "\'" + ", ";
		}
		valuelist = valuelist.substring(0, valuelist.length() - 2);
		System.out.println(valuelist);
		return valuelist;
	}
}