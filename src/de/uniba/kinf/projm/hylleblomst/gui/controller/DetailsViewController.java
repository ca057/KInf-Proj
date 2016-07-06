package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.logicImpl.UserQueryImpl;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

/**
 * Controller for displaying the details of a person.
 * 
 * @author Christian
 *
 */
public class DetailsViewController implements Initializable {

	private StringProperty personID;

	private BooleanProperty noSelectionEvent;

	private Model model;

	private ViewHelper viewHelper;

	@FXML
	private GridPane rootDetails;

	@FXML
	private ComboBox<String> result_details_anredeselection;

	@FXML
	private ComboBox<String> result_details_titelselection;

	@FXML
	private ComboBox<String> result_details_vornameselection;

	@FXML
	private ComboBox<String> result_details_nachnameselection;

	@FXML
	private ComboBox<String> result_details_wirtschaftselection;

	@FXML
	private ComboBox<String> result_details_ortselection;

	@FXML
	private ComboBox<String> result_details_studienfachselection;

	@FXML
	private ComboBox<String> result_details_seminarselection;

	@FXML
	private ComboBox<String> result_details_zusaetzeselection;

	@FXML
	private Label result_details_anrede;
	private StringProperty anrede = new SimpleStringProperty();

	@FXML
	private Label result_details_titel;
	private StringProperty titel = new SimpleStringProperty();

	@FXML
	private Label result_details_vorname;
	private StringProperty vorname = new SimpleStringProperty();

	@FXML
	private Label result_details_nachname;
	private StringProperty nachname = new SimpleStringProperty();

	@FXML
	private Label result_details_adlig;
	private StringProperty adlig = new SimpleStringProperty();

	@FXML
	private Label result_details_jesuit;
	private StringProperty jesuit = new SimpleStringProperty();

	@FXML
	private Label result_details_wirtschaft;
	private StringProperty wirtschaft = new SimpleStringProperty();

	@FXML
	private Label result_details_ort;
	private StringProperty ort = new SimpleStringProperty();

	@FXML
	private Label result_details_studienfach;
	private StringProperty studienfach = new SimpleStringProperty();

	@FXML
	private Label result_details_fakultaet;
	private StringProperty fakultaet = new SimpleStringProperty();

	@FXML
	private Label result_details_seminar;
	private StringProperty seminar = new SimpleStringProperty();

	@FXML
	private Label result_details_graduiert;
	private StringProperty graduiert = new SimpleStringProperty();

	@FXML
	private Label result_details_studienjahr;
	private StringProperty studienjahr = new SimpleStringProperty();

	@FXML
	private Label result_details_einschreibedatum;
	private StringProperty einschreibedatum = new SimpleStringProperty();

	@FXML
	private Label result_details_zusaetze;
	private StringProperty zusaetze = new SimpleStringProperty();

	@FXML
	private Label result_details_fundort;
	private StringProperty fundort = new SimpleStringProperty();

	@FXML
	private Label result_details_anmerkungen;
	private StringProperty anmerkungen = new SimpleStringProperty();

	@FXML
	private Label result_details_nummer;

	@FXML
	private Label result_details_seite;
	private StringProperty seite = new SimpleStringProperty();

	@FXML
	private Label result_details_nummerhess;
	private StringProperty nummerhess = new SimpleStringProperty();

	/**
	 * Default constructor. The {@link ViewHelper} and variables are
	 * instantiated.
	 */
	public DetailsViewController() {
		viewHelper = new ViewHelper();
		personID = new SimpleStringProperty();
		noSelectionEvent = new SimpleBooleanProperty(false);
	}

	/**
	 * The function sets the {@link Model}.
	 * 
	 * <p>
	 * <b>Precondition</b>
	 * <ul>
	 * <li>the {@link Model} must not be {@code null}</li>
	 * </ul>
	 * </p>
	 * 
	 * @param model
	 *            the {@link Model} to set
	 */
	public void setModel(Model model) {
		if (model != null) {
			this.model = model;
		} else {
			throw new IllegalArgumentException(
					"Model ist fehlerhaft, kann dem DetailsViewController nicht hinzugefügt werden.");
		}
	}

