package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.exceptions.SearchException;
import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.logic.UserQuery;
import de.uniba.kinf.projm.hylleblomst.logicImpl.UserQueryImpl;

/**
 * Controller for executing the search. Is not connected to a part of the view,
 * but is used by their controllers for starting the search.
 * 
 * All search requests are checked for correct values, processed in a way the
 * model can finally execute the search.
 *
 */
public class SearchController {

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
	 * The constructor for an SearchController. Gets the number of input fields
	 * and a {@link Model}, which is needed to execute the search.
	 * 
	 * @param inputFieldCounter
	 *            the number of input fields of the view
	 * @param model
	 *            the {@ Model} of the application
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
	 * Main function for executing the search. Gets several parameters from the
	 * {@link ViewController} and processes them in a way the {@link Model} can
	 * use them for starting the search.
	 * 
	 * @param inputValues
	 *            a string array with all input values from the graphical user
	 *            interface
	 * @param inputSourceKey
	 *            an array of the corresponding {@code int}s of
	 *            {@link SourceKeys}, depending on the users input
	 * @param isOr
	 *            {@code true} if the user selected this option, {@code false}
	 *            otherwise
	 * @param isOpenedSearch
	 *            {@code true} if the user selected this option, {@code false}
	 *            otherwise
	 * @return {@code true} if the search could be started, {@code false}
	 *         otherwise
	 * @throws SearchException
	 *             if an error occurs while the execution of the search, thrown
	 *             up by the {@link Model}
	 */
	boolean executeSearch(String[] inputValues, int[] inputSourceKey, boolean isOr, boolean isOpenedSearch)
			throws SearchException {
		if (inputSearchFKey == null || inputSearchFKey.length == 0 || inputSourceKey == null
				|| inputSourceKey.length == 0) {
			throw new IllegalArgumentException("Die Liste mit Eingabefeldern ist leer oder hat keinen Wert.");
		}

		List<UserQuery> requestList = new ArrayList<UserQuery>();
		for (int i = 0; i < inputValues.length; i++) {
			if (!inputValues[i].isEmpty() && !"false".equals(inputValues[i]) && !"yyyy-mm-dd".equals(inputValues[i])) {
				UserQueryImpl tmpReq = new UserQueryImpl(inputSearchFKey[i], inputValues[i], inputSourceKey[i], isOr,
						isOpenedSearch);
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
			throw new SearchException("Ein Fehler bei der Suche ist aufgetreten:\n" + e.getMessage());
		}
	}

	/**
	 * After the user had clicked on a single row in the {@link TableView}, a
	 * {@link UserQuery} is created and passed as parameter to this function. If
	 * its a valid {@link UserQuery}, it is passed to the model to finally
	 * execute the search.
	 * 
	 * <p>
	 * <b>Precondition</b>
	 * <ul>
	 * <li>the {@link UserQuery} must not be {@code null} and contain the ID of
	 * the person</li>
	 * </ul>
	 * </p>
	 * 
	 * @param personIDQuery
	 *            the {@link UserQuery} to search for
	 * @throws SearchException
	 *             if an error occurs while searching, a {@link SearchException}
	 *             is thrown
	 */
	public void startSinglePersonSearch(UserQuery personIDQuery) throws SearchException {
		if (personIDQuery == null) {
			throw new InputMismatchException(
					"Übergebene Suchanfrage hat keinen Wert. Personendetails können nicht gesucht werden.");
		}
		try {
			model.searchPerson(personIDQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SearchException(
					"Ein Fehler bei der Suche nach Person mit ID " + personIDQuery + " ist aufgetreten.");
		}
	}

	/**
	 * An array with all {@link SearchFieldKeys} is generated and returned.
	 * 
	 * @return the array with {@link SearchFieldKeys}
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
}
