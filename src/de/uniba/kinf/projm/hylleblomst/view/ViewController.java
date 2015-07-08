package de.uniba.kinf.projm.hylleblomst.view;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.logic.PersonItem;
import de.uniba.kinf.projm.hylleblomst.logic.UserQueries;
import de.uniba.kinf.projm.hylleblomst.logicImpl.SearchInitiatorImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Controller for the graphical user interface.
 * 
 * @author ca
 *
 */
public class ViewController implements ControllerInterface, Initializable {
	/**
	 * UIHelper supports a nice user interaction.
	 */
	UIHelper ui;

	/**
	 * Implements the logic of preparing the user input for passing it to the
	 * {@link SearchInitiatorImpl}.
	 */
	private SearchController searchCtrl;

	/**
	 * Stores the number of input fields for usable generation of input field
	 * arrays.
	 */
	private int inputFieldCounter = 23;

	/**
	 * 
	 */
	private ArrayList<SearchFieldKeys> usedSearchFieldKeys = new ArrayList<SearchFieldKeys>();

	private StringProperty sourceLabelName = new SimpleStringProperty("Quelle: ");

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
	ComboBox<String> search_sourcekey_selection;

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
	ComboBox<String> searchCategory_person_vornameselection;

	@FXML
	TextField searchCategory_person_vornameinput;

	@FXML
	ComboBox<String> searchCategory_person_nachnameselection;

	@FXML
	TextField searchCategory_person_nachnameinput;

	@FXML
	TitledPane searchCategory_personExtended;

	@FXML
	CheckBox searchCategory_personExtended_adeliger;

	@FXML
	CheckBox searchCategory_personExtended_jesuit;

	@FXML
	ComboBox<String> searchCategory_personExtended_wirtschaftselection;

	@FXML
	TextField searchCategory_personExtended_wirtschaftinput;

	@FXML
	ComboBox<String> searchCategory_personExtended_ortselection;

	@FXML
	TextField searchCategory_personExtended_ortinput;

	@FXML
	TitledPane searchCategory_study;

	@FXML
	ComboBox<String> searchCategory_study_studienfachselection;

	@FXML
	TextField searchCategory_study_studienfachinput;

	@FXML
	TextField searchCategory_study_fakultaet;

	@FXML
	ComboBox<String> searchCategory_study_seminarselection;

	@FXML
	TextField searchCategory_study_seminarinput;

	@FXML
	CheckBox searchCategory_study_graduiert;

	@FXML
	TextField searchCategory_study_studienjahrVon;

	@FXML
	TextField searchCategory_study_studienjahrBis;

	@FXML
	TextField searchCategory_study_einschreibeTag;

	@FXML
	TextField searchCategory_study_einschreibeMonat;

	@FXML
	TextField searchCategory_study_einschreibeJahr;

	@FXML
	TitledPane searchCategory_other;

	@FXML
	ComboBox<String> searchCategory_other_zusaetzeselection;

	@FXML
	TextField searchCategory_other_zusaetzeinput;

	@FXML
	TextField searchCategory_other_fundort;

	@FXML
	TextField searchCategory_other_anmerkungen;

	@FXML
	TextField searchCategory_other_nummer;

	@FXML
	TextField searchCategory_other_seite;

	@FXML
	TextField searchCategory_other_nummerhess;

	@FXML
	Button searchMenu_clearSearch;

	@FXML
	Button searchMenu_search;

	@FXML
	TableView resultTable;

	@FXML
	Label search_sourceLabel;

	@FXML
	TextArea infoArea;

