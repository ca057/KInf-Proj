package de.uniba.kinf.projm.hylleblomst.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Supports the creation of a lovely user interface <3.
 * 
 * @author ca
 *
 */
public class UIHelper {
	/**
	 * The name of the application.
	 */
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
	public boolean askForClosingWindow(Stage stage) {
		if (stage == null) {
			throw new RuntimeException(
					"Stage which should be closed is null (has no value).");
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

	/**
	 * Opens an alert window, showing an information that the expected function
	 * is not available at the moment.
	 */
	public void functionNotAvailable() {
		alertInformation.setTitle(appName + " - Hinweis");
		alertInformation.setHeaderText(null);
		alertInformation
				.setContentText("Diese Funktion steht zur Zeit leider noch nicht zur Verfügung.");
		alertInformation.showAndWait();
	}

	/**
	 * Shows an alert window with some random information about the application.
	 */
	protected void showInfo() {
		alertInformation.setTitle(getAppName() + " - Über");
		alertInformation.setHeaderText(getAppName() + " v1.82.02.22.3");
		alertInformation.setGraphic(new ImageView(this.getClass()
				.getResource("einhorn.gif").toString()));
		String content = "Mit Liebe gemacht von\nSimon Stemper, Johannes Trepesch, Christian Ost";
		content += "\n\u00A9 2015 WTFPL – Do What the Fuck You Want to Public License";
		alertInformation.setContentText(content);

		alertInformation.showAndWait();
	}
}