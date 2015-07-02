package de.uniba.kinf.projm.hylleblomst.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainGUI extends Application {
	private UIHelper ui = new UIHelper();

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass()
					.getResource("hylleblomstView.fxml"));

			Scene scene = new Scene(root);

			primaryStage.setTitle(ui.getAppName());
			primaryStage.setScene(scene);
			primaryStage.show();

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					primaryStage.close();
					// TODO check if correct
					System.exit(0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