	/**
	 * Constructor for a new Controller. When called, the array with
	 * {@link SearchFieldKeys} and {@link SourceKeys} is build and set, the
	 * instances of the {@link SearchInitiatorImpl}, {@link UIHelper} and
	 * {@link SearchController} are instantiated.
	 * 
	 */
	public ViewController() {
		ui = new UIHelper();
		searchCtrl = new SearchController(this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clearResultTable();
		setEventHandlers();
		searchCategories.setExpandedPane(searchCategory_person);
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
		setNumericalInputEventHandlers();
		setTableViewEventHandlers();
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
		searchCategory_study_einschreibeJahr.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				try {
					if (getParsedInt(searchCategory_study_einschreibeJahr.getText()) < 0
							|| getParsedInt(searchCategory_study_einschreibeJahr.getText()) > 2015) {
						ke.consume();
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					ui.showErrorMessage("Einschreibejahr muss eine Zahl zwischen 0 und 2015 sein.");
					searchCategory_study_einschreibeJahr.clear();
				}
			}
		});
		searchCategory_study_einschreibeMonat.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				try {
					if (getParsedInt(searchCategory_study_einschreibeMonat.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeMonat.getText()) > 12) {
						ke.consume();
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					ui.showErrorMessage("Einschreibemonat muss eine Zahl von 1 bis 12 sein.");
					searchCategory_study_einschreibeMonat.clear();
				}
			}
		});
		searchCategory_study_einschreibeTag.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {
				try {
					if (getParsedInt(searchCategory_study_einschreibeTag.getText()) < 1
							|| getParsedInt(searchCategory_study_einschreibeTag.getText()) > 31) {
						ke.consume();
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					ui.showErrorMessage("Einschreibetag muss eine Zahl von 1 bis 31 sein.");
					searchCategory_study_einschreibeTag.clear();
				}
			}
		});
	}

	private void setTableViewEventHandlers() {
		resultTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown()) {
					// startSearchForSinglePerson(resultTable.getSelectionModel().getSelectedItem().getId());
				}
				event.consume();
			}
		});
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
			inputFields[17] = searchCategory_other_zusaetzeinput.getText();
			inputFields[18] = searchCategory_other_fundort.getText();
			inputFields[19] = searchCategory_other_anmerkungen.getText();
			inputFields[20] = searchCategory_other_nummer.getText();
			inputFields[21] = searchCategory_other_seite.getText();
			inputFields[22] = searchCategory_other_nummerhess.getText();
		} catch (InputMismatchException | NumberFormatException e) {
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

		if (!searchCategory_study_einschreibeJahr.getText().isEmpty()) {
			jahr = searchCategory_study_einschreibeJahr.getText();
		}
		if (!searchCategory_study_einschreibeMonat.getText().isEmpty()) {
			monat = searchCategory_study_einschreibeMonat.getText();
		}
		if (!searchCategory_study_einschreibeTag.getText().isEmpty()) {
			tag = searchCategory_study_einschreibeTag.getText();
		}

		return jahr + "-" + monat + "-" + tag;
	}

	private int getParsedInt(String input) {
		if ("".equals(input) || input == null) {
			throw new InputMismatchException("Ein Fehler bei der Verarbeitung der Zahleingabe ist aufgetreten.");
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
		inputSourceKey[17] = getSourceKeyByValueAsString(search_sourcekey_selection.getValue());
		inputSourceKey[18] = SourceKeys.NO_SOURCE;
		inputSourceKey[19] = SourceKeys.NO_SOURCE;
		inputSourceKey[20] = SourceKeys.NO_SOURCE;
		inputSourceKey[21] = SourceKeys.NO_SOURCE;
		inputSourceKey[22] = SourceKeys.NO_SOURCE;

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
		// Clear list of used SearchFieldKeys first
		usedSearchFieldKeys.clear();
		sourceLabelName.set("Quelle: " + search_sourcekey_selection.getValue());
		try {
			String[] input = generateArrayWithInputValues();
			int[] sources = generateArrayWithSourceFieldKeys();
			searchCtrl.executeSearch(input, sources);
			search_sourceLabel.setText(sourceLabelName.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			ui.showErrorMessage("Die Suche konnte nicht gestartet werden.\n" + e.getMessage());
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
		searchCategory_study_einschreibeTag.clear();
		searchCategory_study_einschreibeMonat.clear();
		searchCategory_study_einschreibeJahr.clear();
		searchCategory_other_zusaetzeinput.clear();
		searchCategory_other_fundort.clear();
		searchCategory_other_anmerkungen.clear();
		searchCategory_other_nummer.clear();
		searchCategory_other_seite.clear();
		searchCategory_other_nummerhess.clear();
		search_sourcekey_selection.setValue(null);
		infoArea.clear();
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

	/**
	 * 
	 */
	// FIXME remove comments from parameter
	void fillResultTable(/* List<PersonItem> results */) {
		// clear old table content first
		resultTable.getColumns().clear();

		// FIXME generate list with person items for testing purposes, delete
		// this setup in the end
		List<PersonItem> testList = new ArrayList<PersonItem>();
		for (int i = 0; i < 10; i++) {
			PersonItem person = new PersonItem();
			person.setVorname_norm("person_vorname" + i);
			person.setNachname_norm("person_nachname" + i);
			person.setADLIG("person_adlig" + i);
			person.setANMERKUNGEN("Anmerkungen" + i);
			person.setFACH("fach" + i);
			person.setSEMINAR("Seminar" + i);
			person.setORT("Ort" + i);
			person.setOrt_norm("Bamberg" + i);
			person.setId("ID" + i);
			testList.add(person);
		}

		// create all default columns
		createDefaultColumns();
		// create additional table columns depending on the list of input fields
		// the user searched for
		createColumnsOfSearchResult();

		// FIXME this list must be the result list
		ObservableList<PersonItem> data = FXCollections.observableArrayList(testList);

		resultTable.setItems(data);
	}

	void fillResultTableWithResultSet(ResultSet result) {
		ObservableList<ObservableList> data = FXCollections.observableArrayList();
		try {
			for (int i = 0; i < result.getMetaData().getColumnCount(); i++) {
				final int j = i;
				TableColumn<ObservableList, String> col = new TableColumn(result.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

							@Override
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});
				resultTable.getColumns().add(col);
			}

			while (result.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i < result.getMetaData().getColumnCount(); i++) {
					row.add(result.getString(i));
				}
				data.addAll(row);
			}
			resultTable.setItems(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void createDefaultColumns() {
		// these columns are default columns and will be displayed for all
		// search requests

		// id
		// vorname_norm
		// nachname_norm
		// ort_norm
		// fakultaet_norm

		TableColumn<PersonItem, String> id = new TableColumn<PersonItem, String>("ID");
		PropertyValueFactory<PersonItem, String> idFactory = new PropertyValueFactory<PersonItem, String>("id");
		id.setCellValueFactory(idFactory);

		TableColumn<PersonItem, String> vorname_norm = new TableColumn<PersonItem, String>("Vorname (NORM)");
		PropertyValueFactory<PersonItem, String> vornameFactory = new PropertyValueFactory<PersonItem, String>(
				"vorname_norm");
		vorname_norm.setCellValueFactory(vornameFactory);

		TableColumn<PersonItem, String> nachname_norm = new TableColumn<PersonItem, String>("Nachname (NORM)");
		PropertyValueFactory<PersonItem, String> nachnameFactory = new PropertyValueFactory<PersonItem, String>(
				"nachname_norm");
		nachname_norm.setCellValueFactory(nachnameFactory);

		TableColumn<PersonItem, String> ort_norm = new TableColumn<PersonItem, String>("Ort (NORM)");
		PropertyValueFactory<PersonItem, String> ortFactory = new PropertyValueFactory<PersonItem, String>("ort_norm");
		ort_norm.setCellValueFactory(ortFactory);

		TableColumn<PersonItem, String> fakultaet_norm = new TableColumn<PersonItem, String>("Fakultät (NORM)");
		PropertyValueFactory<PersonItem, String> fakultaetFactory = new PropertyValueFactory<PersonItem, String>(
				"fakultaet_norm");
		fakultaet_norm.setCellValueFactory(fakultaetFactory);

		resultTable.getColumns().addAll(id, vorname_norm, nachname_norm, ort_norm, fakultaet_norm);
	}

	/**
	 * Creates all columns for the {@code resultTable}, which are no default
	 * column. Uses the list {@code usedSearchFieldKeys} to create the columns
	 * which need to be displayed. If a column is part of the default column, it
	 * will not be created.
	 */
	private void createColumnsOfSearchResult() {
		for (int i = 0; i < usedSearchFieldKeys.size(); i++) {
			String columnName = usedSearchFieldKeys.get(i).toString();
			TableColumn<PersonItem, String> column = new TableColumn<PersonItem, String>(columnName);
			PropertyValueFactory<PersonItem, String> columnFactory = new PropertyValueFactory<PersonItem, String>(
					columnName);
			column.setCellValueFactory(columnFactory);
			resultTable.getColumns().add(column);
		}
	}

	/**
	 * Closes the window when the users submits the action.
	 */
	@FXML
	protected void closeWindow() {
		// FIXME check if this is correct
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
		ui.showInfo();
	}

	/**
	 * Helps ensuring full functionality and provides a feedback of the search
	 * input. Collects the user input from all input fields prints it to the
	 * info area.
	 */
	void setInfoTextExtendedSearch(List<UserQueries> requestList) {
		String info = "Suchanfrage\n-----------\n";
		StringBuffer buffer = new StringBuffer();
		if (requestList == null || requestList.size() == 0) {
			info += "Keine Sucheingaben gefunden.";
		} else {
			for (UserQueries qr : requestList) {
				buffer.append(qr.getSearchField().toString() + ": ");
				buffer.append(qr.getInput() + "; ");
				buffer.append("Quellen#: " + qr.getSource() + "\n");
			}
		}
		info += buffer.toString();
		infoArea.setText(info);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
