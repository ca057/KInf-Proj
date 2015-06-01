package de.uniba.kinf.projm.hylleblomst.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource(
					"hylleblomstView.fxml"));

			Scene scene = new Scene(root);
			primaryStage.setTitle("hylleblomst");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
