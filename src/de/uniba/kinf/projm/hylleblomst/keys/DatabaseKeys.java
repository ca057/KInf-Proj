package de.uniba.kinf.projm.hylleblomst.keys;

import java.io.File;

public class DatabaseKeys {

	public String dbURL;

	public void setDatabaseURL(File file) {
		dbURL = "jdbc:derby:" + file.getAbsolutePath() + "/MyDB";

	}
}
