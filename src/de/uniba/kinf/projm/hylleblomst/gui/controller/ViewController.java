package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.exceptions.SearchException;
import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import de.uniba.kinf.projm.hylleblomst.keys.DatabaseKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logicImpl.SearchInitiatorImpl;
import de.uniba.kinf.projm.hylleblomst.logicImpl.UserQueryImpl;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 * Controller for the main graphical user interface. The ViewController manages
 * all the nodes of the layout and several more important classes.
 */
public class ViewController implements ControllerInterface, Initializable {

	private SearchInitiator initiator;

	private Model model;

	private ViewHelper viewHelper;

	private SearchController searchCtrl;

	private int inputFieldCounter = 24;

	private StringProperty sourceLabelName = new SimpleStringProperty("Quelle: ");

	private CachedRowSet result;

	private DatabaseKeys dbKey;

	@FXML
	private BorderPane root;

	@FXML
	private ComboBox<String> search_sourcekey_selection;

	@FXML
	private CheckBox search_useOrConjunction;

	@FXML
	private CheckBox search_useOpenSearch;

	@FXML
	private Accordion searchCategories;

	@FXML
	private TitledPane searchCategory_person;

	@FXML
	private TextField searchCategory_person_anrede;

	@FXML
	private TextField searchCategory_person_anredenorm;

	@FXML
	private TextField searchCategory_person_titel;

	@FXML
	private TextField searchCategory_person_titelnorm;

	@FXML
	private ComboBox<String> searchCategory_person_vornameselection;

	@FXML
	private TextField searchCategory_person_vornameinput;

	@FXML
	private ComboBox<String> searchCategory_person_nachnameselection;

	@FXML
	private TextField searchCategory_person_nachnameinput;

	@FXML
	private TitledPane searchCategory_personExtended;

	@FXML
	private CheckBox searchCategory_personExtended_adeliger;

	@FXML
	private CheckBox searchCategory_personExtended_jesuit;

	@FXML
	private ComboBox<String> searchCategory_personExtended_wirtschaftselection;

	@FXML
	private TextField searchCategory_personExtended_wirtschaftinput;

	@FXML
	private ComboBox<String> searchCategory_personExtended_ortselection;

	@FXML
	private TextField searchCategory_personExtended_ortinput;

	@FXML
	private TitledPane searchCategory_study;

	@FXML
	private ComboBox<String> searchCategory_study_studienfachselection;

	@FXML
	private TextField searchCategory_study_studienfachinput;

	@FXML
	private TextField searchCategory_study_fakultaet;

	@FXML
	private ComboBox<String> searchCategory_study_seminarselection;

	@FXML
	private TextField searchCategory_study_seminarinput;

	@FXML
	private CheckBox searchCategory_study_graduiert;

	@FXML
	private TextField searchCategory_study_studienjahrVon;

	@FXML
	private TextField searchCategory_study_studienjahrBis;

	@FXML
	private TextField searchCategory_study_einschreibeTagVon;

	@FXML
	private TextField searchCategory_study_einschreibeMonatVon;

	@FXML
	private TextField searchCategory_study_einschreibeJahrVon;

	@FXML
	private TextField searchCategory_study_einschreibeTagBis;

	@FXML
	private TextField searchCategory_study_einschreibeMonatBis;

	@FXML
	private TextField searchCategory_study_einschreibeJahrBis;

	@FXML
	private Label searchCategory_study_einschreibeHinweis;

	@FXML
	private TitledPane searchCategory_other;

	@FXML
	private ComboBox<String> searchCategory_other_zusaetzeselection;

	@FXML
	private TextField searchCategory_other_zusaetzeinput;

	@FXML
	private TextField searchCategory_other_fundort;

	@FXML
	private TextField searchCategory_other_anmerkungen;

	@FXML
	private TextField searchCategory_other_nummer;

	@FXML
	private TextField searchCategory_other_seite;

	@FXML
	private TextField searchCategory_other_nummerhess;

	@FXML
	private Button searchMenu_clearSearch;

	@FXML
	private Button searchMenu_search;

	@FXML
	private TableView<ObservableList<String>> resultTable;

	@FXML
	private Label search_sourceLabel;

	@FXML
	private AnchorPane result_persondetails_anchorpane;

	@FXML
	private Parent detailsView;

	@FXML
	private DetailsViewController detailsViewController;

	@FXML
	private Parent mainMenu;

