package de.uniba.kinf.projm.hylleblomst.view;

import java.util.InputMismatchException;

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
	 * Starts the search.
	 */
	@FXML
	private void startSearch() {
		// FIXME Eingaben sammeln und an Suche übergeben
		// Aktuell wird nur der Info-Text ausgegeben
		setInfoText();
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
			ui.showErrorMessage(e.getMessage());
		}
	}

	/**
	 * Clears all input fields of the search.
	 */
	@FXML
	private void clearSearchInput() {
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
		String info = "Suchanfrage\n-----------";
		info += "Volltextsuche: " + getFullTextSearchInput() + "\n";
		infoArea.setText(info);
	}
}
