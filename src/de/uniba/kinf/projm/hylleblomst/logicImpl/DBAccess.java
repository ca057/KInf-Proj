package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

/**
 * @author Johannes
 *
 */
public class DBAccess {
	private String dbURL;
	private String user;
	private String password;

	/**
	 * @param dbURL
	 * @param user
	 * @param password
	 */
	public DBAccess(String dbURL, String user, String password) {
		this.dbURL = dbURL;
		this.user = user;
		this.password = password;
	}

	void setUser(String user) {
		this.user = user;
	}

	void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param sqlStatement
	 * @param inputs
	 * @return
	 * @throws SQLException
	 */
	public CachedRowSet startQuery(String sqlStatement, List<String> inputs) throws SQLException {
		CachedRowSet crs = new CachedRowSetImpl();
		try (Connection con = DriverManager.getConnection(dbURL, user, password);
				PreparedStatement stmt = con.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {
			int parameterIndex = 1;
			if (inputs != null) {
				for (String input : inputs) {
					stmt.setString(parameterIndex, input);
					parameterIndex++;
				}
			}

			con.setAutoCommit(false);
			crs.populate(stmt.executeQuery());
			con.setAutoCommit(true);

		}
		return crs;
	}
}