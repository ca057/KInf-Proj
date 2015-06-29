package de.uniba.kinf.projm.hylleblomst.view;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.logic.PersonItem;
import de.uniba.kinf.projm.hylleblomst.logic.QueryRequest;
import de.uniba.kinf.projm.hylleblomst.logic.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.logicImpl.QueriesImpl;
import de.uniba.kinf.projm.hylleblomst.logicImpl.QueryRequestImpl;

/**
 * Controller for executing the search.
 * 
 * @author ca
 *
 */
public class SearchController {

	/**
	 * Array stores for every input field the corresponding search field key.
	 */
	private SearchFieldKeys[] inputSearchFKey;

	private int inputCounter;

	/**
	 * QueriesImpl executes the search.
	 */
	private QueriesImpl queriesImpl;

	/**
	 * 
	 */
	private ViewController view;

	/**
	 * 
	 * @param view
	 */
	public SearchController(ViewController view) {
		this.view = view;
		this.inputCounter = view.getInputFieldCounter();
		this.inputSearchFKey = generateSearchFieldKeyArray();
		this.queriesImpl = new QueriesImpl();
	}

	/**
	 * 
	 * @param inputValues
	 * @param inputSourceKey
	 */
	void executeSearch(String[] inputValues, int[] inputSourceKey) {
		if (inputSearchFKey == null || inputSearchFKey.length == 0
				|| inputSourceKey == null || inputSourceKey.length == 0) {
			throw new IllegalArgumentException(
					"Die Liste mit Eingabefeldern ist leer oder hat keinen Wert.");
		}

		List<QueryRequest> requestList = new ArrayList<QueryRequest>();
		for (int i = 0; i < inputValues.length; i++) {
			if (!inputValues[i].isEmpty() && !"false".equals(inputValues[i])
					&& !"yyyy-mm-dd".equals(inputValues[i])) {
				QueryRequestImpl tmpReq = new QueryRequestImpl(
						inputSearchFKey[i], inputValues[i], inputSourceKey[i]);
				requestList.add(tmpReq);
			}
		}

		try {
			view.setInfoTextExtendedSearch(requestList);
			if (requestList.size() != 0) {
				ArrayList<PersonItem> result = queriesImpl.search(requestList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Ein Fehler bei der Suche ist aufgetreten:\n"
							+ e.getMessage());
		}
	}

	/**
	 * Builds PersonItems out of the given ResultSet and starts filling the
	 * TableView.
	 * 
	 * @param result
	 */
	private void processResultSetAndFillTable(ResultSet result) {
		// take the ResultSet and get all columns

		// except of the following columns, all other column names can found by
		// using the searchfieldkeys
		// vorname_norm
		// nachname_norm
		// ort_norm
		// fakultaet_norm
		//
		try {
			ResultSetMetaData metaData = result.getMetaData();
			System.out.println(metaData.getColumnCount());
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				System.out.println(metaData.getColumnName(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// view.fillResultTable(results);
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
}
