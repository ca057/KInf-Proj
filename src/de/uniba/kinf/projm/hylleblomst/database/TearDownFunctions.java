package de.uniba.kinf.projm.hylleblomst.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.keys.TableNameKeys;

public class TearDownFunctions {

	public boolean tearDownFunctions(Connection con) throws SetUpException {
		tearDownGroupConcat(con);

		return true;
	}

	private void tearDownGroupConcat(Connection con) throws SetUpException {
		StringBuilder sqlConcatTable = new StringBuilder(
				"DROP DERBY AGGREGATE ");
		sqlConcatTable.append(TableNameKeys.SCHEMA_NAME);
		sqlConcatTable.append(".AGGREGATE_VARCHAR RESTRICT");

		try (PreparedStatement stmt = con.prepareStatement(sqlConcatTable
				.toString())) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SetUpException(e.getErrorCode()
					+ ": Group_Concat could not be removed: " + e.getMessage());
		}

	}
}
