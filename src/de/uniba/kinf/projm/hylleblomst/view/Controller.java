package de.uniba.kinf.projm.hylleblomst.view;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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

public class Controller {
	private UIHelper ui = new UIHelper();

	private QueriesImpl querieImpl = new QueriesImpl();

	private int inputCounter = 6;

	public Controller() {
	}

	@FXML
	BorderPane root;

	@FXML
	MenuBar mainMenu;

	@FXML
	Menu mainMenu_file;

	@FXML
	MenuItem mainMenu_file_save;

	@FXML
	MenuItem mainMenu_file_export;

	@FXML
	MenuItem mainMenu_file_close;

	@FXML
	Menu mainMenu_edit;

	@FXML
	MenuItem mainMenu_edit_options;

	@FXML
	Menu mainMenu_help;

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
	ButtonBar searchMenu;

	@FXML
	Button searchMenu_clearSearch;

	@FXML
	Button searchMenu_search;

	@FXML
	TableView resultTable;

	@FXML
	TextArea infoArea;

	/**
	 * Builds an array with all input fields which are in the user interface.
	 * 
	 * @return the two-dimensional array with the {@link SearchFieldKeys} at
	 *         position 0 and the value from the corresponding input at position
	 *         1
	 */
	private Object[][] setUpArrayWithInputValues() {
		Object[][] inputArray = new Object[inputCounter][3];
		// TODO feste Einträge wie die Zuordnung zu Suchfeld oder Quelle in
		// Konstruktor auslagern, da dies nur zu Beginn der Laufzeit
		// durchgeführt werden muss
		inputArray[0][0] = SearchFieldKeys.ANREDE_TRAD;
		inputArray[0][1] = searchCategory_person_anrede.getText();
		inputArray[0][2] = SourceKeys.STANDARD;
		inputArray[1][0] = SearchFieldKeys.ANREDE_NORM;
		inputArray[1][1] = searchCategory_person_anredenorm.getText();
		inputArray[1][2] = SourceKeys.NORM;
		inputArray[2][0] = SearchFieldKeys.TITEL_TRAD;
		inputArray[2][1] = searchCategory_person_titel.getText();
		inputArray[2][2] = SourceKeys.STANDARD;
		inputArray[3][0] = SearchFieldKeys.TITEL_NORM;
		inputArray[3][1] = searchCategory_person_titelnorm.getText();
		inputArray[3][2] = SourceKeys.NORM;
		// FIXME korrekte SearchFieldKeys und SourceKeys abhängig von speichern
		inputArray[4][0] = SearchFieldKeys.VORNAME_TRAD;
		inputArray[4][1] = searchCategory_person_vornameinput.getText();
		inputArray[4][2] = SourceKeys.STANDARD;
		// FIXME korrekte SearchFieldKeys und SourceKeys speichern
		inputArray[5][0] = SearchFieldKeys.NACHNAME_TRAD;
		inputArray[5][1] = searchCategory_person_nachnameinput.getText();
		inputArray[5][2] = SourceKeys.STANDARD;

		return inputArray;
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
			Object[][] allInputFields = setUpArrayWithInputValues();
			if (allInputFields == null || allInputFields.length == 0) {
				throw new IllegalArgumentException(
						"Die Liste mit Eingabefeldern ist leer oder hat keinen Wert.");
			}

			List<QueryRequest> requestList = new ArrayList<QueryRequest>();

			for (int i = 0; i < inputCounter; i++) {
				// FIXME derzeit nur mit TextEingabe möglich, oder?
				if (!"".equals((allInputFields[i][1]))) {
					SearchFieldKeys sfk = (SearchFieldKeys) allInputFields[i][0];
					int source = (int) allInputFields[i][2];
					QueryRequest tmpReq = new QueryRequest(sfk,
							allInputFields[i][1], source);
					requestList.add(tmpReq);
				}
			}

			if (requestList.size() == 0) {
				throw new IllegalArgumentException(
						"Es wurden keine Sucheingaben gefunden, für die eine Suchanfrage gestellt werden kann.");
			} else {
				// FIXME setInfoText am Ende entfernen, zur Zeit nur für
				// Testzwecke enthalten
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
		// TODO clear all input fields
		searchCategory_person_anrede.clear();
		searchCategory_person_anredenorm.clear();
		searchCategory_person_titel.clear();
		searchCategory_person_titelnorm.clear();
		searchCategory_person_vornameinput.clear();
		searchCategory_person_nachnameinput.clear();
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
