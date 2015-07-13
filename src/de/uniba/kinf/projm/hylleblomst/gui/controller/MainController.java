package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import de.uniba.kinf.projm.hylleblomst.keys.DatabaseKeys;
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

	private ViewHelper viewHelper;

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
	 * Constructor for a new Controller. The constructor initiates all
	 * variables, inititates the {@link Model} and {@link SearchController}.
	 * {@link DatabaseKeys} are set to a default value.
	 * 
	 */
	public MainController() {
		viewHelper = new ViewHelper();
	}

	/**
	 * Implemented from Initializable, this method initializes all
	 * FXML-variables. It makes different default setups, like clearing the
	 * result table or setting up the event handlers. The execution of these
	 * setups is delegated to helper functions.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setEventHandlers();
	}

	public void setModel(Model model) {
		if (model == null) {
			throw new IllegalArgumentException("Das übergene Model ist ungültig und hat keinen Wert.");
		}
		this.model = model;
	}

	public void setModelToControllers(Model model) {
		if (model == null) {
			throw new IllegalArgumentException("Das übergene Model ist ungültig und hat keinen Wert.");
		}
		detailsViewController.setModel(model);
		mainMenuController.setModel(model);
		mainMenuController.setViewController(this);
		inputViewController.setModel(model);
		tableViewController.setModel(model);
		model.setDetailsController(detailsViewController);
		model.addObserver(tableViewController);
	}

	/**
	 * Function sets up event handlers for key input, numerical input in input
	 * fields, for the menu and the table view. Setting up the different
	 * handlers is delegated to different helper functions.
	 */
	private void setEventHandlers() {
		setKeyEvents();
		// setNumericalInputEventHandlers();
		// setTableViewEventHandlers();
	}

	/**
	 * Sets up all 'global' key events. At the moment, this is only the ENTER
	 * key starting the search when a valid input was done.
	 */
	private void setKeyEvents() {
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

	CachedRowSet getResult() {
		return tableViewController.getResult();
	}
}
