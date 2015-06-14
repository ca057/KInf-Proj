package de.uniba.kinf.projm.hylleblomst.logic;

public class MainLogic {
	public static void main(String[] args) {
		QueriesImpl test = new QueriesImpl();
		test.setDatabase("jdbc:derby:db/MyDB", "admin", "password");

		// test.extendedSearch(columns, input, source);
		// test.fullTextSearch("Johannes");
	}
}
