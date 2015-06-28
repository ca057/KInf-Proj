package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.SQLException;
import java.util.LinkedList;

import de.uniba.kinf.projm.hylleblomst.logic.SearchFieldKeys;

public class MainLogic {
	public static void main(String[] args) throws SQLException {
		QueriesImpl test = new QueriesImpl();
		// ArrayList<QueryRequest> qr = new ArrayList<>();
		// test.setDatabase("jdbc:derby:db/MyDB", "admin", "password");
		QueryRequestImpl qr = new QueryRequestImpl(SearchFieldKeys.VORNAME, "Johammes", 1);
		LinkedList<QueryRequestImpl> col = new LinkedList<QueryRequestImpl>();
		col.add(qr);
		// qr.setSearchField(SearchFieldKeys.ADELIG);
		test.search(col);
	}
}
