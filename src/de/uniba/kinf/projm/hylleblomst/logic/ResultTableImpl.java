package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.ResultSet;

public class ResultTableImpl implements ResultTable {

	private ResultTable resultTable;

	public void setResultTable(ResultTable table) {
		if (table == null) {
			throw new IllegalArgumentException(
					"Tabelle ist null und kann nicht befüllt werden.");
		}
		resultTable = table;
	}

	@Override
	public void fillTable(ResultSet set) {
		// TODO null soll nicht ausgegeben werden, sondern leere Zelle
		// darstellen

	}

}
