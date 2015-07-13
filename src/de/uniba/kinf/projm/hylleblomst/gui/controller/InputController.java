package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.uniba.kinf.projm.hylleblomst.exceptions.SearchException;
import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import de.uniba.kinf.projm.hylleblomst.logic.UserQuery;
import de.uniba.kinf.projm.hylleblomst.logicImpl.UserQueryImpl;
import javafx.beans.binding.BooleanBinding;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputController implements Initializable {

	private Model model;

	private ViewHelper viewHelper;

	private int inputFieldCounter = 24;

	private SearchFieldKeys[] searchFieldKeys;

	@FXML
	private ScrollPane rootInput;

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

	public InputController() {
		viewHelper = new ViewHelper();
		searchFieldKeys = generateSearchFieldKeyArray();
	}

	public void setModel(Model model) {
		if (model == null) {
			throw new IllegalArgumentException("Übergebener SearchController hat keinen Wert.");
		}
		this.model = model;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setUpNodes();
		setNumericalInputEventHandlers();
		setUpBindings();
	}

	/**
	 * Makes default setups for different nodes, like setting the default
	 * expanded pane for the accordion or label names.
	 */
	private void setUpNodes() {
		searchCategories.setExpandedPane(searchCategory_person);
		// FIXME
		// search_sourceLabel.setText(sourceLabelName.getValueSafe());
		searchCategory_study_einschreibeHinweis
				.setText("Hinweis: Für eine erfolgreiche Suche muss jeweils mindestens das Jahr ausgefüllt sein.");
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
	void startSearch() {
		// FIXME
		// resultTable.getColumns().clear();
		// resultTable.getItems().clear();
		String[] input = generateArrayWithInputValues();
		int[] sources = generateArrayWithSourceFieldKeys();
		try {

			List<UserQuery> requestList = new ArrayList<UserQuery>();
			for (int i = 0; i < input.length; i++) {
				if (!input[i].isEmpty() && !"false".equals(input[i]) && !"yyyy-mm-dd".equals(input[i])) {
					UserQueryImpl tmpReq = new UserQueryImpl(searchFieldKeys[i], input[i], sources[i],
							search_useOrConjunction.isSelected(), search_useOpenSearch.isSelected());
					requestList.add(tmpReq);
				}
			}

			if (requestList.isEmpty()) {
				viewHelper.showErrorMessage(
						"Die Suche konnte nicht gestartet werden, da keine Eingaben gefunden wurden.");
			}
			try {
				model.search(requestList);
				// FIXME
				// setLabelSource();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new SearchException("Ein Fehler bei der Suche ist aufgetreten:\n" + e.getMessage());
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
	 * After the user had clicked on a single row in the {@link TableView}, a
	 * {@link UserQuery} is created and passed as parameter to this function. If
	 * its a valid {@link UserQuery}, it is passed to the model to finally
	 * execute the search.
	 * 
	 * <p>
	 * <b>Precondition</b>
	 * <ul>
	 * <li>the {@link UserQuery} must not be {@code null} and contain the ID of
	 * the person</li>
	 * </ul>
	 * </p>
	 * 
	 * @param personIDQuery
	 *            the {@link UserQuery} to search for
	 * @throws SearchException
	 *             if an error occurs while searching, a {@link SearchException}
	 *             is thrown
	 */
	public void startSinglePersonSearch(UserQuery personIDQuery) throws SearchException {
		if (personIDQuery == null) {
			throw new IllegalArgumentException(
					"Übergebene Suchanfrage hat keinen Wert. Personendetails können nicht gesucht werden.");
		}
		try {
			model.searchPerson(personIDQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SearchException(
					"Ein Fehler bei der Suche nach Person mit ID " + personIDQuery + " ist aufgetreten.");
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
			throw new IllegalArgumentException(
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
	 * An array with all {@link SearchFieldKeys} is generated and returned.
	 * 
	 * @return the array with {@link SearchFieldKeys}
	 */
	private SearchFieldKeys[] generateSearchFieldKeyArray() {
		SearchFieldKeys[] sfkArray = new SearchFieldKeys[inputFieldCounter];

		sfkArray[0] = SearchFieldKeys.ANREDE;
		sfkArray[1] = SearchFieldKeys.ANREDE;
		sfkArray[2] = SearchFieldKeys.TITEL;
		sfkArray[3] = SearchFieldKeys.TITEL;
		sfkArray[4] = SearchFieldKeys.VORNAME;
		sfkArray[5] = SearchFieldKeys.NACHNAME;
		sfkArray[6] = SearchFieldKeys.ADLIG;
		sfkArray[7] = SearchFieldKeys.JESUIT;
		sfkArray[8] = SearchFieldKeys.WIRTSCHAFTSLAGE;
		sfkArray[9] = SearchFieldKeys.ORT;
		sfkArray[10] = SearchFieldKeys.FACH;
		sfkArray[11] = SearchFieldKeys.FAKULTAETEN;
		sfkArray[12] = SearchFieldKeys.SEMINAR;
		sfkArray[13] = SearchFieldKeys.GRADUIERT;
		sfkArray[14] = SearchFieldKeys.STUDIENJAHR_VON;
		sfkArray[15] = SearchFieldKeys.STUDIENJAHR_BIS;
		sfkArray[16] = SearchFieldKeys.EINSCHREIBEDATUM_VON;
		sfkArray[17] = SearchFieldKeys.EINSCHREIBEDATUM_BIS;
		sfkArray[18] = SearchFieldKeys.ZUSAETZE;
		sfkArray[19] = SearchFieldKeys.FUNDORTE;
		sfkArray[20] = SearchFieldKeys.ANMERKUNGEN;
		sfkArray[21] = SearchFieldKeys.NUMMER;
		sfkArray[22] = SearchFieldKeys.SEITE_ORIGINALE;
		sfkArray[23] = SearchFieldKeys.NUMMER_HESS;

		return sfkArray;
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