	@FXML
	private MenuController mainMenuController;

	/**
	 * Constructor for a new Controller. The constructor initiates all
	 * variables, inititates the {@link Model} and {@link SearchController}.
	 * {@link DatabaseKeys} are set to a default value.
	 * 
	 */
	public ViewController() {
		dbKey = new DatabaseKeys("./db");
		viewHelper = new ViewHelper();
		initiator = new SearchInitiatorImpl(dbKey);
		model = new Model(initiator);
		model.addObserver(this);
		searchCtrl = new SearchController(inputFieldCounter, model);
	}

	/**
	 * Implemented from Initializable, this method initializes all
	 * FXML-variables. It makes different default setups, like clearing the
	 * result table or setting up the event handlers. The execution of these
	 * setups is delegated to helper functions.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clearResultTable();
		setUpNodes();
		setEventHandlers();
		setUpBindings();
		// when import of embedded fxml is finished, set the DetailsController
		// of the model
		model.setDetailsController(detailsViewController);
		detailsViewController.setModel(model);
		mainMenuController.setModel(model);
		mainMenuController.setViewController(this);
	}

	/**
	 * Clears the {@link TableView}.
	 */
	private void clearResultTable() {
		resultTable.getColumns().clear();
		resultTable.getItems().clear();
	}