	/**
	 * Implemented from Interface {@link Initializable}. After all FXML-elements
	 * are initialized, the event handlers and bindings are set.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setUpEventHandlers();
		setUpViewBindings();
		setUpLabelBindings();
		setUpTooltips();
	}

	/*
	 * The event handlers are set. All selection boxes initiates a search with
	 * the corresponding {@link SearchFieldKey} and the source.
	 */
	private void setUpEventHandlers() {
		result_details_anredeselection.setOnAction((event) -> {
			if (!noSelectionEvent.get()) {
				getSourceDetails(SearchFieldKeys.ANREDE,
						result_details_titelselection.getSelectionModel().getSelectedItem());
			}
		});
		result_details_titelselection.setOnAction((event) -> {
			if (!noSelectionEvent.get()) {
				getSourceDetails(SearchFieldKeys.TITEL,
						result_details_titelselection.getSelectionModel().getSelectedItem());
			}
		});
		result_details_vornameselection.setOnAction((event) -> {
			if (!noSelectionEvent.get()) {
				getSourceDetails(SearchFieldKeys.VORNAME,
						result_details_vornameselection.getSelectionModel().getSelectedItem());
			}
		});
		result_details_nachnameselection.setOnAction((event) -> {
			if (!noSelectionEvent.get()) {
				getSourceDetails(SearchFieldKeys.NACHNAME,
						result_details_nachnameselection.getSelectionModel().getSelectedItem());
			}
		});
		result_details_wirtschaftselection.setOnAction((event) -> {
			if (!noSelectionEvent.get()) {
				getSourceDetails(SearchFieldKeys.WIRTSCHAFTSLAGE,
						result_details_wirtschaftselection.getSelectionModel().getSelectedItem());
			}
		});
		result_details_ortselection.setOnAction((event) -> {
			if (!noSelectionEvent.get()) {
				getSourceDetails(SearchFieldKeys.ORT,
						result_details_ortselection.getSelectionModel().getSelectedItem());
			}
		});
		result_details_studienfachselection.setOnAction((event) -> {
			if (!noSelectionEvent.get()) {
				getSourceDetails(SearchFieldKeys.FACH,
						result_details_studienfachselection.getSelectionModel().getSelectedItem());
			}
		});
		result_details_seminarselection.setOnAction((event) -> {
			if (!noSelectionEvent.get()) {
				getSourceDetails(SearchFieldKeys.SEMINAR,
						result_details_seminarselection.getSelectionModel().getSelectedItem());
			}
		});
		result_details_zusaetzeselection.setOnAction((event) -> {
			if (!noSelectionEvent.get()) {
				getSourceDetails(SearchFieldKeys.ZUSAETZE,
						result_details_zusaetzeselection.getSelectionModel().getSelectedItem());
			}
		});
	}

	/*
	 * The bindings of the view are set. At this moment of implementation, this
	 * is only one binding. When there is no or an empty person ID, the view is
	 * disabled.
	 */
	private void setUpViewBindings() {
		BooleanBinding existsID = new BooleanBinding() {

			{
				super.bind(personID);
			}

			@Override
			protected boolean computeValue() {
				if (personID.getValueSafe().isEmpty()) {
					return true;
				}
				return false;
			}
		};
		rootDetails.disableProperty().bind(existsID);
	}

