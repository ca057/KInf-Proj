package de.uniba.kinf.projm.hylleblomst.view;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Map.Entry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import de.uniba.kinf.projm.hylleblomst.logic.ColumnKeys;
import de.uniba.kinf.projm.hylleblomst.logic.QueriesImpl;

public class Controller {
	private UIHelper ui = new UIHelper();

	private QueriesImpl querieImpl = new QueriesImpl();

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
	TextField search_fulltext;

	@FXML
	Button searchFulltext_searchBtn;

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
	 * Gets the input of the fulltext search.
	 * 
	 * @return the input as a string
	 */
	private String getFullTextSearchInput() {
		if (search_fulltext.getText() == null
				|| "".equals(search_fulltext.getText())) {
			throw new InputMismatchException(
					"Für eine Volltextsuche muss eine Eingabe in das Suchfeld vorhanden sein.");
		}
		return search_fulltext.getText();
	}

	/**
	 * Takes the input of the fulltext search field and starts the fulltext
	 * search.
	 */
	@FXML
	private void startFulltextSearch() {
		try {
			// FIXME setInfoText am Ende entfernen, zur Zeit nur für Testzwecke
			// enthalten
			setInfoText();
			querieImpl.fullTextSearch(getFullTextSearchInput());
		} catch (Exception e) {
			// TODO korrekte Exception fangen und nicht einfach mal alles
			e.printStackTrace();
			ui.showErrorMessage(e.getMessage());
		}
	}

	/**
	 * Gets the input of all user input fields and stores it in a
	 * {@link HashMap}.
	 * 
	 * @return The {@link HashMap} with all inputs
	 */
	private Map<Enum<ColumnKeys>, Object> getSearchInput() {
		Map<Enum<ColumnKeys>, Object> map = new HashMap<Enum<ColumnKeys>, Object>();
		if (!"".equals(searchCategory_person_anrede.getText())) {
			map.put(ColumnKeys.ANREDE, searchCategory_person_anrede.getText());
		}
		if (!"".equals(searchCategory_person_anredenorm.getText())) {
			map.put(ColumnKeys.ANREDE_NORM,
					searchCategory_person_anredenorm.getText());
		}
		if (!"".equals(searchCategory_person_titel.getText())) {
			map.put(ColumnKeys.TITEL, searchCategory_person_titel.getText());
		}
		if (!"".equals(searchCategory_person_titelnorm.getText())) {
			map.put(ColumnKeys.TITEL_NORM,
					searchCategory_person_titelnorm.getText());
		}

		return map;
	}

	/**
	 * Starts the extended search.
	 */
	@FXML
	private void startSearch() {
		try {
			// FIXME setInfoText am Ende entfernen, zur Zeit nur für Testzwecke
			// enthalten
			setInfoText();
			querieImpl.extendedSearch(getSearchInput());
		} catch (Exception e) {
			// TODO korrekte Exception fangen und nicht einfach mal alles
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
		search_fulltext.clear();
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
	private void setInfoText() {
		String info = "Suchanfrage\n-----------\n";
		// if (!(getFullTextSearchInput() == null)) {
		// info += "Volltextsuche: " + getFullTextSearchInput() + "\n";
		// }
		Map<Enum<ColumnKeys>, Object> map = getSearchInput();

		for (Entry<Enum<ColumnKeys>, Object> entry : map.entrySet()) {
			info += entry.getKey() + ": " + entry.getValue() + "\n";
		}
		infoArea.setText(info);
	}
}
