package de.uniba.kinf.projm.hylleblomst.database;

import java.util.List;

public interface Import {

	public void importData(List<String[]> rows) throws Exception;

}
