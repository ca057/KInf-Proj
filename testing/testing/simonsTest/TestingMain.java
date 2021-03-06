package testing.simonsTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestingMain {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.exit(0);
		}

		try (Connection con = DriverManager.getConnection("jdbc:derby:" + args[0] + "/MyDB;user=admin;password=r+l=j");
				PreparedStatement stmt = con.prepareStatement(
						"SELECT HYLLEBLOMST.AGGREGATE_VARCHAR(QuellenName) FROM HYLLEBLOMST.QUELLEN");) {

			ResultSet rs = stmt.executeQuery();
			con.setAutoCommit(false);
			for (; rs.next();) {
				System.out.println(rs.getString(1));
			}
			con.setAutoCommit(true);
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
