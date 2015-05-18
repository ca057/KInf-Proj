package testing.christiansTest.javaFX;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource(
					"mainview.fxml"));

			Scene scene = new Scene(root, 600, 400);
			primaryStage.setTitle("fancy Application v1.82.02.22.3");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					closeWindow(primaryStage);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeWindow(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("fancy Application - Programm beenden");
		alert.setHeaderText("Programm beenden");
		alert.setContentText("Möchten Sie das Programm wirklich beenden?");

		ButtonType btnJa = new ButtonType("Ja");
		ButtonType btnNein = new ButtonType("Nein");

		alert.getButtonTypes().setAll(btnJa, btnNein);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == btnJa) {
			alert.close();
			stage.close();
		} else {
			alert.close();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