	/*
	 * The label with the details are bound to {@link SimpleStringProperty}s,
	 * which are changed when a new person is loaded into the details view or
	 * the search for a specific search is started.
	 */
	private void setUpLabelBindings() {
		result_details_adlig.textProperty().bind(adlig);
		result_details_anmerkungen.textProperty().bind(anmerkungen);
		result_details_anrede.textProperty().bind(anrede);
		result_details_einschreibedatum.textProperty().bind(einschreibedatum);
		result_details_fakultaet.textProperty().bind(fakultaet);
		result_details_fundort.textProperty().bind(fundort);
		result_details_graduiert.textProperty().bind(graduiert);
		result_details_jesuit.textProperty().bind(jesuit);
		result_details_nachname.textProperty().bind(nachname);
		result_details_nummer.textProperty().bind(personID);
		result_details_nummerhess.textProperty().bind(nummerhess);
		result_details_ort.textProperty().bind(ort);
		result_details_seite.textProperty().bind(seite);
		result_details_seminar.textProperty().bind(seminar);
		result_details_studienfach.textProperty().bind(studienfach);
		result_details_studienjahr.textProperty().bind(studienjahr);
		result_details_titel.textProperty().bind(titel);
		result_details_vorname.textProperty().bind(vorname);
		result_details_wirtschaft.textProperty().bind(wirtschaft);
		result_details_zusaetze.textProperty().bind(zusaetze);
	}

	/*
	 * The tooltips for all {@link Label}s are set. They show the value of the
	 * label, especially when the text length is too long for the size of the
	 * label.
	 */
	private void setUpTooltips() {
		Tooltip adligTooltip = new Tooltip();
		adligTooltip.textProperty().bind(adlig);
		result_details_adlig.setTooltip(adligTooltip);

		Tooltip anmerkungenTooltip = new Tooltip();
		anmerkungenTooltip.textProperty().bind(anmerkungen);
		result_details_anmerkungen.setTooltip(anmerkungenTooltip);

		Tooltip anredeTooltip = new Tooltip();
		anredeTooltip.textProperty().bind(anrede);
		result_details_anrede.setTooltip(anredeTooltip);

		Tooltip einschreibedatumTooltip = new Tooltip();
		einschreibedatumTooltip.textProperty().bind(einschreibedatum);
		result_details_einschreibedatum.setTooltip(einschreibedatumTooltip);

		Tooltip fakultaetTooltip = new Tooltip();
		fakultaetTooltip.textProperty().bind(fakultaet);
		result_details_fakultaet.setTooltip(fakultaetTooltip);

		Tooltip fundortTooltip = new Tooltip();
		fundortTooltip.textProperty().bind(fundort);
		result_details_fundort.setTooltip(fundortTooltip);

		Tooltip graduiertTooltip = new Tooltip();
		graduiertTooltip.textProperty().bind(graduiert);
		result_details_graduiert.setTooltip(graduiertTooltip);

		Tooltip jesuitTooltip = new Tooltip();
		jesuitTooltip.textProperty().bind(jesuit);
		result_details_jesuit.setTooltip(jesuitTooltip);

		Tooltip nachnameTooltip = new Tooltip();
		nachnameTooltip.textProperty().bind(nachname);
		result_details_nachname.setTooltip(nachnameTooltip);

		Tooltip nummerTooltip = new Tooltip();
		nummerTooltip.textProperty().bind(personID);
		result_details_nummer.setTooltip(nummerTooltip);

		Tooltip nummerhessTooltip = new Tooltip();
		nummerhessTooltip.textProperty().bind(nummerhess);
		result_details_nummerhess.setTooltip(nummerhessTooltip);

		Tooltip ortTooltip = new Tooltip();
		ortTooltip.textProperty().bind(ort);
		result_details_ort.setTooltip(ortTooltip);

		Tooltip seiteTooltip = new Tooltip();
		seiteTooltip.textProperty().bind(seite);
		result_details_seite.setTooltip(seiteTooltip);

		Tooltip seminarTooltip = new Tooltip();
		seminarTooltip.textProperty().bind(seminar);
		result_details_seminar.setTooltip(seminarTooltip);

		Tooltip studienfachTooltip = new Tooltip();
		studienfachTooltip.textProperty().bind(studienfach);
		result_details_studienfach.setTooltip(studienfachTooltip);

		Tooltip studienjahrTooltip = new Tooltip();
		studienjahrTooltip.textProperty().bind(studienjahr);
		result_details_studienjahr.setTooltip(studienjahrTooltip);

		Tooltip titelTooltip = new Tooltip();
		titelTooltip.textProperty().bind(titel);
		result_details_titel.setTooltip(titelTooltip);

		Tooltip vornameTooltip = new Tooltip();
		vornameTooltip.textProperty().bind(vorname);
		result_details_vorname.setTooltip(vornameTooltip);

		Tooltip wirtschaftTooltip = new Tooltip();
		wirtschaftTooltip.textProperty().bind(wirtschaft);
		result_details_wirtschaft.setTooltip(wirtschaftTooltip);

		Tooltip zusaetzeTooltip = new Tooltip();
		zusaetzeTooltip.textProperty().bind(zusaetze);
		result_details_zusaetze.setTooltip(zusaetzeTooltip);
	}

