package de.uniba.kinf.projm.hylleblomst.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
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
	Accordion searchCategories;

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
	ComboBox searchCategory_person_vornameselection;

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
	ComboBox searchCategory_person_nachnameselection;

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
	 * Default constructor for a new Controller. When called, the array with
	 * {@link SearchFieldKeys} and {@link SourceKeys} is build and set, the
	 * instances of the {@link QueriesImpl}, {@link UIHelper} and
	 * {@link SearchController} are instantiated.
	 * 
	 */
	public Controller() {
		ui = new UIHelper();
		searchCtrl = new SearchController(inputFieldCounter);
	}

	/**
	 * Returns the amount of input fields as an {@code int}.
	 * 
	 * @return the amount of input fields as {@code int}
	 */
	int getInputFieldCounter() {
		return inputFieldCounter;
	}

	/**
	 * Builds an array of all input fields and their value at the moment of
	 * building. The order of inputs corresponds with the order of the keys in
	 * {@link #generateArraysWithSourceFieldKeys()}. The method does not check,
	 * if a input was done or if the input field was left empty.
	 * 
	 * @return the array with all inputs
	 */
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

	/**
	 * The two arrays with {@link SearchFieldKeys} and {@link SourceKeys} are
	 * build.
	 */
	private int[] generateArrayWithSourceFieldKeys() {
		int[] inputSourceKey = new int[inputFieldCounter];

		inputSourceKey[0] = SourceKeys.STANDARD;
		inputSourceKey[1] = SourceKeys.NORM;
		inputSourceKey[2] = SourceKeys.STANDARD;
		inputSourceKey[3] = SourceKeys.NORM;
		// FIXME korrekte SearchFieldKeys und SourceKeys abhängig von Auswahl
		// speichern
		inputSourceKey[4] = SourceKeys.STANDARD;
		// FIXME korrekte SearchFieldKeys und SourceKeys abhängig von Auswahl
		// speichern
		inputSourceKey[5] = SourceKeys.STANDARD;

		return inputSourceKey;
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
			searchCtrl.prepareInputForSearch(generateArrayWithInputValues(),
					generateArrayWithSourceFieldKeys());
		} catch (RuntimeException e) {
			e.printStackTrace();
			ui.showErrorMessage(e.getMessage());
		}
	}

	/**
	 * Clears all input fields of the search.
	 */
	@FXML
	private void clearSearchInput() {
		searchCategory_person_anrede.clear();
		searchCategory_person_anredenorm.clear();
		searchCategory_person_titel.clear();
		searchCategory_person_titelnorm.clear();
		searchCategory_person_vornameinput.clear();
		searchCategory_person_nachnameinput.clear();
		infoArea.clear();
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
