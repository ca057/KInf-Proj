package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.SQLException;
import java.util.LinkedList;

public class MainLogic {
	public static void main(String[] args) throws SQLException {
		QueriesImpl test = new QueriesImpl();
		// ArrayList<QueryRequest> qr = new ArrayList<>();
		// test.setDatabase("jdbc:derby:db/MyDB", "admin", "password");
		QueryRequest qr = new QueryRequest(SearchFieldKeys.VORNAME,
				"Johammes", 1);
		LinkedList<QueryRequest> col = new LinkedList<QueryRequest>();
		col.add(qr);
		// qr.setSearchField(SearchFieldKeys.ADELIG);
		test.search(col);
	}
}
