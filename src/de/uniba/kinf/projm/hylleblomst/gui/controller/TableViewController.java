package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.sql.rowset.CachedRowSet;

import de.uniba.kinf.projm.hylleblomst.exceptions.SearchException;
import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import de.uniba.kinf.projm.hylleblomst.logic.UserQuery;
import de.uniba.kinf.projm.hylleblomst.logicImpl.UserQueryImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class TableViewController implements Initializable, Observer {

	private Model model;

	private ViewHelper viewHelper;

	private CachedRowSet result;

	private MainController mainController;

	private StringProperty sourceLabelName;

	@FXML
	private TableView<ObservableList<String>> resultTable;

	@FXML
	private Label search_sourceLabel;

	public TableViewController() {
		viewHelper = new ViewHelper();
	}

	public void setModel(Model model) {
		if (model == null) {
			throw new IllegalArgumentException("Das übergebene Model hat keinen Wert.");
		}
		this.model = model;
	}

	/**
	 * @param mainController
	 *            the mainController to set
	 */
	public void setMainController(MainController mainController) {
		if (mainController == null) {
			throw new IllegalArgumentException("Der übergebene MainController hat keinen Wert.");
		}
		this.mainController = mainController;
		setBindingsWithMainController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clearResultTable();
		setTableViewEventHandlers();
		search_sourceLabel.textProperty().set("Quelle: Keine Quelle ausgewählt.");
	}

	/*
	 * Clears the {@link TableView}.
	 */
	private void clearResultTable() {
		resultTable.getColumns().clear();
		resultTable.getItems().clear();
	}

	/*
	 * 
	 */
	private void setTableViewEventHandlers() {
		resultTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (resultTable.getSelectionModel().getSelectedItem() != null) {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
						startSinglePersonSearch(resultTable.getSelectionModel().getSelectedItem().get(0));
					}
				}
				event.consume();
			}
		});
	}

	private void setBindingsWithMainController() {
		sourceLabelName = new SimpleStringProperty();
		sourceLabelName.bind(mainController.getSelectedSourceProperty());
		sourceLabelName.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (observable == null || newValue == null) {
					search_sourceLabel.textProperty().set("Quelle: Keine Quelle ausgewählt.");
				} else {
					search_sourceLabel.textProperty().set("Quelle: " + newValue);
				}
			}
		});
	}

	public CachedRowSet getResult() {
		return result;
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
	public void startSinglePersonSearch(String id) {
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException(
					"Übergebene ID hat keinen Wert. Personendetails können nicht gesucht werden.");
		}
		try {
			UserQueryImpl personIDQuery = new UserQueryImpl(id);
			model.searchPerson(personIDQuery);
		} catch (SearchException | SQLException e) {
			e.printStackTrace();
			viewHelper.showErrorMessage("Ein Fehler bei der Suche nach Person mit ID " + id + " ist aufgetreten.");
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
			clearResultTable();
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
					col.setPrefWidth(125.0);
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
}
