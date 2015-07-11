package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

import de.uniba.kinf.projm.hylleblomst.keys.DBUserKeys;
import de.uniba.kinf.projm.hylleblomst.keys.DatabaseKeys;
import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logic.UserQuery;

/**
 * Implementation of {@link SearchInitiator}
 * 
 * @author Johannes
 *
 */
public class SearchInitiatorImpl implements SearchInitiator {
	// DBAccess db = new DBAccess(DBUserKeys.dbURL, DBUserKeys.guestUser2,
	// DBUserKeys.guestPassword2);
	SQLBuilder sqlBuilder;
	private String dbKey;
	private String user;
	private String password;

	public SearchInitiatorImpl(DatabaseKeys dbKey) {
		setDbKey(dbKey);
		user = DBUserKeys.guestUser2;
		password = DBUserKeys.guestPassword2;
	}

	@Override
	public void setDbKey(DatabaseKeys dbKey) {
		if (dbKey != null) {
			this.dbKey = dbKey.dbURL;
		} else {
			throw new InputMismatchException("Der Pfad zur Datenbank darf nicht null sein.");
		}
	}

	@Override
	public void setUser(String user) {
		if (user != null) {
			this.user = user;
		} else {
			throw new InputMismatchException("Der Nutzer*innenname für die Datenbank darf nicht null sein.");
		}
	}

	@Override
	public void setPassword(String password) {
		if (password != null) {
			this.password = password;
		} else {
			throw new InputMismatchException("Das Passwort für die Datenbank darf nicht null sein.");
		}
	}

	@Override
	public CachedRowSet search(Collection<UserQuery> userQuery) throws SQLException {
		if (!(userQuery == null || userQuery.isEmpty())) {
			sqlBuilder = new SQLBuilder(userQuery);
			return startQuery(sqlBuilder.getSQLStatement(), sqlBuilder.getInputs());
		} else {
			throw new InputMismatchException("Die übergebene Collection ist fehlerhaft (null oder leer)");
		}
	}

	@Override
	public CachedRowSet searchPersonOrNotation(UserQuery userQuery) throws SQLException {
		if (userQuery != null) {
			sqlBuilder = new SQLBuilder(userQuery);
			return startQuery(sqlBuilder.getSQLStatement(), sqlBuilder.getInputs());
		} else {
			throw new InputMismatchException("Die übergebene UserQuery ist fehlerhaft (null)");
		}
	}

	private CachedRowSet startQuery(String sqlStatement, List<Object> inputs) throws SQLException {
		CachedRowSet crs = new CachedRowSetImpl();
		try (Connection con = DriverManager.getConnection(dbKey, user, password);
				PreparedStatement stmt = con.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);) {
			int parameterIndex = 1;
			if (inputs != null) {
				for (Object input : inputs) {
					// if (input instanceof String) {
					stmt.setString(parameterIndex, (String) input);
					// } else if (input instanceof Integer) {
					// stmt.setInt(parameterIndex, (Integer) input);
					//
					// }
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
