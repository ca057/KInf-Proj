package testing.christiansTest.javaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuController {
	private UIHelper ui = new UIHelper();

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
	Button btn_search;

	@FXML
	protected void save() {
		ui.functionNotAvailable();
	}

	@FXML
	protected void closeProgram() {
		Stage stage = (Stage) root.getScene().getWindow();
		if (ui.askForClosingWindow(stage)) {
			stage.close();
		}
	}

	@FXML
	protected void setOptions() {
		ui.functionNotAvailable();
	}

	@FXML
	protected void getHelp() {
		ui.functionNotAvailable();
	}

	@FXML
	protected void showInfo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(ui.getAppName() + " - Über");
		alert.setHeaderText(ui.getAppName() + " v1.82.02.22.3");
		alert.setGraphic(new ImageView(this.getClass()
				.getResource("einhorn.gif").toString()));
		String content = "Mit Liebe gemacht von\nSimon Stemper, Johannes Trepesch, Christian Ost";
		content += "\n\u00A9 2015 WTFPL – Do What the Fuck You Want to Public License";
		alert.setContentText(content);

		alert.showAndWait();
	}

	@FXML
	private void startSearch() {
		ui.functionNotAvailable();
	}
}
