package de.uniba.kinf.projm.hylleblomst.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.exceptions.ViewException;
import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logicImpl.SearchInitiatorImpl;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Controller for the graphical user interface.
 */
public class ViewController implements ControllerInterface, Initializable {

	private SearchInitiator initiator;

	private Model model;

	private UIHelper ui;

	private SearchController searchCtrl;

	private int inputFieldCounter = 24;

	private StringProperty sourceLabelName = new SimpleStringProperty("Quelle: ");

	@FXML
	private BorderPane root;

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
	private MenuItem mainMenu_help_help;

	@FXML
	private MenuItem mainMenu_help_about;

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
	private TextArea infoArea;

	/**
	 * Constructor for a new Controller. When called, the array with
	 * {@link SearchFieldKeys} and {@link SourceKeys} is build and set, the
	 * instances of the {@link SearchInitiatorImpl}, {@link UIHelper} and
	 * {@link SearchController} are instantiated.
	 * 
	 */
	public ViewController() {
		ui = new UIHelper();
		initiator = new SearchInitiatorImpl();
		model = new Model(initiator);
		model.addObserver(this);
		searchCtrl = new SearchController(inputFieldCounter, model);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clearResultTable();
		setEventHandlers();
		setUpLabels();
		setUpBindings();
		searchCategories.setExpandedPane(searchCategory_person);
		search_sourceLabel.setText(sourceLabelName.getValueSafe());
	}

