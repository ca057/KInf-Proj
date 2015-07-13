package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

/**
 * Controller for the main graphical user interface. The ViewController manages
 * all the nodes of the layout and several more important classes.
 */
public class MainController implements Initializable {

	private Model model;

	private StartController startController;

	@FXML
	private BorderPane root;

	@FXML
	private Parent mainMenu;

	@FXML
	private MenuController mainMenuController;

	@FXML
	private Parent inputView;

	@FXML
	private InputController inputViewController;

	@FXML
	private Parent tableView;

	@FXML
	private TableViewController tableViewController;

	@FXML
	private Parent detailsView;

	@FXML
	private DetailsViewController detailsViewController;

	/**
	 * Default constructor.
	 */
	public MainController() {

	}

	/**
	 * Implemented from Initializable. After the JavaFX-elements with
	 * FXML-Annotations are initialized, the event handlers are set.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setEventHandlers();
	}

	/**
	 * The {@link MainModel} is set.
	 * 
	 * <p>
	 * <b>Precondition</b>
	 * <ul>
	 * <li>the {@link MainModel} must not be {@code null}</li>
	 * </ul>
	 * </b>
	 * 
	 * @param model
	 *            the {@link MainModel} to set
	 */
	public void setModel(Model model) {
		if (model == null) {
			throw new IllegalArgumentException("Das übergene Model ist ungültig und hat keinen Wert.");
		}
		this.model = model;
		setModelToControllers();
	}

	/*
	 * Called by the {@link StartController}, this methods sets the model for
	 * all controllers. It also sets a {@link TableViewController} as observer
	 * to the {@link MainModel} and adds a {@link DetailsViewController}.
	 */
	private void setModelToControllers() {
		model.setDetailsController(detailsViewController);
		model.addObserver(tableViewController);
		detailsViewController.setModel(model);
		mainMenuController.setModel(model);
		mainMenuController.setMainController(this);
		inputViewController.setModel(model);
		tableViewController.setModel(model);
		tableViewController.setMainController(this);
	}

	public void setStartController(StartController startController) {
		if (startController == null) {
			throw new IllegalArgumentException(
					"Der übergebene StartController hat keinen Wert und kann nicht gesetzt werden.");
		}
		this.startController = startController;
	}

	/**
	 * Function sets up event handlers for key input, numerical input in input
	 * fields, for the menu and the table view. Setting up the different
	 * handlers is delegated to different helper functions.
	 */
	private void setEventHandlers() {
		root.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ENTER) {
					inputViewController.startSearch();
				}
				ke.consume();
			}
		});
	}

	/**
	 * 
	 * @return
	 */
	CachedRowSet getResult() {
		return tableViewController.getResult();
	}

	/**
	 * 
	 */
	void closeWindow() {
		startController.closeView();
	}

	/**
	 * 
	 * @return
	 */
	StringProperty getSelectedSourceProperty() {
		return inputViewController.getSelectedSourceProperty();
	}
}
