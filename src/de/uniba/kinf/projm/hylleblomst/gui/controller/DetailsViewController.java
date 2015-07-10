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
	private StringProperty nummer = new SimpleStringProperty();

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
		setUpBindings();
	}

	private void setUpBindings() {
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
						personID.setValue(searchResult.getString(i));
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
