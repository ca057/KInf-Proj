package testing.simonsTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Formulate some statements to interact with Derby-database
 * 
 * @author Simon
 *
 */
public class TestDB {

	public static void main(String[] args) {
		String dbURL = "jdbc:derby:./db/MyDB;create=true";
		String user = "admin";
		String password = "password";

		ResultSet rs = null;

		// try (Connection con = DriverManager
		// .getConnection(dbURL, user, password);
		// Statement stmt = con.createStatement(
		// ResultSet.TYPE_SCROLL_INSENSITIVE,
		// ResultSet.CONCUR_READ_ONLY);
		//
		// ) {
		// System.out.println("CREATE SCHEMA test: "
		// + !stmt.execute("CREATE SCHEMA test"));
		// System.out
		// .println("CREATE TABLE test.tableOne(id integer PRIMARY KEY NOT NULL, name varchar(40) NOT NULL): "
		// +
		// !stmt.execute("CREATE TABLE test.tableOne(id integer PRIMARY KEY NOT NULL, name varchar(40) NOT NULL)"));
		// System.out
		// .println("INSERT into test.tableOne values(1, 'Germany'): "
		// + !stmt.execute("INSERT into test.tableOne values(1, 'Germany')"));
		// System.out.println("DROP TABLE test.tableOne: "
		// + !stmt.execute("DROP TABLE test.tableOne"));
		// System.out.println("DROP SCHEMA test Restrict: "
		// + !stmt.execute("DROP SCHEMA test Restrict"));
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		System.out
				.println("============================================================");
		System.out.println("TestFullAccess");
		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {
			System.out.println("con.getAutoCommit(): " + con.getAutoCommit());
			if (con != null) {
				System.out.println("Connection to database is established");
			}

			String sql = "INSERT INTO test.tableONE values(" + 14
					+ ", 'Malta')";
			stmt.execute(sql);

			rs = stmt.executeQuery("SELECT id, name FROM test.tableOne");

			con.setAutoCommit(false);
			// System.out.println("con.getAutoCommit(): " +
			// con.getAutoCommit());
			// System.out.println(rs);
			for (; rs.next();) {
				System.out
						.println(rs.getInt("id") + " " + rs.getString("name"));
			}

			con.setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out
				.println("============================================================");
		System.out.println("TestReadOnlyAccess");
		dbURL = "jdbc:derby:./db/MyDB";
		user = "guest";
		password = "guest";

		rs = null;

		try (Connection con = DriverManager
				.getConnection(dbURL, user, password);
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {
			// System.out.println("con.getAutoCommit(): " +
			// con.getAutoCommit());
			if (con != null) {
				System.out.println("Connection to database is established");
			}

			String sql = "INSERT INTO test.tableONE values(" + 4564
					+ ", 'Malta')";
			stmt.execute(sql);

			rs = stmt.executeQuery("SELECT id, name FROM test.tableOne");

			con.setAutoCommit(false);
			// System.out.println("con.getAutoCommit(): " +
			// con.getAutoCommit());
			// System.out.println(rs);

			for (; rs.next();) {
				System.out
						.println(rs.getInt("id") + " " + rs.getString("name"));
			}

			con.setAutoCommit(true);

		} catch (SQLException e) {
			System.out.println("Operation failed: " + e.getMessage());
		}
	}
}