package de.uniba.kinf.projm.hylleblomst.keys;

/**
 * We are aware that saving the user names and passwords directly with the
 * source-code is neither best practice nor recommendable. Yet advanced security
 * measures such as hashed passwords or public / private keys did not seem
 * worthwhile for the purposes of the project.
 * 
 * @author Simon
 *
 */
public class DBUserKeys {
	public final static String dbURL = "jdbc:derby:./db/MyDB";

	public final static String guestUser = "guest";
	public final static String guestPassword = "ned-stark";

	public final static String guestUser2 = "admin";
	public final static String guestPassword2 = "r+l=j";

	public final static String adminUser = "admin";
	public final static String adminPassword = "r+l=j";
}
