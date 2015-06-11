package de.uniba.kinf.projm.hylleblomst.database;

import java.util.List;

public interface ImportDatabase {

	public void importData(List<String[]> rows) throws Exception;

}
