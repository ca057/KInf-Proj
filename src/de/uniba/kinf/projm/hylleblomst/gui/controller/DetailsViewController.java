package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.ResourceBundle;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import de.uniba.kinf.projm.hylleblomst.keys.SearchFieldKeys;
import de.uniba.kinf.projm.hylleblomst.logicImpl.UserQueryImpl;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Controller for displaying the details of a person.
 *
 */
public class DetailsViewController implements ControllerInterface, Initializable {

	private StringProperty personID;

	private Model model;

	private ViewHelper viewHelper;

	@FXML
	private GridPane root;

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
	private ComboBox<String> result_details_fakultätselection;

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
	// label value is set with personID

	@FXML
	private Label result_details_seite;
	private StringProperty seite = new SimpleStringProperty();

	@FXML
	private Label result_details_nummerhess;
	private StringProperty nummerhess = new SimpleStringProperty();

	public DetailsViewController() {
		viewHelper = new ViewHelper();
		personID = new SimpleStringProperty();
	}

	public void setModel(Model model) {
		if (model != null) {
			this.model = model;
		} else {
			throw new InputMismatchException(
					"Model ist fehlerhaft, kann dem DetailsViewController nicht hinzugefügt werden.");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setUpEventHandlers();
		setUpViewBindings();
		setUpLabelBindings();
	}

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
		root.disableProperty().bind(existsID);
	}

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

	private void setUpEventHandlers() {
		result_details_zusaetzeselection.setOnAction((event) -> {
			getSourceDetails(SearchFieldKeys.ZUSAETZE,
					result_details_zusaetzeselection.getSelectionModel().getSelectedItem());
		});
	}

	private void getSourceDetails(SearchFieldKeys sfk, String source) {
		if (sfk == null || source == null || source.isEmpty()) {
			throw new InputMismatchException(
					"Es können keine Tradierungen gesucht werden, da das Suchfeld oder die Quelle keinen Wert hat oder leer ist.");
		}
		System.out.println("Gesucht wird nach Quelle: " + source);
		CachedRowSet singleResult = model.searchSourceDetails(
				new UserQueryImpl(sfk, personID.getValueSafe(), viewHelper.getSourceKeyByValueAsString(source)));
		if (singleResult != null) {
			System.out.println(singleResult.size());
		} else {
			System.out.println("Es gibt keine Detailinformationen für dieses Feld.");
		}
	}

	/**
	 * 
	 * @param searchResult
	 */
	public void processCompleteSearchResult(CachedRowSet searchResult) {
		if (searchResult == null) {
			throw new InputMismatchException("Das Suchergebnis für die Details einer Person hat keinen Wert.");
		}
		ResultSetMetaData crsMeta;
		try {
			crsMeta = searchResult.getMetaData();
			System.out.println(crsMeta.getColumnCount());
			System.out.println(searchResult.size());

			searchResult.first();
			while (searchResult.next()) {
				for (int i = 1; i <= crsMeta.getColumnCount(); i++) {
					System.out.println(crsMeta.getColumnName(i));
					System.out.print(searchResult.getString(i) + "\n---\n");
				}
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
						// FIXME Datum muss überschrieben werden
						if (searchResult.getString(i) == null || searchResult.getString(i).isEmpty()) {
							einschreibedatum.setValue("");
						} else {
							einschreibedatum.setValue(searchResult.getString(i));
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
			System.out.println("PersonID = " + personID.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
