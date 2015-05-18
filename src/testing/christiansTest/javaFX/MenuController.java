package testing.christiansTest.javaFX;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuController {
	private String appName = "fancy Application";

	@FXML
	VBox root;

	@FXML
	MenuBar menu;

	@FXML
	Menu menu_datei;

	@FXML
	MenuItem menu_datei_speichern;

	@FXML
	MenuItem menu_datei_beenden;

	@FXML
	MenuItem menu_bearbeiten_optionen;

	@FXML
	MenuItem menu_hilfe_hilfe;

	@FXML
	MenuItem menu_hilfe_ueber;

	@FXML
	protected void save() {
		functionNotAvailable();
	}

	@FXML
	protected void closeProgram() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(appName + " - Programm beenden");
		alert.setHeaderText("Programm beenden");
		alert.setContentText("Möchten Sie das Programm wirklich beenden?");

		ButtonType btnJa = new ButtonType("Ja");
		ButtonType btnNein = new ButtonType("Nein");

		alert.getButtonTypes().setAll(btnJa, btnNein);

		Optional<ButtonType> result = alert.showAndWait();
		Stage stage = (Stage) root.getScene().getWindow();

		if (result.get() == btnJa) {
			alert.close();
			stage.close();
		} else {
			alert.close();
		}
	}

	@FXML
	protected void setOptions() {
		functionNotAvailable();
	}

	@FXML
	protected void getHelp() {
		functionNotAvailable();
	}

	@FXML
	protected void showInfo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(appName + " - Über");
		alert.setHeaderText(appName + " v1.82.02.22.3");
		alert.setContentText("Mit Liebe gemacht von\nSimon Stemper, Johannes Trepesch, Christian Ost");

		alert.showAndWait();
	}

	private void functionNotAvailable() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("fancy Application - Hinweis");
		alert.setHeaderText(null);
		alert.setContentText("Diese Funktion steht zur Zeit leider noch nicht zur Verfügung.");

		alert.showAndWait();
	}

}
