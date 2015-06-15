package de.uniba.kinf.projm.hylleblomst.view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextField;
import de.uniba.kinf.projm.hylleblomst.logic.QueryRequest;

public class SearchController {

	Controller controller;

	public SearchController(Controller controller) {
		if (controller == null) {
			throw new IllegalArgumentException(
					"Übergebener Controller hat keinen Wert (= null).");
		}
		this.controller = controller;
	}

	List<QueryRequest> prepareInputForSearch() {
		if (controller.getInputSearchFKey() == null
				|| controller.getInputSearchFKey().length == 0
				|| controller.getInputFields() == null
				|| controller.getInputFields().length == 0
				|| controller.getInputSourceKey() == null
				|| controller.getInputSourceKey().length == 0) {
			throw new IllegalArgumentException(
					"Die Liste mit Eingabefeldern ist leer oder hat keinen Wert.");
		}
		// neue Rückgabeliste anlegen
		List<QueryRequest> requestList = new ArrayList<QueryRequest>();
		// über alle Eingabefelder iterieren
		for (int i = 0; i < controller.getInputFields().length; i++) {
			// checken, welcher Typ von Eingabefeld das ist und entsprechend
			// behandeln
			if (controller.getInputFields()[i] instanceof TextField) {
				QueryRequest tmpReg = new QueryRequest(
						controller.getInputSearchFKey()[i],
						((TextField) controller.getInputFields()[i]).getText(),
						controller.getInputSourceKey()[i]);
				requestList.add(tmpReg);
			} else {
				// Fehler werfen falls Eingabetyp nicht gefunden wurde
				throw new RuntimeException(
						"Die Sucheingabe konnte nicht verarbeitet werden.");
			}
		}
		return requestList;
	}

}
