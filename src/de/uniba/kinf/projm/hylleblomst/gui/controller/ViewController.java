package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
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
public class ViewController implements Observer, Initializable {

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
						searchCategory_study_studienjahrBis.clear();
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
						viewHelper.showErrorMessage(
								"Einschreibejahr muss eine vierstellige Eingabe zwischen 0 und 2015 sein.");
						searchCategory_study_einschreibeJahrVon.clear();
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage(
							"Einschreibejahr muss eine vierstellige Eingabe zwischen 0 und 2015 sein.");
					searchCategory_study_einschreibeJahrVon.clear();
				}
				ke.consume();
			}
		});
		searchCategory_study_einschreibeMonatVon.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_study_einschreibeMonatVon.getText().matches("[0-9][0-2]?")) {
					if (getParsedInt(searchCategory_study_einschreibeMonatVon.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeMonatVon.getText()) > 12) {
						viewHelper.showErrorMessage("Einschreibemonat muss eine Zahl von 1 bis 12 sein.");
						searchCategory_study_einschreibeMonatVon.clear();
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
				if (searchCategory_study_einschreibeTagVon.getText().matches("[0-9][0-9]?")) {
					if (getParsedInt(searchCategory_study_einschreibeTagVon.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeTagVon.getText()) > 31) {
						viewHelper.showErrorMessage("Einschreibetag muss eine Zahl von 1 bis 31 sein.");
						searchCategory_study_einschreibeTagVon.clear();
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
						viewHelper.showErrorMessage(
								"Einschreibejahr muss eine vierstellige Eingabe zwischen 0 und 2015 sein.");
						searchCategory_study_einschreibeJahrBis.clear();
					}
				} else if (ke.getCode() != KeyCode.TAB && ke.getCode() != KeyCode.BACK_SPACE
						&& ke.getCode() != KeyCode.SHIFT && ke.getCode() != KeyCode.DELETE) {
					viewHelper.showErrorMessage(
							"Einschreibejahr muss eine vierstellige Eingabe zwischen 0 und 2015 sein.");
					searchCategory_study_einschreibeJahrBis.clear();
				}
				ke.consume();
			}
		});
		searchCategory_study_einschreibeMonatBis.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				if (searchCategory_study_einschreibeMonatBis.getText().matches("[0-9][0-2]?")) {
					if (getParsedInt(searchCategory_study_einschreibeMonatBis.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeMonatBis.getText()) > 12) {
						viewHelper.showErrorMessage("Einschreibemonat muss eine Zahl von 1 bis 12 sein.");
						searchCategory_study_einschreibeMonatBis.clear();
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
				if (searchCategory_study_einschreibeTagBis.getText().matches("[0-9][0-9]?")) {
					if (getParsedInt(searchCategory_study_einschreibeTagBis.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeTagBis.getText()) > 31) {
						viewHelper.showErrorMessage("Einschreibetag muss eine Zahl von 1 bis 31 sein.");
						searchCategory_study_einschreibeTagBis.clear();
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
	 * Collects all data from the input, gets all source keys and starts the
	 * search by calling the corresponding function of the
	 * {@link SearchContoller}. If the search could not be started, a error
	 * message is shown to the user. If an exception occurs while the search is
	 * executed, again an error message is shown.
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
	 * Starts the search for a single person, which then will be shown in the
	 * details view, therefore a {@link DetailsViewController} is used. To start
	 * the search for a single person, the id ("Nummer") of this person is
	 * needed.
	 * <p>
	 * <b>Precondition</b>
	 * <ul>
	 * <li>the {@code id} must not be null or an empty string</li>
	 * </ul>
	 * </p>
	 * 
	 * @param string
	 *            the id of the person
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

	/**
	 * Using the passed {@link CachedRowSet}, the {@link TableView} of the user
	 * interface is filled with the search result. Depending on the number of
	 * columns, the {@link TableView} is generated dynamically. Several columns
	 * with additional information (like person id or if date fields were
	 * manipulated while importing the data) are hidden, the user gets the data
	 * displayed in the original state.
	 * 
	 * <p>
	 * <b>Precondition</b>
	 * <ul>
	 * <li>the {@link CachedRowSet} must not be null or empty (
	 * {@code size != 0})</li>
	 * </ul>
	 * </p>
	 * 
	 * @param resultCachedRowSet
	 *            the {@link CachedRowSet} with the search result
	 */
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

	/**
	 * Implemented by the {@link ControllerInterface}, this method is needed to
	 * get the data from the model for filling the result table.
	 */
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
			inputFields[16] = getEinschreibungAsFormattedString(searchCategory_study_einschreibeJahrVon,
					searchCategory_study_einschreibeMonatVon, searchCategory_study_einschreibeTagVon);
			inputFields[17] = getEinschreibungAsFormattedString(searchCategory_study_einschreibeJahrBis,
					searchCategory_study_einschreibeMonatBis, searchCategory_study_einschreibeTagBis);
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
	 * Gets the input of years of "Einschreibung" as formatted string as needed
	 * for a correct search. The given input fields are checked if an input is
	 * done. If true, the input is used to build the return string. The year
	 * needs to have 4 characters, so if there are less, the first postions are
	 * filled with 0 until the year has a length of 4.
	 * 
	 * <p>
	 * <b>Precondition</b>
	 * <ul>
	 * <li>the {@link TextFields} must not be null but can be empty</li>
	 * </ul>
	 * </p>
	 * 
	 * @param inputYear
	 *            the {@link TextField} with the input of the year
	 * @param inputMonth
	 *            the {@link TextField} with the input of the year
	 * @param inputDay
	 *            the {@link TextField} with the input of the year
	 * @return the string with the input in the form {@code yyyy-mm-dd}
	 */
	private String getEinschreibungAsFormattedString(TextField inputYear, TextField inputMonth, TextField inputDay) {
		if (inputYear == null || inputMonth == null || inputDay == null) {
			throw new InputMismatchException(
					"Die Eingabe in den Feldern Einschreibung konnte nicht verarbeitet werden, da eines der Felder keinen Wert hat.");
		}
		String year = "yyyy";
		String month = "mm";
		String day = "dd";

		if (!inputYear.getText().isEmpty()) {
			year = "0000" + inputYear.getText();
			year = year.substring(year.length() - 4);
			if (!inputMonth.getText().isEmpty()) {
				month = inputMonth.getText();
			}
			if (!inputDay.getText().isEmpty()) {
				day = inputDay.getText();
			}
		}
		System.out.println(year + "-" + month + "-" + day);
		return year + "-" + month + "-" + day;
	}

	/**
	 * Gets a string and returns it if possible as a parsed {@code int}. If the
	 * parsing could not be done, an exception is thrown with a problem
	 * description.
	 * 
	 * @param input
	 *            the string which needs to be parsed
	 * @return the parsed string as {@code int}
	 */
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
	 * 
	 * The array with {@link SourceKeys} is generated.
	 *
	 * @return the array with all {@link SourceKeys}
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

	/**
	 * When the search was started and the user selected a source, the name of
	 * the search is shown beneath the {@link TableView}. This methods sets the
	 * {@link Label} which displays the source.
	 */
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
	}
}
