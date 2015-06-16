package de.uniba.kinf.projm.hylleblomst.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import de.uniba.kinf.projm.hylleblomst.logic.QueriesImpl;
import de.uniba.kinf.projm.hylleblomst.logic.QueryRequest;
import de.uniba.kinf.projm.hylleblomst.logic.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.logic.SourceKeys;

/**
 * Controller for the graphical user interface.
 * 
 * @author ca
 *
 */
public class Controller {
	/**
	 * UIHelper supports a nice user interaction.
	 */
	UIHelper ui;

	/**
	 * Implements the logic of preparing the user input for passing it to the
	 * {@link QueriesImpl}.
	 */
	private SearchController searchCtrl;

	/**
	 * QueriesImpl executes the search.
	 */
	private QueriesImpl querieImpl;

	/**
	 * Array stores for every input field the corresponding search field key.
	 */
	private SearchFieldKeys[] inputSearchFKey;

	/**
	 * Array stores all input fields of the graphical user interface.
	 */
	private Object[] inputFields;

	/**
	 * Array stores for every input field the corresponding source key.
	 */
	private int[] inputSourceKey;

	/**
	 * Stores the number of input fields for usable generation of input field
	 * arrays.
	 */
	private int inputFieldCounter = 6;

	@FXML
	BorderPane root;

	@FXML
	MenuItem mainMenu_file_save;

	@FXML
	MenuItem mainMenu_file_export;

	@FXML
	MenuItem mainMenu_file_close;

	@FXML
	MenuItem mainMenu_edit_options;

	@FXML
	MenuItem mainMenu_help_help;

	@FXML
	MenuItem mainMenu_help_about;

	@FXML
	TitledPane searchCategory_person;

	@FXML
	TextField searchCategory_person_anrede;

	@FXML
	TextField searchCategory_person_anredenorm;

	@FXML
	TextField searchCategory_person_titel;

	@FXML
	TextField searchCategory_person_titelnorm;

	@FXML
	SplitMenuButton searchCategory_person_vornameselection;

	@FXML
	MenuItem selection_vorname;

	@FXML
	MenuItem selection_vornameNorm;

	@FXML
	MenuItem selection_vornameHSB;

	@FXML
	MenuItem selection_vornameHSC;

	@FXML
	MenuItem selection_vornameHSD;

	@FXML
	MenuItem selection_vornameHSE;

	@FXML
	MenuItem selection_vornameHSF;

	@FXML
	MenuItem selection_vornameHSG;

	@FXML
	MenuItem selection_vornameHSH;

	@FXML
	MenuItem selection_vornameHSI;

	@FXML
	MenuItem selection_vornameHSJ;

	@FXML
	MenuItem selection_vornameAUB;

	@FXML
	TextField searchCategory_person_vornameinput;

	@FXML
	SplitMenuButton searchCategory_person_nachnameselection;

	@FXML
	TextField searchCategory_person_nachnameinput;

	@FXML
	TitledPane searchCategory_personExtended;

	@FXML
	TitledPane searchCategory_study;

	@FXML
	TitledPane searchCategory_other;

	@FXML
	Button searchMenu_clearSearch;

	@FXML
	Button searchMenu_search;

	@FXML
	TableView resultTable;

	@FXML
	TextArea infoArea;

	/**
	 * Default constructor for a new Controller. When called, the three arrays
	 * with input fields and keys are set.
	 */
	public Controller() {
		generateArraysWithSearchAndSourceKeys();
		querieImpl = new QueriesImpl();
		ui = new UIHelper();
		searchCtrl = new SearchController(inputSearchFKey, inputSourceKey);
	}

	SearchFieldKeys[] getInputSearchFKey() {
		return inputSearchFKey;
	}

	Object[] getInputFields() {
		return inputFields;
	}

	int[] getInputSourceKey() {
		return inputSourceKey;
	}

	int getInputFieldCounter() {
		return inputFieldCounter;
	}

	/**
	 * Builds three arrays with all user input fields of the user interface and
	 * their corresponding {@link SearchFieldKeys} and {@link SourceKeys}.
	 */
	private void generateArraysWithInputs() {
		inputSearchFKey = new SearchFieldKeys[inputFieldCounter];
		inputSourceKey = new int[inputFieldCounter];

		inputSearchFKey[0] = SearchFieldKeys.ANREDE_TRAD;
		inputSourceKey[0] = SourceKeys.STANDARD;

		inputSearchFKey[1] = SearchFieldKeys.ANREDE_NORM;
		inputSourceKey[1] = SourceKeys.NORM;

		inputSearchFKey[2] = SearchFieldKeys.TITEL_TRAD;
		inputSourceKey[2] = SourceKeys.STANDARD;

		inputSearchFKey[3] = SearchFieldKeys.TITEL_NORM;
		inputSourceKey[3] = SourceKeys.NORM;

		// FIXME korrekte SearchFieldKeys und SourceKeys abhängig von speichern
		inputSearchFKey[4] = SearchFieldKeys.VORNAME_TRAD;
		inputSourceKey[4] = SourceKeys.STANDARD;

		// FIXME korrekte SearchFieldKeys und SourceKeys speichern
		inputSearchFKey[5] = SearchFieldKeys.NACHNAME_TRAD;
		inputSourceKey[5] = SourceKeys.STANDARD;
	}

