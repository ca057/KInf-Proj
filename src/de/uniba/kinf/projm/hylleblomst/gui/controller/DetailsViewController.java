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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * Controller for displaying the details of a person.
 *
 */
public class DetailsViewController implements ControllerInterface, Initializable {

	private String personID;

	private Model model;

	private ViewHelper viewHelper;

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

	@FXML
	private Label result_details_titel;

	@FXML
	private Label result_details_vorname;

	@FXML
	private Label result_details_nachname;

	@FXML
	private Label result_details_adlig;

	@FXML
	private Label result_details_jesuit;

	@FXML
	private Label result_details_wirtschaft;

	@FXML
	private Label result_details_ort;

	@FXML
	private Label result_details_studienfach;

	@FXML
	private Label result_details_fakultaet;

	@FXML
	private Label result_details_seminar;

	@FXML
	private Label result_details_graduiert;

	@FXML
	private Label result_details_studienjahr;

	@FXML
	private Label result_details_einschreibedatum;

	@FXML
	private Label result_details_zusaetze;

	@FXML
	private Label result_details_fundort;

	@FXML
	private Label result_details_anmerkungen;

	@FXML
	private Label result_details_nummer;

	@FXML
	private Label result_details_seite;

	@FXML
	private Label result_details_nummerhess;

	public DetailsViewController() {
		viewHelper = new ViewHelper();
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
	}

	private void setUpEventHandlers() {
		result_details_zusaetzeselection.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});
	}

	private void getSourceDetails(SearchFieldKeys sfk, String source) {
		if (source == null || source.isEmpty()) {
			throw new InputMismatchException(
					"Es können keine Tradierungen gesucht werden, da die Quelle keinen Wert hat oder leer ist.");
		}

		UserQueryImpl sourceQuery = new UserQueryImpl();

	}

	/**
	 * 
	 * @param searchResult
	 */
	public void processSearchResult(CachedRowSet searchResult) {
		if (searchResult == null) {
			throw new InputMismatchException("Das Suchergebnis für die Details einer Person hat keinen Wert.");
		}
		ResultSetMetaData crsMeta;
		try {
			crsMeta = searchResult.getMetaData();
			System.out.println(crsMeta.getColumnCount());
			System.out.println(searchResult.size());
			for (int i = 1; i <= crsMeta.getColumnCount(); i++) {
				System.out.println(
						"ColumnName: " + crsMeta.getColumnName(i) + " - ColumnLabel: " + crsMeta.getColumnLabel(i));
			}

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
