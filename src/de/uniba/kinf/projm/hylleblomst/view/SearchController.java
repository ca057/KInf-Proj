package de.uniba.kinf.projm.hylleblomst.view;

import java.util.ArrayList;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.logic.QueryRequest;
import de.uniba.kinf.projm.hylleblomst.logic.SearchFieldKeys;

public class SearchController {

	/**
	 * Array stores for every input field the corresponding search field key.
	 */
	SearchFieldKeys[] inputSearchFKey;

	/**
	 * Array stores all input fields of the graphical user interface.
	 */
	Object[] inputFields;

	/**
	 * Array stores for every input field the corresponding source key.
	 */
	int[] inputSourceKey;

	public SearchController(SearchFieldKeys[] inputSearchFKey,
			int[] inputSourceKey) {
		if (inputSearchFKey == null || inputSourceKey == null) {
			throw new IllegalArgumentException(
					"Übergebener Controller oder Arrays mit Eingabefeldern haben keinen Wert (= null).");
		}
		this.inputSearchFKey = inputSearchFKey;
		this.inputSourceKey = inputSourceKey;
	}

	List<QueryRequest> prepareInputForSearch(Object[] inputValues) {
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
			if (inputValues[i] instanceof String && !"".equals(inputValues[i])) {
				QueryRequest tmpReq = new QueryRequest(inputSearchFKey[i],
						inputValues[i], inputSourceKey[i]);
				requestList.add(tmpReq);
			} else {
				continue;
				// TODO Fehler werfen falls Eingabetyp nicht gefunden wurde
				// throw new
				// RuntimeException("Die Sucheingabe konnte nicht verarbeitet werden.");
			}
		}
		return requestList;
	}
}
