package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

/**
 * Supports the creation of a lovely user interface <3.
 *
 */
public class UIHelper {

	private String appName = "Hylleblomst";

	private Alert alertInformation = new Alert(AlertType.INFORMATION);

	/**
	 * Returns the name of the application.
	 * 
	 * @return the name as string
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * Asks the user, if the window should be closed.
	 * 
	 * An alert window is opened, providing yes/no-buttons. User have to submit,
	 * if they want to close the window. The alert is closed, a boolean is
	 * returned.
	 * 
	 * @param stage
	 *            the stage which should be closed
	 * @return <code>true</code> if stage should be closed, <code>false</code>
	 *         otherwise
	 */
	boolean askForClosingWindow() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(appName + " - Programm beenden");
		alert.setHeaderText("Programm beenden");
		alert.setContentText("Möchten Sie das Programm wirklich beenden?");

		ButtonType btnJa = new ButtonType("Ja");
		ButtonType btnNein = new ButtonType("Nein");

		alert.getButtonTypes().setAll(btnJa, btnNein);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent() && result.get() == btnJa) {
			alert.close();
			return true;
		} else {
			alert.close();
			return false;
		}
	}

	/**
	 * Opens an alert window, showing an information that the expected function
	 * is not available at the moment.
	 */
	void functionNotAvailable() {
		alertInformation.setTitle(appName + " - Hinweis");
		alertInformation.setHeaderText(null);
		alertInformation.setContentText("Diese Funktion steht zur Zeit leider noch nicht zur Verfügung.");
		alertInformation.showAndWait();
	}

	/**
	 * Shows an alert window with some random information about the application.
	 */
	void showApplicationInfo() {
		alertInformation.setTitle(getAppName() + " - Über");
		alertInformation.setHeaderText(getAppName() + " v1.82.02.22.3");
		alertInformation.setGraphic(new ImageView(this.getClass().getResource("../view/einhorn.gif").toString()));
		String content = "Mit Liebe gemacht von\nSimon Stemper, Johannes 'Git-Gott' Trepesch, Christian Ost\n";
		content += "WTFPL 2015 – Do What the Fuck You Want to Public License\n";
		content += "Einhorn-Grafik: \u00A9 unbekannt\n";
		alertInformation.setContentText(content);

		alertInformation.showAndWait();
	}

	/**
	 * Shows an alert window with a passed information text.
	 */
	void showInfo(String info) {
		alertInformation.setTitle(getAppName() + " - Information");
		alertInformation.setHeaderText("Information");
		if (info == null || info.isEmpty()) {
			alertInformation.setContentText(
					"Hier gibt es ein Problem: Es wurden keine weiteren Informationen zum Anzeigen übergeben.");
		} else {
			alertInformation.setContentText(info);
		}

		alertInformation.showAndWait();
	}

	/**
	 * Shows an alert window with an error message. If an empty String as error
	 * message is passed, a default error text is shown.
	 * 
	 * <p>
	 * <b>Precondition</b>
	 * </p>
	 * <ul>
	 * <li>{@code errorMsg} must not be null
	 * </ul>
	 * 
	 * @param errorMsg
	 *            The error message as String
	 */
	void showErrorMessage(String errorMsg) {
		Alert exceptionAlert = new Alert(AlertType.ERROR);
		exceptionAlert.setTitle(appName + " - Fehler");
		exceptionAlert.setHeaderText("Ein Fehler ist aufgetreten.");
		if (errorMsg == null || "".equals(errorMsg)) {
			errorMsg = "Art und Ursprung des Fehlers sind nicht bekannt.";
		}
		exceptionAlert.setContentText(errorMsg);
		exceptionAlert.showAndWait();
	}

	boolean getUserConfirmation(String nameOfAction) {
		if (nameOfAction == null || nameOfAction.isEmpty()) {
			throw new IllegalArgumentException(
					"Bestätigungsfenster kann nicht angezeigt werden, da keine Informationen übergeben wurden.");
		}
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
		confirmationAlert.setTitle(appName + " - Achtung");
		confirmationAlert.setHeaderText(nameOfAction);
		confirmationAlert.setContentText("Möchten Sie diese Aktion wirklich durchführen?");

		Optional<ButtonType> result = confirmationAlert.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			confirmationAlert.close();
			return true;
		} else {
			confirmationAlert.close();
			return false;
		}
	}
}