	private void clearResultTable() {
		resultTable.getColumns().clear();
		resultTable.getItems().clear();
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
	 */
	private void setEventHandlers() {
		setMenuEventHandlers();
		setNumericalInputEventHandlers();
		setTableViewEventHandlers();
	}

	private void setMenuEventHandlers() {
		mainMenu_file_close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				closeWindow();
				event.consume();
			}
		});
		mainMenu_file_export.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ui.functionNotAvailable();
				event.consume();
			}
		});
		mainMenu_database_setupDatabase.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				model.setUpDatabase();
				event.consume();
			}
		});
		mainMenu_database_importData.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				model.importData();
				event.consume();
			}
		});
		mainMenu_database_clearDatabase.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				model.clearDatabase();
				event.consume();
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
				try {
					if (getParsedInt(searchCategory_study_studienjahrVon.getText()) < 0
							|| getParsedInt(searchCategory_study_studienjahrVon.getText()) > 2015) {
						ke.consume();
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					ui.showErrorMessage("Studienjahr muss eine Zahl zwischen 0 und 2015 sein.");
					searchCategory_study_studienjahrVon.clear();
				}
			}
		});
		searchCategory_study_studienjahrBis.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				try {
					if (getParsedInt(searchCategory_study_studienjahrBis.getText()) < 0
							|| getParsedInt(searchCategory_study_studienjahrBis.getText()) > 2015) {
						ke.consume();
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					ui.showErrorMessage("Studienjahr muss eine Zahl zwischen 0 und 2015 sein.");
					searchCategory_study_studienjahrBis.clear();
				}
			}
		});
		searchCategory_study_einschreibeJahrVon.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				try {
					if (getParsedInt(searchCategory_study_einschreibeJahrVon.getText()) < 0
							|| getParsedInt(searchCategory_study_einschreibeJahrVon.getText()) > 2015) {
						ke.consume();
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					ui.showErrorMessage("Einschreibejahr muss eine Zahl zwischen 0 und 2015 sein.");
					searchCategory_study_einschreibeJahrVon.clear();
				}
			}
		});
		searchCategory_study_einschreibeMonatVon.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				try {
					if (getParsedInt(searchCategory_study_einschreibeMonatVon.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeMonatVon.getText()) > 12) {
						ke.consume();
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					ui.showErrorMessage("Einschreibemonat muss eine Zahl von 1 bis 12 sein.");
					searchCategory_study_einschreibeMonatVon.clear();
				}
			}
		});
		searchCategory_study_einschreibeTagVon.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				try {
					if (getParsedInt(searchCategory_study_einschreibeTagVon.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeTagVon.getText()) > 31) {
						ke.consume();
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					ui.showErrorMessage("Einschreibetag muss eine Zahl von 1 bis 31 sein.");
					searchCategory_study_einschreibeTagVon.clear();
				}
			}
		});
		searchCategory_study_einschreibeJahrBis.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				try {
					if (getParsedInt(searchCategory_study_einschreibeJahrBis.getText()) < 0
							|| getParsedInt(searchCategory_study_einschreibeJahrBis.getText()) > 2015) {
						ke.consume();
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					ui.showErrorMessage("Einschreibejahr muss eine Zahl zwischen 0 und 2015 sein.");
					searchCategory_study_einschreibeJahrBis.clear();
				}
			}
		});
		searchCategory_study_einschreibeMonatBis.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				try {
					if (getParsedInt(searchCategory_study_einschreibeMonatBis.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeMonatBis.getText()) > 12) {
						ke.consume();
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					ui.showErrorMessage("Einschreibemonat muss eine Zahl von 1 bis 12 sein.");
					searchCategory_study_einschreibeMonatBis.clear();
				}
			}
		});
		searchCategory_study_einschreibeTagBis.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				try {
					if (getParsedInt(searchCategory_study_einschreibeTagBis.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeTagBis.getText()) > 31) {
						ke.consume();
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					ui.showErrorMessage("Einschreibetag muss eine Zahl von 1 bis 31 sein.");
					searchCategory_study_einschreibeTagBis.clear();
				}
			}
		});
	}

	private void setTableViewEventHandlers() {
		resultTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					System.out.println(
							"Suche Details von ID " + resultTable.getSelectionModel().getSelectedItem().get(0));
					startSearchForSinglePerson(resultTable.getSelectionModel().getSelectedItem().get(0));
				}
				event.consume();
			}
		});
	}

	private void setUpLabels() {
		searchCategory_study_einschreibeHinweis
				.setText("Hinweis: Für eine erfolgreiche Suche muss jeweils mindestens das Jahr ausgefüllt sein.");
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
								| searchCategory_study_einschreibeJahrBis.getText().isEmpty());
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
				ui.showErrorMessage("Die Suche konnte nicht gestartet werden, da keine Eingaben gefunden wurden.");
			} else {
				setLabelSource();
			}
		} catch (ViewException e) {
			e.printStackTrace();
			if (e.getMessage().isEmpty()) {
				ui.showErrorMessage("Bei der Suche ist ein Fehler aufgetreten.");
			} else {
				ui.showErrorMessage(e.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param string
	 */
	private void startSearchForSinglePerson(String string) {
		try {
			searchCtrl.startSinglePersonSearch(string);
		} catch (Exception e) {
			e.printStackTrace();
			ui.showErrorMessage(
					"Es können keine Detailinformationen für diese Person angezeigt werden.\n" + e.getMessage());
		}
	}

	void fillResultTable(CachedRowSet result) {
		if (result == null || result.size() == 0) {
			ui.showInfo("Die Suche hat kein Ergebnis zurückgeliefert.");
		} else {
			ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
			try {
				result.first();
				for (int i = 0; i < result.getMetaData().getColumnCount(); i++) {
					final int j = i;
					String columnName = result.getMetaData().getColumnName(i + 1);
					if (columnName.contains("_")) {
						columnName = columnName.substring(0, columnName.indexOf("_")) + " "
								+ columnName.substring(columnName.indexOf("_") + 1);
					}
					TableColumn<ObservableList<String>, String> col = new TableColumn<ObservableList<String>, String>(
							columnName);
					col.setCellValueFactory(
							new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {

								@Override
								public ObservableValue<String> call(
										CellDataFeatures<ObservableList<String>, String> param) {
									Optional<String> optionalParam = Optional.ofNullable(param.getValue().get(j));
									if (optionalParam.isPresent()) {
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
				result.first();
				while (result.next()) {
					ObservableList<String> row = FXCollections.observableArrayList();
					for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
						row.add(result.getString(i));
					}
					data.add(row);
				}
				result.close();
				resultTable.setItems(data);
			} catch (SQLException e) {
				ui.showErrorMessage("Bei der Anzeige der gefundenen Datensätze ist ein Fehler aufgetreten.");
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
			ui.showErrorMessage(e.getMessage());
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
		if (input.isEmpty() || input == null) {
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

		inputSourceKey[0] = SourceKeys.STANDARD;
		inputSourceKey[1] = SourceKeys.NORM;
		inputSourceKey[2] = SourceKeys.STANDARD;
		inputSourceKey[3] = SourceKeys.NORM;
		inputSourceKey[4] = getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[5] = SourceKeys.STANDARD;
		inputSourceKey[6] = SourceKeys.NO_SOURCE;
		inputSourceKey[7] = SourceKeys.NO_SOURCE;
		inputSourceKey[8] = getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[9] = getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[10] = getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[11] = SourceKeys.NO_SOURCE;
		inputSourceKey[12] = getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[13] = SourceKeys.NO_SOURCE;
		inputSourceKey[14] = SourceKeys.NO_SOURCE;
		inputSourceKey[15] = SourceKeys.NO_SOURCE;
		inputSourceKey[16] = SourceKeys.NO_SOURCE;
		inputSourceKey[17] = SourceKeys.NO_SOURCE;
		inputSourceKey[18] = getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[19] = SourceKeys.NO_SOURCE;
		inputSourceKey[20] = SourceKeys.NO_SOURCE;
		inputSourceKey[21] = SourceKeys.NO_SOURCE;
		inputSourceKey[22] = SourceKeys.NO_SOURCE;
		inputSourceKey[23] = SourceKeys.NO_SOURCE;

		return inputSourceKey;
	}

	/**
	 * Checks the value of the selection for the source of the first name and
	 * returns the corresponding {@link SourceKey}.
	 * 
	 * @param value
	 *            the user input as String
	 * @return the corresponding {@link SourceKey} as {@code String}
	 */
	private int getSourceKeyByValueAsString(String value) {
		if ("Standard".equals(value)) {
			return SourceKeys.STANDARD;
		} else if ("normalisiert".equals(value)) {
			return SourceKeys.NORM;
		} else if ("Abweichung normalisiert".equals(value)) {
			return SourceKeys.ORT_NORM_AB;
		} else if ("HS B (AUB, I 11)".equals(value)) {
			return SourceKeys.HSB_AUB_I11;
		} else if ("HS C (AUB, I 13/1)".equals(value)) {
			return SourceKeys.HSC_AUB_I131;
		} else if ("HS D (AUB, I 13/2)".equals(value)) {
			return SourceKeys.HSD_AUB_I132;
		} else if ("HS E (AUB, I 9)".equals(value)) {
			return SourceKeys.HSE_AUB_I9;
		} else if ("HS F (AUB, I 8)".equals(value)) {
			return SourceKeys.HSF_AUB_I8;
		} else if ("HS G (AUB, I 6)".equals(value)) {
			return SourceKeys.HSG_AUB_I6;
		} else if ("HS H (AEB, Rep. I, Nr. 321)".equals(value)) {
			return SourceKeys.HSH_AEB_I321;
		} else if ("HS I (SB Bamberg, Msc.Add.3a)".equals(value)) {
			return SourceKeys.HSI_SB_3a;
		} else if ("HS J (SB Bamberg, Msc.Add.3)".equals(value)) {
			return SourceKeys.HSJ_3;
		} else if ("AUB, V E 38".equals(value)) {
			return SourceKeys.AUB_V_E38;
		}
		return SourceKeys.NO_SELECTION;
	}

	private void setLabelSource() {
		String labelText = search_sourcekey_selection.getValue();
		if (labelText == null) {
			labelText = "Keine Quelle ausgewählt.";
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
		search_sourcekey_selection.setValue(null);
		search_useOrConjunction.setSelected(false);
		search_useOpenSearch.setSelected(false);
		infoArea.clear();
	}

	/**
	 * Closes the window when the users submits the action.
	 */
	@FXML
	private void closeWindow() {
		Stage stage = (Stage) root.getScene().getWindow();
		if (ui.askForClosingWindow()) {
			stage.close();
			System.exit(0);
		}
	}

	/**
	 * Opens an alert window with some random information.
	 */
	@FXML
	private void showInfo() {
		ui.showApplicationInfo();
	}
}
