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

	int inputCounter;

	/**
	 * Array stores all input fields of the graphical user interface.
	 */
	Object[] inputFields;

	public SearchController(int inputCounter) {
		if (inputCounter <= 0) {
			throw new IllegalArgumentException(
					"Anzahl der Eingabefelder kann nicht 0 oder kleiner sein, ist aber: "
							+ inputCounter);
		}
		this.inputCounter = inputCounter;
		this.inputSearchFKey = generateSearchFieldKeyArray();
	}

	List<QueryRequest> prepareInputForSearch(Object[] inputValues,
			int[] inputSourceKey) {
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
				// TODO Fehler werfen falls Eingabetyp nicht gefunden wurde
				// throw new
				// RuntimeException("Die Sucheingabe konnte nicht verarbeitet werden.");
			}
		}
		return requestList;
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
