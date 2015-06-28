package de.uniba.kinf.projm.hylleblomst.view;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import de.uniba.kinf.projm.hylleblomst.logic.QueryRequest;
import de.uniba.kinf.projm.hylleblomst.logic.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.logic.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.logicImpl.QueriesImpl;
import de.uniba.kinf.projm.hylleblomst.logicImpl.QueryRequestImpl;

/**
 * Controller for the graphical user interface.
 * 
 * @author ca
 *
 */
public class ViewController implements Initializable {
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
	private int inputFieldCounter = 22;

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
	TextArea infoArea;

	/**
	 * Constructor for a new Controller. When called, the array with
	 * {@link SearchFieldKeys} and {@link SourceKeys} is build and set, the
	 * instances of the {@link QueriesImpl}, {@link UIHelper} and
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

	}

	public TableView<?> getResultTable() {
		return resultTable;
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
		try {
			inputFields[0] = searchCategory_person_anrede.getText();
			inputFields[1] = searchCategory_person_anredenorm.getText();
			inputFields[2] = searchCategory_person_titel.getText();
			inputFields[3] = searchCategory_person_titelnorm.getText();
			inputFields[4] = searchCategory_person_vornameinput.getText();
			inputFields[5] = searchCategory_person_nachnameinput.getText();
			inputFields[6] = searchCategory_personExtended_adeliger
					.isSelected();
			inputFields[7] = searchCategory_personExtended_jesuit.isSelected();
			inputFields[8] = searchCategory_personExtended_wirtschaftinput
					.getText();
			inputFields[9] = searchCategory_personExtended_ortinput.getText();
			inputFields[10] = searchCategory_study_studienfachinput.getText();
			inputFields[11] = searchCategory_study_fakultaet.getText();
			inputFields[12] = searchCategory_study_seminarinput.getText();
			inputFields[13] = searchCategory_study_graduiert.isSelected();
			inputFields[14] = getStudienjahr();
			inputFields[15] = getEinschreibung();
			inputFields[16] = searchCategory_other_zusaetzeinput.getText();
			inputFields[17] = searchCategory_other_fundort.getText();
			inputFields[18] = searchCategory_other_anmerkungen.getText();
			if (!"".equals(searchCategory_other_nummer.getText())) {
				inputFields[19] = getParsedInt(searchCategory_other_nummer
						.getText());
			} else {
				inputFields[19] = null;
			}
			if (!"".equals(searchCategory_other_seite.getText())) {
				inputFields[20] = getParsedInt(searchCategory_other_seite
						.getText());
			} else {
				inputFields[20] = null;
			}
			if (!"".equals(searchCategory_other_nummerhess.getText())) {
				inputFields[21] = getParsedInt(searchCategory_other_nummerhess
						.getText());
			} else {
				inputFields[21] = null;
			}
		} catch (InputMismatchException | NumberFormatException e) {
			ui.showErrorMessage(e.getMessage());
		}
		return inputFields;
	}

	private int[] getStudienjahr() {
		int[] studienjahr = new int[2];
		if (!searchCategory_study_studienjahrVon.getText().isEmpty()
				&& !searchCategory_study_studienjahrBis.getText().isEmpty()) {
			studienjahr[0] = getParsedInt(searchCategory_study_studienjahrVon
					.getText());
			studienjahr[1] = getParsedInt(searchCategory_study_studienjahrBis
					.getText());
			return studienjahr;
		} else if (!searchCategory_study_studienjahrVon.getText().isEmpty()) {
			studienjahr[0] = getParsedInt(searchCategory_study_studienjahrVon
					.getText());
			studienjahr[1] = Integer.MAX_VALUE;
			return studienjahr;
		} else if (!searchCategory_study_studienjahrBis.getText().isEmpty()) {
			studienjahr[0] = Integer.MIN_VALUE;
			studienjahr[1] = getParsedInt(searchCategory_study_studienjahrBis
					.getText());
			return studienjahr;
		}
		// FIXME das muss besser gehen!
		return null;
	}

	private int[] getEinschreibung() {
		int[] einschreibung = new int[3];
		boolean inputDone = false;
		if (!searchCategory_study_einschreibeJahr.getText().isEmpty()) {
			einschreibung[0] = getParsedInt(searchCategory_study_einschreibeJahr
					.getText());
			inputDone = true;
		} else {
			einschreibung[0] = 0;
		}
		if (!searchCategory_study_einschreibeMonat.getText().isEmpty()) {
			einschreibung[1] = getParsedInt(searchCategory_study_einschreibeMonat
					.getText());
			inputDone = true;
		} else {
			einschreibung[1] = 0;
		}
		if (!searchCategory_study_einschreibeTag.getText().isEmpty()) {
			einschreibung[2] = getParsedInt(searchCategory_study_einschreibeTag
					.getText());
			inputDone = true;
		} else {
			einschreibung[2] = 0;
		}
		if (inputDone) {
			return einschreibung;
		} else {
			// FIXME das muss besser gehen!
			return null;
		}
	}

	private int getParsedInt(String input) {
		if ("".equals(input) || input == null) {
			throw new InputMismatchException(
					"Ein Fehler bei der Verarbeitung der Zahleingabe ist aufgetreten.");
		}
		int result;
		try {
			result = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Die Eingabe von \"" + input
					+ "\" in einem Zahlenfeld ist nicht gültig.");
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
		inputSourceKey[4] = getSourceKeyByValueAsString(searchCategory_person_vornameselection
				.getValue());
		inputSourceKey[5] = SourceKeys.STANDARD;
		inputSourceKey[6] = SourceKeys.NO_SOURCE;
		inputSourceKey[7] = SourceKeys.NO_SOURCE;
		inputSourceKey[8] = getSourceKeyByValueAsString(searchCategory_personExtended_wirtschaftselection
				.getValue());
		inputSourceKey[9] = getSourceKeyByValueAsString(searchCategory_personExtended_ortselection
				.getValue());
		inputSourceKey[10] = getSourceKeyByValueAsString(searchCategory_study_studienfachselection
				.getValue());
		inputSourceKey[11] = SourceKeys.NO_SOURCE;
		inputSourceKey[12] = getSourceKeyByValueAsString(searchCategory_study_seminarselection
				.getValue());
		inputSourceKey[13] = SourceKeys.NO_SOURCE;
		inputSourceKey[14] = SourceKeys.NO_SOURCE;
		inputSourceKey[15] = SourceKeys.NO_SOURCE;
		inputSourceKey[16] = getSourceKeyByValueAsString(searchCategory_other_zusaetzeselection
				.getValue());
		inputSourceKey[17] = SourceKeys.NO_SOURCE;
		inputSourceKey[18] = SourceKeys.NO_SOURCE;
		inputSourceKey[19] = SourceKeys.NO_SOURCE;
		inputSourceKey[20] = SourceKeys.NO_SOURCE;
		inputSourceKey[21] = SourceKeys.NO_SOURCE;

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
			Object[] input = generateArrayWithInputValues();
			int[] sources = generateArrayWithSourceFieldKeys();
			searchCtrl.executeSearch(input, sources);
		} catch (Exception e) {
			e.printStackTrace();
			ui.showErrorMessage("Die Suche konnte nicht gestartet werden.\n"
					+ e.getMessage());
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
		searchCategory_person_vornameselection.setValue(null);
		searchCategory_person_nachnameinput.clear();
		searchCategory_personExtended_adeliger.setSelected(false);
		searchCategory_personExtended_jesuit.setSelected(false);
		searchCategory_personExtended_wirtschaftselection.setValue(null);
		searchCategory_personExtended_wirtschaftinput.clear();
		searchCategory_personExtended_ortselection.setValue(null);
		searchCategory_personExtended_ortinput.clear();
		searchCategory_study_studienfachselection.setValue(null);
		searchCategory_study_studienfachinput.clear();
		searchCategory_study_fakultaet.clear();
		searchCategory_study_seminarselection.setValue(null);
		searchCategory_study_seminarinput.clear();
		searchCategory_study_graduiert.setSelected(false);
		searchCategory_study_studienjahrVon.clear();
		searchCategory_study_studienjahrBis.clear();
		searchCategory_study_einschreibeTag.clear();
		searchCategory_study_einschreibeMonat.clear();
		searchCategory_study_einschreibeJahr.clear();
		searchCategory_other_zusaetzeselection.setValue(null);
		searchCategory_other_zusaetzeinput.clear();
		searchCategory_other_fundort.clear();
		searchCategory_other_anmerkungen.clear();
		searchCategory_other_nummer.clear();
		searchCategory_other_seite.clear();
		searchCategory_other_nummerhess.clear();
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

	void fillResultTable(ResultSet result) {
		resultTable.getColumns().clear();

		ObservableList<ObservableList> data = FXCollections
				.observableArrayList();

		try {
			while (result.next()) {
				ObservableList<String> row = FXCollections
						.observableArrayList();
				for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
					row.add(result.getString(i));
				}
				data.add(row);
			}
			resultTable.setItems(data);
			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// vorname_norm
		// nachname_norm
		// ort_norm
		// fakultaet_norm

		TableColumn col = new TableColumn("vorname_norm");

		col.setCellValueFactory(
		// Callback <CellDataFeatures, Observable Value>
		new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
			// Methode die Wert f�r Zelle zur�ckliefert
			public ObservableValue<String> call(
					TableColumn.CellDataFeatures<ObservableList, String> param) {
				//
				return new SimpleStringProperty(param.getValue().get(0)
						.toString());
			}
		});

		resultTable.getColumns().add(col);

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
	void setInfoTextExtendedSearch(List<QueryRequestImpl> requestList) {
		String info = "Suchanfrage\n-----------\n";
		if (requestList == null || requestList.size() == 0) {
			info += "Keine Sucheingaben gefunden.";
		} else {
			for (QueryRequest qr : requestList) {
				info += qr.getSearchField().toString() + ": ";
				info += qr.getInput() + "; ";
				info += "Quellen#: " + qr.getSource() + "\n";
			}
		}

		infoArea.setText(info);
	}
}
