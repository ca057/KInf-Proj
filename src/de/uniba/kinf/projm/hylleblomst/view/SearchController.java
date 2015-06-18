package de.uniba.kinf.projm.hylleblomst.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.logic.QueriesImpl;
import de.uniba.kinf.projm.hylleblomst.logic.QueryRequest;
import de.uniba.kinf.projm.hylleblomst.logic.SearchFieldKeys;

public class SearchController {

	/**
	 * Array stores for every input field the corresponding search field key.
	 */
	SearchFieldKeys[] inputSearchFKey;

	int inputCounter;

	/**
	 * QueriesImpl executes the search.
	 */
	private QueriesImpl querieImpl;

	View view;

	public SearchController(View view, int inputCounter) {
		if (inputCounter <= 0) {
			throw new IllegalArgumentException(
					"Anzahl der Eingabefelder kann nicht 0 oder kleiner sein, ist aber: "
							+ inputCounter);
		}
		this.view = view;
		this.inputCounter = inputCounter;
		this.inputSearchFKey = generateSearchFieldKeyArray();
		this.querieImpl = new QueriesImpl();
	}

	void prepareInputForSearch(Object[] inputValues, int[] inputSourceKey) {
		if (inputSearchFKey == null || inputSearchFKey.length == 0
				|| inputSourceKey == null || inputSourceKey.length == 0) {
			throw new IllegalArgumentException(
					"Die Liste mit Eingabefeldern ist leer oder hat keinen Wert.");
		}

		// neue Rückgabeliste anlegen
		List<QueryRequest> requestList = new ArrayList<QueryRequest>();
		// über alle Eingabefelder iterieren
		for (int i = 0; i < inputValues.length; i++) {
			// checken, welcher Typ von Eingabefeld das ist und entsprechend
			// behandeln
			if (inputValues[i] instanceof String && !"".equals(inputValues[i])
					&& inputValues[i] != null) {
				QueryRequest tmpReq = new QueryRequest(inputSearchFKey[i],
						inputValues[i], inputSourceKey[i]);
				requestList.add(tmpReq);
			} else {
				continue;
			}
		}

		try {
			view.setInfoTextExtendedSearch(requestList);
			querieImpl.search(requestList);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Fehler bei der Suche\n"
					+ e.getMessage());
		}
	}

	SearchFieldKeys[] generateSearchFieldKeyArray() {
		SearchFieldKeys[] sfkArray = new SearchFieldKeys[inputCounter];

		sfkArray[0] = SearchFieldKeys.ANREDE;
		sfkArray[1] = SearchFieldKeys.ANREDE;
		sfkArray[2] = SearchFieldKeys.TITEL;
		sfkArray[3] = SearchFieldKeys.TITEL;
		sfkArray[4] = SearchFieldKeys.VORNAME;
		sfkArray[5] = SearchFieldKeys.NACHNAME;

		return sfkArray;
	}
}
