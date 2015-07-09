package de.uniba.kinf.projm.hylleblomst.keys;

import java.io.File;

/**
 * @author Simon
 *
 */
public class DatabaseKeys {

	public final String dbURL;

	public DatabaseKeys(File file) {
		this.dbURL = "jdbc:derby:" + file.getAbsolutePath() + "/MyDB";
	}

}