	/**
	 * This function takes an {@link CachedRowSet} and processes it in a way,
	 * that all the search result can be shown in the view.
	 * 
	 * @param searchResult
	 *            the {@link CachedRowSet} to show
	 */
	public void processCompleteSearchResult(CachedRowSet searchResult) {
		if (searchResult == null) {
			throw new IllegalArgumentException("Das Suchergebnis für die Details einer Person hat keinen Wert.");
		}
		ResultSetMetaData crsMeta;
		try {
			crsMeta = searchResult.getMetaData();
			clearDetailsView();
			while (searchResult.next()) {
				for (int i = 1; i <= crsMeta.getColumnCount(); i++) {
					if (crsMeta.getColumnName(i).equals("PERSONID")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							personID.setValue("");
						} else {
							personID.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("ANREDENORM")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							anrede.setValue("");
						} else {
							anrede.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("TITELNORM")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							titel.setValue("");
						} else {
							titel.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("VORNAMENORM")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							vorname.setValue("");
						} else {
							vorname.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("NAMENORM")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							nachname.setValue("");
						} else {
							nachname.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("ADLIG")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							adlig.setValue("");
						} else {
							adlig.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("JESUIT")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							jesuit.setValue("");
						} else {
							jesuit.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("WIRTSCHAFTSLAGENORM")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							wirtschaft.setValue("");
						} else {
							wirtschaft.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("ORTNORM")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							ort.setValue("");
						} else {
							ort.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("FACHNORM")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							studienfach.setValue("");
						} else {
							studienfach.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("FAKULTAETENNORM")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							fakultaet.setValue("");
						} else {
							fakultaet.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("SEMINARNORM")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							seminar.setValue("");
						} else {
							seminar.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("GRADUIERT")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							graduiert.setValue("");
						} else {
							graduiert.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("STUDIENJAHR")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							studienjahr.setValue("");
						} else {
							studienjahr.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("DATUM")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							einschreibedatum.setValue("");
						} else {
							einschreibedatum.setValue(viewHelper.formatDateForDisplaying(searchResult.getString(i),
									searchResult.getString(i + 1)));
						}
					} else if (crsMeta.getColumnName(i).equals("ZUSAETZE")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							zusaetze.setValue("");
						} else {
							zusaetze.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("FUNDORTENORM")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							fundort.setValue("");
						} else {
							fundort.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("ANMERKUNG")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							anmerkungen.setValue("");
						} else {
							anmerkungen.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("SEITEORIGINAL")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							seite.setValue("");
						} else {
							seite.setValue(searchResult.getString(i));
						}
					} else if (crsMeta.getColumnName(i).equals("NUMMERHESS")) {
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							nummerhess.setValue("");
						} else {
							nummerhess.setValue(searchResult.getString(i));
						}
					}
				}
			}
		} catch (SQLException e) {
			viewHelper.showErrorMessage(
					"Es können keine Detailinformationen für diese Person angezeigt werden.\n" + e.getMessage());
		}
	}

	/*
	 * The function takes a {@link SearchFieldKey} and a source as {@link
	 * String} as input, calls the model to execute the search. After a
	 * completed search, the result is shown in the corresponding {@link Label}.
	 */
	private void getSourceDetails(SearchFieldKeys sfk, String source) {
		if (sfk == null || source == null || source.isEmpty()) {
			throw new IllegalArgumentException(
					"Es können keine Tradierungen gesucht werden, da das Suchfeld oder die Quelle keinen Wert hat oder leer ist.");
		}
		try {
			CachedRowSet sourceResult = model.searchSourceDetails(
					new UserQueryImpl(sfk, personID.getValueSafe(), viewHelper.getSourceKeyByValueAsString(source)));
			if (sourceResult == null) {
				viewHelper.showErrorMessage(
						"Bei der Suche nach Detailinformationen ist ein Fehler aufgetreten. Das Ergebnis hat keinen Wert.");
			}
			String result = "";
			if (sourceResult.size() != 0) {
				while (sourceResult.next()) {
					for (int i = 1; i <= sourceResult.getMetaData().getColumnCount(); i++) {
						if (!sfk.toString().equals(sourceResult.getMetaData().getColumnName(i))) {
							viewHelper.showErrorMessage(
									"Die Detailinformationen konnten nicht ausgewertet werden, da ein unbekannter Spaltenname zurückgegeben wurde.");
							break;
						}
						result = sourceResult.getString(i);
					}
				}
			}
			if ("ANREDE".equals(sfk.toString())) {
				anrede.setValue(result);
			} else if ("TITEL".equals(sfk.toString())) {
				titel.setValue(result);
			} else if ("VORNAME".equals(sfk.toString())) {
				vorname.setValue(result);
			} else if ("NACHNAME".equals(sfk.toString())) {
				nachname.setValue(result);
			} else if ("WIRTSCHAFTSLAGE".equals(sfk.toString())) {
				wirtschaft.setValue(result);
			} else if ("ORT".equals(sfk.toString())) {
				ort.setValue(result);
			} else if ("FACH".equals(sfk.toString())) {
				studienfach.setValue(result);
			} else if ("SEMINAR".equals(sfk.toString())) {
				seminar.setValue(result);
			} else if ("ZUSAETZE".equals(sfk.toString())) {
				zusaetze.setValue(result);
			} else {
				viewHelper.showErrorMessage("Der Wert '" + result + "' der Spalte " + sfk.toString()
						+ " konnte keinem Feld zugeordnet werden.");
			}
		} catch (SQLException e) {
			viewHelper.showErrorMessage("Es konnten keine Tradierungen gefunden werden.\n" + e.getMessage());
		}
	}

	private void clearDetailsView() {
		setLabelValuesToEmptyString();
		setSelectionValueToDefault();
	}

	private void setLabelValuesToEmptyString() {
		anrede.setValue("");
		adlig.setValue("");
		anmerkungen.setValue("");
		einschreibedatum.setValue("");
		fakultaet.setValue("");
		fundort.setValue("");
		graduiert.setValue("");
		jesuit.setValue("");
		nachname.setValue("");
		nummerhess.setValue("");
		ort.setValue("");
		personID.setValue("");
		seite.setValue("");
		seminar.setValue("");
		studienfach.setValue("");
		studienjahr.setValue("");
		titel.setValue("");
		vorname.setValue("");
		wirtschaft.setValue("");
		zusaetze.setValue("");
	}

	private void setSelectionValueToDefault() {
		noSelectionEvent.setValue(true);
		result_details_anredeselection.getSelectionModel().clearSelection();
		result_details_nachnameselection.getSelectionModel().clearSelection();
		result_details_ortselection.getSelectionModel().clearSelection();
		result_details_seminarselection.getSelectionModel().clearSelection();
		result_details_studienfachselection.getSelectionModel().clearSelection();
		result_details_titelselection.getSelectionModel().clearSelection();
		result_details_vornameselection.getSelectionModel().clearSelection();
		result_details_wirtschaftselection.getSelectionModel().clearSelection();
		result_details_zusaetzeselection.getSelectionModel().clearSelection();
		noSelectionEvent.setValue(false);
	}

}
