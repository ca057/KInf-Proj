package de.uniba.kinf.projm.hylleblomst.logic;

public class MainLogic {
	public static void main(String[] args) {
		QueriesImpl test = new QueriesImpl();
		// ArrayList<QueryRequest> qr = new ArrayList<>();
		// test.setDatabase("jdbc:derby:db/MyDB", "admin", "password");
		test.getColumnName("PERSON");
		// test.search(qr);
		// test.extendedSearch(columns, input, source);
		// test.fullTextSearch("Johannes");
	}
}
