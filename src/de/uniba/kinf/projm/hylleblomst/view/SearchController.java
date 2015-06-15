package de.uniba.kinf.projm.hylleblomst.view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextField;
import de.uniba.kinf.projm.hylleblomst.logic.QueryRequest;
import de.uniba.kinf.projm.hylleblomst.logic.SearchFieldKeys;

public class SearchController {

	/**
	 * The {@link Controller} the SearchController is associated with.
	 */
	Controller controller;

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

	public SearchController(Controller controller,
			SearchFieldKeys[] inputSearchFKey, Object[] inputFields,
			int[] inputSourceKey) {
		if (controller == null || inputSearchFKey == null
				|| inputFields == null || inputSourceKey == null) {
			throw new IllegalArgumentException(
					"Übergebener Controller oder Arrays mit Eingabefeldern haben keinen Wert (= null).");
		}
		this.controller = controller;
		this.inputSearchFKey = inputSearchFKey;
		this.inputFields = inputFields;
		this.inputSourceKey = inputSourceKey;
	}

	List<QueryRequest> prepareInputForSearch() {
		if (inputSearchFKey == null || inputSearchFKey.length == 0
				|| inputFields == null || inputFields.length == 0
				|| inputSourceKey == null || inputSourceKey.length == 0) {
			throw new IllegalArgumentException(
					"Die Liste mit Eingabefeldern ist leer oder hat keinen Wert.");
		}
		// neue Rückgabeliste anlegen
		List<QueryRequest> requestList = new ArrayList<QueryRequest>();
		// über alle Eingabefelder iterieren
		for (int i = 0; i < inputFields.length; i++) {
			// checken, welcher Typ von Eingabefeld das ist und entsprechend
			// behandeln
			if (inputFields[i] instanceof TextField) {
				QueryRequest tmpReq = new QueryRequest(inputSearchFKey[i],
						((TextField) inputFields[i]).getText(),
						inputSourceKey[i]);
				requestList.add(tmpReq);
			} else {
				// Fehler werfen falls Eingabetyp nicht gefunden wurde
				throw new RuntimeException(
						"Die Sucheingabe konnte nicht verarbeitet werden.");
			}
		}
		return requestList;
	}
}
