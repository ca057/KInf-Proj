package testing.christiansTest.javaFX;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	private UIHelper ui = new UIHelper();

	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource(
					"mainview.fxml"));

			Scene scene = new Scene(root, 600, 400);
			primaryStage.setTitle(ui.getAppName() + " v1.82.02.22.3");
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					if (ui.askForClosingWindow(primaryStage)) {
						primaryStage.close();
					} else {
						we.consume();
					}
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
