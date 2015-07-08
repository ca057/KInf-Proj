package de.uniba.kinf.projm.hylleblomst.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logic.UserQueries;
import de.uniba.kinf.projm.hylleblomst.logicImpl.UserQueriesImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller for executing the search.
 * 
 * @author ca
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

	private ObservableList<SearchFieldKeys> usedSearchFields = FXCollections.observableArrayList();

	/**
	 * SearchInitiatorImpl executes the search.
	 */
	private SearchInitiator searchInitiatorImpl;

	/**
	 * 
	 */
	private ViewController view;

	/**
	 * 
	 * @param view
	 */
	public SearchController(ViewController view, SearchInitiator searchInitiatorImpl) {
		this.view = view;
		this.inputCounter = view.getInputFieldCounter();
		this.inputSearchFKey = generateSearchFieldKeyArray();
		this.searchInitiatorImpl = searchInitiatorImpl;
	}

	/**
	 * 
	 * @param inputValues
	 * @param inputSourceKey
	 */
	void executeSearch(String[] inputValues, int[] inputSourceKey) {
		if (inputSearchFKey == null || inputSearchFKey.length == 0 || inputSourceKey == null
				|| inputSourceKey.length == 0) {
			throw new IllegalArgumentException("Die Liste mit Eingabefeldern ist leer oder hat keinen Wert.");
		}
		// clear list of used SearchFieldKeys first
		usedSearchFields.clear();
		List<UserQueries> requestList = new ArrayList<UserQueries>();
		for (int i = 0; i < inputValues.length; i++) {
			if (!inputValues[i].isEmpty() && !"false".equals(inputValues[i]) && !"yyyy-mm-dd".equals(inputValues[i])) {
				UserQueriesImpl tmpReq = new UserQueriesImpl(inputSearchFKey[i], inputValues[i], inputSourceKey[i]);
				requestList.add(tmpReq);
			}
		}

		try {
			ResultSet resultSet = searchInitiatorImpl.search(requestList);
			view.fillResultTableWithResultSet(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Ein Fehler bei der Suche ist aufgetreten:\n" + e.getMessage());
		}
	}

	/**
	 * 
	 * @param string
	 */
	public void startSinglePersonSearch(String string) {
		try {
			searchInitiatorImpl.searchPerson(string);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Ein Fehler bei der Suche nach Person mit ID " + string + " ist aufgetreten.");
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
		sfkArray[17] = SearchFieldKeys.ZUSAETZE;
		sfkArray[18] = SearchFieldKeys.FUNDORTE;
		sfkArray[19] = SearchFieldKeys.ANMERKUNGEN;
		sfkArray[20] = SearchFieldKeys.NUMMER;
		sfkArray[21] = SearchFieldKeys.SEITE_ORIGINALE;
		sfkArray[22] = SearchFieldKeys.NUMMER_HESS;

		return sfkArray;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
}
