package testing.simonsTest;

import de.uniba.kinf.projm.hylleblomst.database.ImportDatabaseImpl;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;

public class Main {
	public static void main(String[] args) throws ImportException {
		// String underscores = "this_has_many_underscores";
		// String noUnderscores = underscores.replace("_", "");
		//
		// System.out.println(underscores);
		// System.out.println(noUnderscores);
		//
		// String wow = String.format("This is %s and %s", "great", "top");
		//
		// System.out.println(wow);

		// String columnName = (tableName.replace("_", "") +
		// "ID").replace("hylleblomst.", "");
		// String getMaxIDSQL = String.format("SELECT max(%s) FROM %s",
		// columnName, tableName);
		//
		// System.out.println(getMaxIDSQL);

		String dbURL = "jdbc:derby:C:/Users/Simon/git/KInf-Projekt/db/MyDB";
		String user = "admin";
		String password = "password";

		ImportDatabaseImpl imports = new ImportDatabaseImpl(dbURL, user,
				password);

		// System.out.println(imports.getMaxID("hylleblomst.anrede_norm"));

		// try (Connection con = DriverManager
		// .getConnection(dbURL, user, password);
		// Statement stmt = con.createStatement(
		// ResultSet.TYPE_SCROLL_INSENSITIVE,
		// ResultSet.CONCUR_READ_ONLY)) {
		//
		// if (con != null)
		// System.out.println("Connection established: " + con);
		//
		// String tableName = "hylleblomst.anrede_norm";
		// String columnName = (tableName.replace("_", "") + "ID").replace(
		// "hylleblomst.", "");
		// String getMaxIdSql = String.format("SELECT max(%s) FROM %s",
		// columnName, tableName);
		// System.out.println(getMaxIdSql);
		// ResultSet rs = stmt.executeQuery(getMaxIdSql);
		//
		// con.setAutoCommit(false);
		//
		// rs.first();
		// int result = rs.getInt(1);
		// System.out.println(result);
		//
		// con.setAutoCommit(true);
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
