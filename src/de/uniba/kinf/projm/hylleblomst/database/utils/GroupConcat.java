package de.uniba.kinf.projm.hylleblomst.database.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Simon
 *
 */
public final class GroupConcat {
	/**
	 * This function returns a concatenation of the given Strings separated by
	 * the given separator
	 * 
	 * @param separator
	 * @param arguments
	 * @return A String in the format "arg0, arg1, ..., argN"
	 */
	public static String groupConcat(String separator, String... arguments) {
		StringBuilder result = new StringBuilder("");
		for (String arg : arguments) {
			result.append(arg + separator);
		}
		result.delete(result.length() - separator.length(), result.length());
		return result.toString();
	}

	public static void main(String[] args) {
		try (Connection con = DriverManager
				.getConnection("jdbc:derby:./db/MyDB;user=admin;password=r+l=j")) {

			PreparedStatement stmt = con
					.prepareStatement("SELECT HYLLEBLOMST.GROUP_CONCAT (', ',QuellenName,FakultaetenNorm) FROM HYLLEBLOMST.quellen, HYLLEBLOMST.fakultaeten WHERE Hylleblomst.quellen.quellenID<=3 AND Hylleblomst.fakultaeten.fakultaetenID<=3");
			ResultSet string = stmt.executeQuery();
			for (; string.next();) {
				System.out.println(string.getString(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
