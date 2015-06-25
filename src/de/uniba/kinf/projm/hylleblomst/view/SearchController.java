package de.uniba.kinf.projm.hylleblomst.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.logic.QueriesImpl;
import de.uniba.kinf.projm.hylleblomst.logic.QueryRequestImpl;
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

	ViewController view;

	public SearchController(ViewController view) {
		this.view = view;
		this.inputCounter = view.getInputFieldCounter();
		this.inputSearchFKey = generateSearchFieldKeyArray();
		this.querieImpl = new QueriesImpl();
	}

	void executeSearch(Object[] inputValues, int[] inputSourceKey) {
		if (inputSearchFKey == null || inputSearchFKey.length == 0
				|| inputSourceKey == null || inputSourceKey.length == 0) {
			throw new IllegalArgumentException(
					"Die Liste mit Eingabefeldern ist leer oder hat keinen Wert.");
		}

		// neue Rückgabeliste anlegen
		List<QueryRequestImpl> requestList = new ArrayList<QueryRequestImpl>();
		// über alle Eingabefelder iterieren
		for (int i = 0; i < inputValues.length; i++) {
			// checken, welcher Typ von Eingabefeld das ist und entsprechend
			// behandeln
			if (inputValues[i] instanceof String && !"".equals(inputValues[i])
					&& inputValues[i] != null) {
				QueryRequestImpl tmpReq = new QueryRequestImpl(
						inputSearchFKey[i], inputValues[i], inputSourceKey[i]);
				requestList.add(tmpReq);
			} else if (inputValues[i] instanceof Boolean
					&& (Boolean) inputValues[i] == true) {
				QueryRequestImpl tmpReq = new QueryRequestImpl(
						inputSearchFKey[i], inputValues[i], inputSourceKey[i]);
				requestList.add(tmpReq);
			} else if (inputValues[i] instanceof Integer) {
				QueryRequestImpl tmpReq = new QueryRequestImpl(
						inputSearchFKey[i], inputValues[i], inputSourceKey[i]);
				requestList.add(tmpReq);
			} else if (inputValues[i] instanceof int[]) {

			}
		}

		try {
			view.setInfoTextExtendedSearch(requestList);
			// TODO nicht bei leerer Liste machen
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
		sfkArray[6] = SearchFieldKeys.ADLIG;
		sfkArray[7] = SearchFieldKeys.JESUIT;
		sfkArray[8] = SearchFieldKeys.WIRTSCHAFTSLAGE;
		sfkArray[9] = SearchFieldKeys.ORT;
		sfkArray[10] = SearchFieldKeys.FACH;
		sfkArray[11] = SearchFieldKeys.FAKULTAETEN;
		sfkArray[12] = SearchFieldKeys.SEMINAR;
		sfkArray[13] = SearchFieldKeys.GRADUIERT;
		sfkArray[14] = SearchFieldKeys.STUDIENJAHR;
		sfkArray[15] = SearchFieldKeys.EINSCHREIBEDATUM;
		sfkArray[16] = SearchFieldKeys.ZUSAETZE;
		sfkArray[17] = SearchFieldKeys.FUNDORTE;
		sfkArray[18] = SearchFieldKeys.ANMERKUNGEN;
		sfkArray[19] = SearchFieldKeys.NUMMER;
		sfkArray[20] = SearchFieldKeys.SEITE_ORIGINALE;
		sfkArray[21] = SearchFieldKeys.NUMMER_HESS;

		return sfkArray;
	}
}
