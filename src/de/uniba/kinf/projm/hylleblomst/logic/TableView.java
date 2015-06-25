package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.ResultSet;

public interface TableView {
	void fillTable(ResultSet set);
}