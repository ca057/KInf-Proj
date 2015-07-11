package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.util.Optional;

import de.uniba.kinf.projm.hylleblomst.keys.SourceKeys;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

/**
 * Supports the creation of a lovely user interface <3.
 *
 */
public class ViewHelper {

	private String appName = "Hylleblomst";

	private Alert alertInformation;

	private Alert alertConfirmation;

	public ViewHelper() {
		alertInformation = new Alert(AlertType.INFORMATION);
		alertConfirmation = new Alert(AlertType.CONFIRMATION);
	}

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
		alertConfirmation.setTitle(appName + " - Programm beenden");
		alertConfirmation.setHeaderText("Programm beenden");
		alertConfirmation.setContentText("Möchten Sie das Programm wirklich beenden?");

		ButtonType btnJa = new ButtonType("Ja");
		ButtonType btnNein = new ButtonType("Nein");

		alertConfirmation.getButtonTypes().setAll(btnJa, btnNein);

		Optional<ButtonType> result = alertConfirmation.showAndWait();

		if (result.isPresent() && result.get() == btnJa) {
			alertConfirmation.close();
			return true;
		} else {
			alertConfirmation.close();
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
		alertInformation.setTitle(appName + " - Über");
		alertInformation.setHeaderText(appName + " v1.82.02.22.3");
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
		alertConfirmation.setTitle(appName + " - Achtung");
		alertConfirmation.setHeaderText(nameOfAction);
		alertConfirmation.setContentText("Möchten Sie diese Aktion wirklich durchführen?");

		Optional<ButtonType> result = alertConfirmation.showAndWait();

		if (result.isPresent() && result.get() == ButtonType.OK) {
			alertConfirmation.close();
			return true;
		} else {
			alertConfirmation.close();
			return false;
		}
	}

	/**
	 * Checks the value of the selection for the source of the first name and
	 * returns the corresponding {@link SourceKey}.
	 * 
	 * @param value
	 *            the user input as String
	 * @return the corresponding {@link SourceKey} as {@code String}
	 */
	int getSourceKeyByValueAsString(String value) {
		if ("Standard".equals(value)) {
			return SourceKeys.STANDARD;
		} else if ("keine Quelle".equals(value)) {
			return SourceKeys.NO_SOURCE;
		} else if ("normalisiert".equals(value)) {
			return SourceKeys.NORM;
		} else if ("Abweichung normalisiert".equals(value)) {
			return SourceKeys.ORT_NORM_AB;
		} else if ("HS B (AUB, I 11)".equals(value)) {
			return SourceKeys.HSB_AUB_I11;
		} else if ("HS C (AUB, I 13/1)".equals(value)) {
			return SourceKeys.HSC_AUB_I131;
		} else if ("HS D (AUB, I 13/2)".equals(value)) {
			return SourceKeys.HSD_AUB_I132;
		} else if ("HS E (AUB, I 9)".equals(value)) {
			return SourceKeys.HSE_AUB_I9;
		} else if ("HS F (AUB, I 8)".equals(value)) {
			return SourceKeys.HSF_AUB_I8;
		} else if ("HS G (AUB, I 6)".equals(value)) {
			return SourceKeys.HSG_AUB_I6;
		} else if ("HS H (AEB, Rep. I, Nr. 321)".equals(value)) {
			return SourceKeys.HSH_AEB_I321;
		} else if ("HS I (SB Bamberg, Msc.Add.3a)".equals(value)) {
			return SourceKeys.HSI_SB_3a;
		} else if ("HS J (SB Bamberg, Msc.Add.3)".equals(value)) {
			return SourceKeys.HSJ_3;
		} else if ("AUB, V E 38".equals(value)) {
			return SourceKeys.AUB_V_E38;
		}
		return SourceKeys.NO_SELECTION;
	}
}
