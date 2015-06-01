package testing.christiansTest.javaFX;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class UIHelper {
	private String appName = "Hylleblomst";

	public String getAppName() {
		return appName;
	}

	public boolean askForClosingWindow(Stage stage) {
		if (stage == null) {
			// TODO: Welche Exception soll geschmissen werden?
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(appName + " - Programm beenden");
		alert.setHeaderText("Programm beenden");
		alert.setContentText("Möchten Sie das Programm wirklich beenden?");

		ButtonType btnJa = new ButtonType("Ja");
		ButtonType btnNein = new ButtonType("Nein");

		alert.getButtonTypes().setAll(btnJa, btnNein);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == btnJa) {
			alert.close();
			return true;
		} else {
			alert.close();
			return false;
		}
	}

	public void functionNotAvailable() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(appName + " - Hinweis");
		alert.setHeaderText(null);
		alert.setContentText("Diese Funktion steht zur Zeit leider noch nicht zur Verfügung.");

		alert.showAndWait();
	}
}
