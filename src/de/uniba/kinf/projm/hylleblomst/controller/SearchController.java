package de.uniba.kinf.projm.hylleblomst.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Observable;

import de.uniba.kinf.projm.hylleblomst.exceptions.ViewException;
import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.logic.UserQueries;
import de.uniba.kinf.projm.hylleblomst.logicImpl.UserQueriesImpl;

/**
 * Controller for executing the search.
 *
 */
public class SearchController implements ControllerInterface {

	/**
	 * Array stores for every input field the corresponding search field key.
	 */
	private SearchFieldKeys[] inputSearchFKey;

	/**
	 * Stores the number of input fields for setting up the
	 * {@code inputSearchFKey} with a correct length
	 */
	private int inputCounter;

	/**
	 * SearchInitiatorImpl executes the search.
	 */
	private Model model;

	/**
	 * 
	 * @param inputFieldCounter
	 * @param model
	 */
	public SearchController(int inputFieldCounter, Model model) {
		if (inputFieldCounter == 0 || model == null) {
			throw new InputMismatchException("Die Anzahl der Eingabefelder ist 0 oder das Model hat keinen Wert.");
		}
		this.model = model;
		inputCounter = inputFieldCounter;
		inputSearchFKey = generateSearchFieldKeyArray();
	}

	/**
	 * 
	 * @param inputValues
	 * @param inputSourceKey
	 * @throws ViewException
	 */
	boolean executeSearch(String[] inputValues, int[] inputSourceKey, boolean isOr, boolean isOpenedSearch)
			throws ViewException {
		if (inputSearchFKey == null || inputSearchFKey.length == 0 || inputSourceKey == null
				|| inputSourceKey.length == 0) {
			throw new IllegalArgumentException("Die Liste mit Eingabefeldern ist leer oder hat keinen Wert.");
		}

		List<UserQueries> requestList = new ArrayList<UserQueries>();
		for (int i = 0; i < inputValues.length; i++) {
			if (!inputValues[i].isEmpty() && !"false".equals(inputValues[i]) && !"yyyy-mm-dd".equals(inputValues[i])) {
				UserQueriesImpl tmpReq = new UserQueriesImpl(inputSearchFKey[i], inputValues[i], inputSourceKey[i],
						isOr, isOpenedSearch);
				requestList.add(tmpReq);
			}
		}

		if (requestList.isEmpty()) {
			return false;
		}
		try {
			model.search(requestList);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ViewException("Ein Fehler bei der Suche ist aufgetreten:\n" + e.getMessage());
		}
	}

	/**
	 * 
	 * @param id
	 * @throws ViewException
	 */
	public boolean startSinglePersonSearch(String id) throws ViewException {
		if (id == null || id.isEmpty()) {
			throw new InputMismatchException(
					"Übergebene ID ist leer oder hat keinen Wert. Personendetails können nicht gesucht werden.");
		}
		try {
			model.searchPerson(id);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ViewException("Ein Fehler bei der Suche nach Person mit ID " + id + " ist aufgetreten.");
		}
	}

	/**
	 * 
	 * @return
	 */
	private SearchFieldKeys[] generateSearchFieldKeyArray() {
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
		sfkArray[14] = SearchFieldKeys.STUDIENJAHR_VON;
		sfkArray[15] = SearchFieldKeys.STUDIENJAHR_BIS;
		sfkArray[16] = SearchFieldKeys.EINSCHREIBEDATUM_VON;
		sfkArray[17] = SearchFieldKeys.EINSCHREIBEDATUM_BIS;
		sfkArray[18] = SearchFieldKeys.ZUSAETZE;
		sfkArray[19] = SearchFieldKeys.FUNDORTE;
		sfkArray[20] = SearchFieldKeys.ANMERKUNGEN;
		sfkArray[21] = SearchFieldKeys.NUMMER;
		sfkArray[22] = SearchFieldKeys.SEITE_ORIGINALE;
		sfkArray[23] = SearchFieldKeys.NUMMER_HESS;

		return sfkArray;
	}

	/**
	 * Not needed for the implementation of this controller.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {

	}
}