	/**
	 * Makes default setups for different nodes, like setting the default
	 * expanded pane for the accordion or label names.
	 */
	private void setUpNodes() {
		searchCategories.setExpandedPane(searchCategory_person);
		search_sourceLabel.setText(sourceLabelName.getValueSafe());
		searchCategory_study_einschreibeHinweis
				.setText("Hinweis: Für eine erfolgreiche Suche muss jeweils mindestens das Jahr ausgefüllt sein.");
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
	 * 
	 * @return
	 */
	CachedRowSet getResult() {
		return result;
	}

	/**
	 * Function sets up event handlers for key input, numerical input in input
	 * fields, for the menu and the table view. Setting up the different
	 * handlers is delegated to different helper functions.
	 */
	private void setEventHandlers() {
		setKeyEvents();
		setNumericalInputEventHandlers();
		setTableViewEventHandlers();
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
					startSearch();
				}
				ke.consume();
			}
		});
	}

	/**
	 * 
	 */
	private void setNumericalInputEventHandlers() {
		searchCategory_study_studienjahrVon.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_study_studienjahrVon.getText().matches("[0-9]{1,4}")) {
					if (getParsedInt(searchCategory_study_studienjahrVon.getText()) < 0
							|| getParsedInt(searchCategory_study_studienjahrVon.getText()) > 2015) {
						viewHelper.showErrorMessage("Studienjahr muss eine Zahl zwischen 0 und 2015 sein.");
						searchCategory_study_studienjahrVon.clear();
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage("Studienjahr muss eine Zahl zwischen 0 und 2015 sein.");
					searchCategory_study_studienjahrVon.clear();
				}
				ke.consume();
			}
		});
		searchCategory_study_studienjahrBis.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_study_studienjahrBis.getText().matches("[0-9]{1,4}")) {
					if (getParsedInt(searchCategory_study_studienjahrBis.getText()) < 0
							|| getParsedInt(searchCategory_study_studienjahrBis.getText()) > 2015) {
						viewHelper.showErrorMessage("Studienjahr muss eine Zahl zwischen 0 und 2015 sein.");
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage("Studienjahr muss eine Zahl zwischen 0 und 2015 sein.");
					searchCategory_study_studienjahrBis.clear();
				}
				ke.consume();
			}
		});
		searchCategory_study_einschreibeJahrVon.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_study_einschreibeJahrVon.getText().matches("[0-9]{1,4}")) {
					if (getParsedInt(searchCategory_study_einschreibeJahrVon.getText()) < 0
							|| getParsedInt(searchCategory_study_einschreibeJahrVon.getText()) > 2015) {
						viewHelper.showErrorMessage("Einschreibejahr muss eine Zahl zwischen 0 und 2015 sein.");
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage("Einschreibejahr muss eine Zahl zwischen 0 und 2015 sein.");
					searchCategory_study_einschreibeJahrVon.clear();
				}
				ke.consume();
			}
		});
		searchCategory_study_einschreibeMonatVon.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_study_einschreibeMonatVon.getText().matches("[1-9][0-2]?")) {
					if (getParsedInt(searchCategory_study_einschreibeMonatVon.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeMonatVon.getText()) > 12) {
						viewHelper.showErrorMessage("Einschreibemonat muss eine Zahl von 1 bis 12 sein.");
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage("Einschreibemonat muss eine Zahl von 1 bis 12 sein.");
					searchCategory_study_einschreibeMonatVon.clear();
				}
				ke.consume();
			}
		});
		searchCategory_study_einschreibeTagVon.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_study_einschreibeTagVon.getText().matches("[1-3][0-9]?")) {
					if (getParsedInt(searchCategory_study_einschreibeTagVon.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeTagVon.getText()) > 31) {
						viewHelper.showErrorMessage("Einschreibetag muss eine Zahl von 1 bis 31 sein.");
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage("Einschreibetag muss eine Zahl von 1 bis 31 sein.");
					searchCategory_study_einschreibeTagVon.clear();
				}
				ke.consume();
			}
		});
		searchCategory_study_einschreibeJahrBis.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_study_einschreibeJahrBis.getText().matches("[0-9]{1,4}")) {
					if (getParsedInt(searchCategory_study_einschreibeJahrBis.getText()) < 0
							|| getParsedInt(searchCategory_study_einschreibeJahrBis.getText()) > 2015) {
						viewHelper.showErrorMessage("Einschreibejahr muss eine Zahl zwischen 0 und 2015 sein.");
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage("Einschreibejahr muss eine Zahl zwischen 0 und 2015 sein.");
					searchCategory_study_einschreibeJahrBis.clear();
				}
				ke.consume();
			}
		});
		searchCategory_study_einschreibeMonatBis.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_study_einschreibeMonatBis.getText().matches("[1-9][0-2]?")) {
					if (getParsedInt(searchCategory_study_einschreibeMonatBis.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeMonatBis.getText()) > 12) {
						viewHelper.showErrorMessage("Einschreibemonat muss eine Zahl von 1 bis 12 sein.");
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage("Einschreibemonat muss eine Zahl von 1 bis 12 sein.");
					searchCategory_study_einschreibeMonatBis.clear();
				}
				ke.consume();
			}
		});
		searchCategory_study_einschreibeTagBis.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_study_einschreibeTagBis.getText().matches("[1-3][0-9]?")) {
					if (getParsedInt(searchCategory_study_einschreibeTagBis.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeTagBis.getText()) > 31) {
						viewHelper.showErrorMessage("Einschreibetag muss eine Zahl von 1 bis 31 sein.");
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage("Einschreibetag muss eine Zahl von 1 bis 31 sein.");
					searchCategory_study_einschreibeTagBis.clear();
				}
				ke.consume();
			}
		});
		searchCategory_other_nummer.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_other_nummer.getText().matches("[0-9]{1,}")) {
					if (getParsedInt(searchCategory_other_nummer.getText()) < 0) {
						viewHelper.showErrorMessage(
								"Nummer muss eine nicht-negative Zahl sein und darf keine Buchstaben enthalten.");
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage(
							"Nummer muss eine nicht-negative Zahl sein und darf keine Buchstaben enthalten.");
					searchCategory_other_nummer.clear();
				}
				ke.consume();
			}
		});
		searchCategory_other_nummerhess.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_other_nummerhess.getText().matches("[0-9]{1,}")) {
					if (getParsedInt(searchCategory_other_nummerhess.getText()) < 0) {
						viewHelper.showErrorMessage(
								"Nummer Hess muss eine nicht-negative Zahl sein und darf keine Buchstaben enthalten.");
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage(
							"Nummer Hess muss eine nicht-negative Zahl sein und darf keine Buchstaben enthalten.");
					searchCategory_other_nummerhess.clear();
				}
				ke.consume();
			}
		});
		searchCategory_other_seite.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_other_seite.getText().matches("[0-9]{1,}")) {
					if (getParsedInt(searchCategory_other_seite.getText()) < 0) {
						viewHelper.showErrorMessage(
								"Seite muss eine nicht-negative Zahl sein und darf keine Buchstaben enthalten.");
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage(
							"Seite muss eine nicht-negative Zahl sein und darf keine Buchstaben enthalten.");
					searchCategory_other_seite.clear();
				}
				ke.consume();
			}
		});
	}

	private void setTableViewEventHandlers() {
		resultTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (resultTable.getSelectionModel().getSelectedItem() != null) {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
						System.out.println(
								"Suche Details von ID " + resultTable.getSelectionModel().getSelectedItem().get(0));
						startSearchForSinglePerson(resultTable.getSelectionModel().getSelectedItem().get(0));
					}
				}
				event.consume();
			}
		});
	}

	private void setUpBindings() {
		BooleanBinding booleanBindingEinschreibeHinweis = new BooleanBinding() {

			{
				super.bind(searchCategory_study_einschreibeTagVon.textProperty(),
						searchCategory_study_einschreibeTagBis.textProperty(),
						searchCategory_study_einschreibeMonatVon.textProperty(),
						searchCategory_study_einschreibeMonatBis.textProperty(),
						searchCategory_study_einschreibeJahrVon.textProperty(),
						searchCategory_study_einschreibeJahrBis.textProperty());
			}

			@Override
			protected boolean computeValue() {
				return (!searchCategory_study_einschreibeTagVon.getText().isEmpty()
						|| !searchCategory_study_einschreibeTagBis.getText().isEmpty()
						|| !searchCategory_study_einschreibeMonatVon.getText().isEmpty()
						|| !searchCategory_study_einschreibeMonatBis.getText().isEmpty())
						&& (searchCategory_study_einschreibeJahrVon.getText().isEmpty()
								|| searchCategory_study_einschreibeJahrBis.getText().isEmpty());
			}
		};
		searchCategory_study_einschreibeHinweis.visibleProperty().bind(booleanBindingEinschreibeHinweis);
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
		resultTable.getColumns().clear();
		resultTable.getItems().clear();
		String[] input = generateArrayWithInputValues();
		int[] sources = generateArrayWithSourceFieldKeys();
		try {
			boolean couldSearchBeStarted = searchCtrl.executeSearch(input, sources,
					search_useOrConjunction.isSelected(), search_useOpenSearch.isSelected());

			if (!couldSearchBeStarted) {
				viewHelper.showErrorMessage(
						"Die Suche konnte nicht gestartet werden, da keine Eingaben gefunden wurden.");
			} else {
				setLabelSource();
			}
		} catch (SearchException e) {
			e.printStackTrace();
			if (e.getMessage().isEmpty()) {
				viewHelper.showErrorMessage("Bei der Suche ist ein Fehler aufgetreten.");
			} else {
				viewHelper.showErrorMessage(e.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param string
	 */
	private void startSearchForSinglePerson(String id) {
		if (id == null || id.isEmpty()) {
			throw new InputMismatchException(
					"Übergebene ID ist leer oder hat keinen Wert, es können keine Details zur Person gesucht werden.");
		}
		UserQueryImpl idQuery = new UserQueryImpl(id);
		try {
			searchCtrl.startSinglePersonSearch(idQuery);
		} catch (SearchException e) {
			e.printStackTrace();
			viewHelper.showErrorMessage(
					"Es können keine Detailinformationen für diese Person angezeigt werden.\n" + e.getMessage());
		}
	}

	private void fillResultTable(CachedRowSet resultCachedRowSet) {
		if (resultCachedRowSet == null || resultCachedRowSet.size() == 0) {
			viewHelper.showInfo("Die Suche hat kein Ergebnis zurückgeliefert.");
		} else {
			ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
			result = resultCachedRowSet;
			try {
				result.beforeFirst();
				for (int i = 0; i < result.getMetaData().getColumnCount(); i++) {
					String columnName = result.getMetaData().getColumnName(i + 1);
					if (columnName.contains("_")) {
						columnName = columnName.substring(0, columnName.indexOf("_")) + " "
								+ columnName.substring(columnName.indexOf("_") + 1);
					}
					TableColumn<ObservableList<String>, String> col = new TableColumn<ObservableList<String>, String>(
							columnName);
					if ("DATUMSFELDERGESETZT".equals(columnName)) {
						col.setVisible(false);
					}
					final int j = i;
					final String columnNameForCheck = columnName;
					col.setCellValueFactory(
							new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

								@Override
								public ObservableValue<String> call(
										CellDataFeatures<ObservableList<String>, String> param) {
									Optional<String> optionalParam = Optional.ofNullable(param.getValue().get(j));
									if (optionalParam.isPresent() && columnNameForCheck.equals("DATUM")) {
										return new SimpleStringProperty(viewHelper.formatDateForDisplaying(
												param.getValue().get(j), param.getValue().get(j + 1)));
									} else if (optionalParam.isPresent()) {
										return new SimpleStringProperty(optionalParam.get());
									} else {
										return new SimpleStringProperty();
									}
								}
							});
					resultTable.getColumns().add(col);
				}
				// set visibility of first column with ID to false
				resultTable.getColumns().get(0).setVisible(false);
				result.beforeFirst();
				while (result.next()) {
					ObservableList<String> row = FXCollections.observableArrayList();
					for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
						row.add(result.getString(i));
					}
					data.add(row);
				}
				result.first();
				resultTable.setItems(data);
			} catch (SQLException e) {
				viewHelper.showErrorMessage("Bei der Anzeige der gefundenen Datensätze ist ein Fehler aufgetreten.");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable observable, Object updateData) {
		if (observable != null) {
			if (observable instanceof Model && updateData instanceof CachedRowSet) {
				fillResultTable((CachedRowSet) updateData);
			}
		}
	}

	/**
	 * Builds an array of all input fields and their value at the moment of
	 * building. The order of inputs corresponds with the order of the keys in
	 * {@link #generateArraysWithSourceFieldKeys()}. The method does not check,
	 * if a input was done or if the input field was left empty.
	 * 
	 * @return the array with all inputs
	 */
	private String[] generateArrayWithInputValues() {
		String[] inputFields = new String[inputFieldCounter];
		try {
			inputFields[0] = searchCategory_person_anrede.getText();
			inputFields[1] = searchCategory_person_anredenorm.getText();
			inputFields[2] = searchCategory_person_titel.getText();
			inputFields[3] = searchCategory_person_titelnorm.getText();
			inputFields[4] = searchCategory_person_vornameinput.getText();
			inputFields[5] = searchCategory_person_nachnameinput.getText();
			inputFields[6] = String.valueOf(searchCategory_personExtended_adeliger.isSelected());
			inputFields[7] = String.valueOf(searchCategory_personExtended_jesuit.isSelected());
			inputFields[8] = searchCategory_personExtended_wirtschaftinput.getText();
			inputFields[9] = searchCategory_personExtended_ortinput.getText();
			inputFields[10] = searchCategory_study_studienfachinput.getText();
			inputFields[11] = searchCategory_study_fakultaet.getText();
			inputFields[12] = searchCategory_study_seminarinput.getText();
			inputFields[13] = String.valueOf(searchCategory_study_graduiert.isSelected());
			inputFields[14] = searchCategory_study_studienjahrVon.getText();
			inputFields[15] = searchCategory_study_studienjahrBis.getText();
			inputFields[16] = getEinschreibungVon();
			inputFields[17] = getEinschreibungBis();
			inputFields[18] = searchCategory_other_zusaetzeinput.getText();
			inputFields[19] = searchCategory_other_fundort.getText();
			inputFields[20] = searchCategory_other_anmerkungen.getText();
			inputFields[21] = searchCategory_other_nummer.getText();
			inputFields[22] = searchCategory_other_seite.getText();
			inputFields[23] = searchCategory_other_nummerhess.getText();
		} catch (IllegalArgumentException e) {
			viewHelper.showErrorMessage(e.getMessage());
		}
		return inputFields;
	}

	/**
	 * 
	 * @return
	 */
	private String getEinschreibungVon() {
		String jahr = "yyyy";
		String monat = "mm";
		String tag = "dd";

		if (!searchCategory_study_einschreibeJahrVon.getText().isEmpty()) {
			jahr = searchCategory_study_einschreibeJahrVon.getText();
			if (!searchCategory_study_einschreibeMonatVon.getText().isEmpty()) {
				monat = searchCategory_study_einschreibeMonatVon.getText();
			}
			if (!searchCategory_study_einschreibeTagVon.getText().isEmpty()) {
				tag = searchCategory_study_einschreibeTagVon.getText();
			}
		}

		return jahr + "-" + monat + "-" + tag;
	}

	private String getEinschreibungBis() {
		String jahr = "yyyy";
		String monat = "mm";
		String tag = "dd";

		if (!searchCategory_study_einschreibeJahrBis.getText().isEmpty()) {
			jahr = searchCategory_study_einschreibeJahrBis.getText();
			if (!searchCategory_study_einschreibeMonatBis.getText().isEmpty()) {
				monat = searchCategory_study_einschreibeMonatBis.getText();
			}
			if (!searchCategory_study_einschreibeTagBis.getText().isEmpty()) {
				tag = searchCategory_study_einschreibeTagBis.getText();
			}
		}
		return jahr + "-" + monat + "-" + tag;
	}

	private int getParsedInt(String input) {
		if (input == null || input.isEmpty()) {
			throw new IllegalArgumentException("Ein Fehler bei der Verarbeitung der Zahleingabe ist aufgetreten.");
		}
		int result;
		try {
			result = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Die Eingabe von \"" + input + "\" in einem Zahlenfeld ist nicht gültig.");
		}
		return result;
	}

	/**
	 * The array with {@link SourceKeys} is generated.
	 */
	private int[] generateArrayWithSourceFieldKeys() {
		int[] inputSourceKey = new int[inputFieldCounter];

		if (viewHelper.getSourceKeyByValueAsString(search_sourcekey_selection.getValue()) != SourceKeys.NORM) {
			inputSourceKey[0] = SourceKeys.NO_SOURCE;
			inputSourceKey[2] = SourceKeys.NO_SOURCE;
		} else {
			inputSourceKey[0] = SourceKeys.NORM;
			inputSourceKey[2] = SourceKeys.NORM;
		}
		inputSourceKey[1] = SourceKeys.NORM;
		inputSourceKey[3] = SourceKeys.NORM;
		inputSourceKey[4] = viewHelper.getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[5] = viewHelper.getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[6] = SourceKeys.NO_SOURCE;
		inputSourceKey[7] = SourceKeys.NO_SOURCE;
		inputSourceKey[8] = viewHelper.getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[9] = viewHelper.getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[10] = viewHelper.getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[11] = SourceKeys.NO_SOURCE;
		inputSourceKey[12] = viewHelper.getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[13] = SourceKeys.NO_SOURCE;
		inputSourceKey[14] = SourceKeys.NO_SOURCE;
		inputSourceKey[15] = SourceKeys.NO_SOURCE;
		inputSourceKey[16] = SourceKeys.NO_SOURCE;
		inputSourceKey[17] = SourceKeys.NO_SOURCE;
		inputSourceKey[18] = viewHelper.getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[19] = SourceKeys.NO_SOURCE;
		inputSourceKey[20] = SourceKeys.NO_SOURCE;
		inputSourceKey[21] = SourceKeys.NO_SOURCE;
		inputSourceKey[22] = SourceKeys.NO_SOURCE;
		inputSourceKey[23] = SourceKeys.NO_SOURCE;

		return inputSourceKey;
	}

	private void setLabelSource() {
		String labelText = search_sourcekey_selection.getValue();
		if (labelText == null) {
			labelText = "Keine Quelle ausgewählt.";
		} else if ("normalisiert".equals(labelText)) {
			labelText += " (Hinweis: Datensätze ohne Normalisierung werden nicht angezeigt.)";
		}
		sourceLabelName.set("Quelle: " + labelText);
		search_sourceLabel.setText(sourceLabelName.getValueSafe());
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
		searchCategory_personExtended_adeliger.setSelected(false);
		searchCategory_personExtended_jesuit.setSelected(false);
		searchCategory_personExtended_wirtschaftinput.clear();
		searchCategory_personExtended_ortinput.clear();
		searchCategory_study_studienfachinput.clear();
		searchCategory_study_fakultaet.clear();
		searchCategory_study_seminarinput.clear();
		searchCategory_study_graduiert.setSelected(false);
		searchCategory_study_studienjahrVon.clear();
		searchCategory_study_studienjahrBis.clear();
		searchCategory_study_einschreibeTagVon.clear();
		searchCategory_study_einschreibeMonatVon.clear();
		searchCategory_study_einschreibeJahrVon.clear();
		searchCategory_study_einschreibeTagBis.clear();
		searchCategory_study_einschreibeMonatBis.clear();
		searchCategory_study_einschreibeJahrBis.clear();
		searchCategory_other_zusaetzeinput.clear();
		searchCategory_other_fundort.clear();
		searchCategory_other_anmerkungen.clear();
		searchCategory_other_nummer.clear();
		searchCategory_other_seite.clear();
		searchCategory_other_nummerhess.clear();
		search_sourcekey_selection.getSelectionModel().clearSelection();
		search_useOrConjunction.setSelected(false);
		search_useOpenSearch.setSelected(false);
		System.out.println("");
	}
}
