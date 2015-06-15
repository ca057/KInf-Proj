package de.uniba.kinf.projm.hylleblomst.view;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
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
	private UIHelper ui = new UIHelper();

	/**
	 * QueriesImpl executes the search.
	 */
	private QueriesImpl querieImpl = new QueriesImpl();

	/**
	 * Array stores for every input field the corresponding search field key.
	 */
	private SearchFieldKeys[] inputSearchFKey;

	/**
	 * Array stores all input fields of the graphical user interface.
	 */
	private Control[] inputFields;

	/**
	 * Array stores for every input field the corresponding source key.
	 */
	private int[] inputSourceKey;

	/**
	 * Stores the number of input fields for usable generation of input field
	 * arrays.
	 */
	private int inputFieldCounter = 6;

	/**
	 * Default constructor for a new Controller. When called, the three arrays
	 * with input fields and keys are set.
	 */
	public Controller() {
		setUpArrayWithInputs();
	}

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
	 * Builds three arrays with all user input fields of the user interface and
	 * their corresponding {@link SearchFieldKeys} and {@link SourceKeys}.
	 * 
	 * @return
	 */
	private void setUpArrayWithInputs() {
		inputSearchFKey = new SearchFieldKeys[inputFieldCounter];
		inputFields = new Control[inputFieldCounter];
		inputSourceKey = new int[inputFieldCounter];

		inputSearchFKey[0] = SearchFieldKeys.ANREDE_TRAD;
		inputFields[0] = searchCategory_person_anrede;
		inputSourceKey[0] = SourceKeys.STANDARD;

		inputSearchFKey[1] = SearchFieldKeys.ANREDE_NORM;
		inputFields[1] = searchCategory_person_anredenorm;
		inputSourceKey[1] = SourceKeys.NORM;

		inputSearchFKey[2] = SearchFieldKeys.TITEL_TRAD;
		inputFields[2] = searchCategory_person_titel;
		inputSourceKey[2] = SourceKeys.STANDARD;

		inputSearchFKey[3] = SearchFieldKeys.TITEL_NORM;
		inputFields[3] = searchCategory_person_titelnorm;
		inputSourceKey[3] = SourceKeys.NORM;

		// FIXME korrekte SearchFieldKeys und SourceKeys abhängig von speichern
		inputSearchFKey[4] = SearchFieldKeys.VORNAME_TRAD;
		inputFields[4] = searchCategory_person_vornameinput;
		inputSourceKey[4] = SourceKeys.STANDARD;

		// FIXME korrekte SearchFieldKeys und SourceKeys speichern
		inputSearchFKey[5] = SearchFieldKeys.NACHNAME_TRAD;
		inputFields[5] = searchCategory_person_nachnameinput;
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
		try {
			if (inputSearchFKey == null || inputSearchFKey.length == 0
					|| inputFields == null || inputFields.length == 0
					|| inputSourceKey == null || inputSourceKey.length == 0) {
				throw new IllegalArgumentException(
						"Die Liste mit Eingabefeldern ist leer oder hat keinen Wert.");
			}
			// neue Rückgabeliste anlegen
			List<QueryRequest> requestList = new ArrayList<QueryRequest>();
			// über alle Eingabefelder iterieren
			for (int i = 0; i < inputFieldCounter; i++) {
				// checken, welcher Typ von Eingabefeld das ist und entsprechend
				// behandeln
				if (inputFields[i] instanceof TextField) {
					QueryRequest tmpReg = new QueryRequest(inputSearchFKey[i],
							((TextField) inputFields[i]).getText(),
							inputSourceKey[i]);
					requestList.add(tmpReg);
				} else {
					// Fehler werfen falls Eingabetyp nicht gefunden wurde
					throw new RuntimeException(
							"Die Sucheingabe konnte nicht verarbeitet werden.");
				}
			}

			// falls eine leere Liste angelegt wurde, soll diese nicht an die
			// eigentliche Suche übergeben werden
			if (requestList.size() == 0) {
				throw new IllegalArgumentException(
						"Es wurden keine Sucheingaben gefunden, für die eine Suchanfrage gestellt werden kann.");
			} else {
				// FIXME setInfoText am entfernen, nur für Testzwecke
				setInfoTextExtendedSearch(requestList);
				querieImpl.search(requestList);
			}
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
		if (inputFields.length == 0) {
			throw new RuntimeException(
					"Es konnten keine Felder gefunden werden, die geleert werden können.");
		}
		for (int i = 0; i < inputFields.length; i++) {
			if (inputFields[i] instanceof TextInputControl) {
				((TextInputControl) inputFields[i]).clear();
			} else {
				// TODO andere input-typen implementieren
			}
		}
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
