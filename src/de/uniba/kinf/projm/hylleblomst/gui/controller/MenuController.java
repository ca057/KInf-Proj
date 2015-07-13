package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.ResourceBundle;

import de.uniba.kinf.projm.hylleblomst.exceptions.ExportException;
import de.uniba.kinf.projm.hylleblomst.exceptions.ImportException;
import de.uniba.kinf.projm.hylleblomst.exceptions.SetUpException;
import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	private Model model;

	private ViewHelper viewHelper;

	private FileChooser fileChooser;

	private ViewController viewController;

	@FXML
	private MenuBar menuRoot;

	@FXML
	private MenuItem mainMenu_file_export;

	@FXML
	private MenuItem mainMenu_file_close;

	@FXML
	private MenuItem mainMenu_database_setupDatabase;

	@FXML
	private MenuItem mainMenu_database_importData;

	@FXML
	private MenuItem mainMenu_database_clearDatabase;

	@FXML
	private MenuItem mainMenu_help_about;

	public MenuController() {
		viewHelper = new ViewHelper();
		fileChooser = new FileChooser();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setMenuEventHandlers();
	}

	public void setModel(Model model) {
		if (model != null) {
			this.model = model;
		} else {
			throw new InputMismatchException("Das übergebene Model ist fehlerhaft und kann nicht gesetzt werden.");
		}
	}

	public void setViewController(ViewController viewController) {
		if (viewController != null) {
			this.viewController = viewController;
		} else {
			throw new InputMismatchException(
					"Der übergebene ViewController ist fehlerhaft und kann nicht gesetzt werden.");
		}
	}

	/**
	 * Sets up all event handlers belonging to the main menu. If an action could
	 * not be performed, an alert window with information on the error is shown.
	 */
	private void setMenuEventHandlers() {
		mainMenu_file_export.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (viewController.getResult() != null) {
					fileChooser.setTitle(viewHelper.getAppName() + " - Speicherort für Export auswählen");
					fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV-Datei (*.csv)", "*.csv"));

					Optional<File> exportFile = Optional
							.ofNullable(fileChooser.showSaveDialog(menuRoot.getScene().getWindow()));
					if (exportFile.isPresent()) {
						try {
							if (viewController.getResult().getMetaData().getColumnCount() != 0) {
								model.exportSearchedData(exportFile.get(), viewController.getResult());
								viewHelper.showInfo("Export der Daten in Datei " + exportFile.get().getName()
										+ " war erfolgreich.");
							} else {
								viewHelper.showErrorMessage(
										"Suchergebnis enthält keine Daten, Export ist nicht möglich.");
							}
						} catch (ExportException | SQLException e) {
							e.printStackTrace();
							viewHelper
									.showErrorMessage("Export der Daten in Datei " + exportFile.get().getAbsolutePath()
											+ " war nicht erfolgreich.\n" + e.getMessage());
						}
					}
				} else {
					viewHelper.showErrorMessage("Keine Suchergebnisse vorhanden, Export der Daten nicht möglich.");
				}
				event.consume();
			}
		});
		mainMenu_file_close.setOnAction((event) -> {
			closeWindow();
		});
		mainMenu_database_setupDatabase.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser dirChooser = new DirectoryChooser();
				dirChooser.setTitle(viewHelper.getAppName() + " - Pfad für Datenbank auswählen");
				Optional<File> setupDir = Optional.ofNullable(dirChooser.showDialog(menuRoot.getScene().getWindow()));
				if (setupDir.isPresent()) {
					try {
						model.setUpDatabase(setupDir.get().getAbsoluteFile());
						viewHelper.showInfo("Das Anlegen der Datenbank und Tabellen im Verzeichnis "
								+ setupDir.get().toString() + " war erfolgreich.");
					} catch (SetUpException e) {
						viewHelper.showErrorMessage("Datenbank konnte nicht angelegt werden:\n" + e.getMessage());
						e.printStackTrace();
					}
				}
				event.consume();
			}
		});
		mainMenu_database_importData.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				fileChooser.setTitle(viewHelper.getAppName() + " - CSV-Datei für Import auswählen");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV-Datei (*.csv)", "*.csv"));

				Optional<File> importFile = Optional
						.ofNullable(fileChooser.showOpenDialog(menuRoot.getScene().getWindow()));
				if (importFile.isPresent()) {
					try {
						model.importData(importFile.get().getAbsoluteFile());
						viewHelper.showInfo("Import der Datei " + importFile.get().getName() + " war erfolgreich.");
					} catch (ImportException e) {
						viewHelper.showErrorMessage("Datei konnte nicht importiert werden:\n" + e.getMessage());
						e.printStackTrace();
					}
				}
				event.consume();
			}
		});
		mainMenu_database_clearDatabase.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (viewHelper.getUserConfirmation("Datenbank leeren")) {
					try {
						model.clearDatabase();
					} catch (SetUpException e) {
						e.printStackTrace();
						viewHelper.showErrorMessage("Datenbank konnte nicht geleert werden:\n" + e.getMessage());
					}
				}
				event.consume();
			}
		});
		mainMenu_help_about.setOnAction((event) -> {
			viewHelper.showApplicationInfo();
		});
	}

	/**
	 * Closes the window when the users submits the action.
	 */
	private void closeWindow() {
		Stage stage = (Stage) menuRoot.getScene().getWindow();
		if (viewHelper.askForClosingWindow()) {
			stage.close();
			System.exit(0);
		}
	}

}
