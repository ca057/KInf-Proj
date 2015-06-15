package de.uniba.kinf.projm.hylleblomst.logic;

public class MainLogic {
	public static void main(String[] args) {
		QueriesImpl test = new QueriesImpl();
		// ArrayList<QueryRequest> qr = new ArrayList<>();
		// test.setDatabase("jdbc:derby:db/MyDB", "admin", "password");
		QueryRequest qr = new QueryRequest(SearchFieldKeys.ADELIG, "test", 1);
		qr.searchFieldKeyToDatabaseData(SearchFieldKeys.ADELIG);
		System.out.println(qr.getTable() + ", " + qr.getColumn());
	}
}