	private Object[] generateArrayWithInputValues() {
		Object[] inputFields = new Object[inputFieldCounter];

		inputFields[0] = searchCategory_person_anrede.getText();
		inputFields[1] = searchCategory_person_anredenorm.getText();
		inputFields[2] = searchCategory_person_titel.getText();
		inputFields[3] = searchCategory_person_titelnorm.getText();
		inputFields[4] = searchCategory_person_vornameinput.getText();
		inputFields[5] = searchCategory_person_nachnameinput.getText();

		return inputFields;
	}

	private void generateArraysWithSearchAndSourceKeys() {
		inputSearchFKey = new SearchFieldKeys[inputFieldCounter];
		inputSourceKey = new int[inputFieldCounter];

		inputSearchFKey[0] = SearchFieldKeys.ANREDE_TRAD;
		inputSourceKey[0] = SourceKeys.STANDARD;

		inputSearchFKey[1] = SearchFieldKeys.ANREDE_NORM;
		inputSourceKey[1] = SourceKeys.NORM;

		inputSearchFKey[2] = SearchFieldKeys.TITEL_TRAD;
		inputSourceKey[2] = SourceKeys.STANDARD;

		inputSearchFKey[3] = SearchFieldKeys.TITEL_NORM;
		inputSourceKey[3] = SourceKeys.NORM;

		// FIXME korrekte SearchFieldKeys und SourceKeys abhängig von speichern
		inputSearchFKey[4] = SearchFieldKeys.VORNAME_TRAD;
		inputSourceKey[4] = SourceKeys.STANDARD;

		// FIXME korrekte SearchFieldKeys und SourceKeys speichern
		inputSearchFKey[5] = SearchFieldKeys.NACHNAME_TRAD;
		inputSourceKey[5] = SourceKeys.STANDARD;
	}

	/**
	 * Collects all data from the input fields and starts the search.
	 * 
	 * @throws IllegalArgumentException
	 *             if the list with all input fields could not be generated or
	 *             is null
	 */
	@FXML
	private void startSearch() {
		List<QueryRequest> requestList;
		try {
			requestList = searchCtrl
					.prepareInputForSearch(generateArrayWithInputValues());
			if (requestList == null || requestList.size() == 0) {
				throw new IllegalArgumentException(
						"Liste mit Suchanfrage hat keinen Wert (= null) oder enthält keine Werte.");
			}
			// FIXME setInfoText am entfernen, nur für Testzwecke
			setInfoTextExtendedSearch(requestList);
			querieImpl.search(requestList);
		} catch (Exception /* | IllegalArgumentException */e) {
			// FIXME korrekte Exceptions fangen!
			e.printStackTrace();
			ui.showErrorMessage(e.getMessage());
		}
	}

	/**
	 * Clears all input fields of the search.
	 */
	@FXML
	private void clearSearchInput() {
		/*
		 * if (inputFields.length == 0) { throw new RuntimeException(
		 * "Es konnten keine Felder gefunden werden, die geleert werden können."
		 * ); } for (int i = 0; i < inputFields.length; i++) { if
		 * (inputFields[i] instanceof TextField) { ((TextField)
		 * inputFields[i]).clear(); } else { // TODO andere input-typen
		 * implementieren und continue entfernen continue; } }
		 */
	}

	/**
	 * Closes the window when the users submits the action.
	 */
	@FXML
	protected void closeWindow() {
		Stage stage = (Stage) root.getScene().getWindow();
		if (ui.askForClosingWindow(stage)) {
			stage.close();
		}
	}

	/**
	 * Opens an alert window with some random information.
	 */
	@FXML
	private void showInfo() {
		ui.showInfo();
	}

	/**
	 * Helps ensuring full functionality and provides a feedback of the search
	 * input. Collects the user input from all input fields prints it to the
	 * info area.
	 */
	private void setInfoTextExtendedSearch(List<QueryRequest> requestList) {
		String info = "Suchanfrage\n-----------\n";
		if (requestList == null || requestList.size() == 0) {
			info += "Keine Sucheingaben gefunden.";
		} else {
			for (QueryRequest qr : requestList) {
				info += qr.getSearchField().toString() + ": ";
				info += (String) qr.getInput() + "; ";
				info += "Quellen#: " + qr.getSource() + "\n";
			}
		}

		infoArea.setText(info);
	}
}
