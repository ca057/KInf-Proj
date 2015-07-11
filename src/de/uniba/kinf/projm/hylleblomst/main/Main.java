package de.uniba.kinf.projm.hylleblomst.main;

import de.uniba.kinf.projm.hylleblomst.gui.controller.ViewHelper;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This program provides a possibility to analyze database data. It was
 * developed for the specific case of students. Therefore, the core of the
 * database is a table called "Person" which is the junction to all other
 * tables. <br/>
 * The <i>database</i> is a perfectly thought-out instrument to store
 * information. The <i>GUI</i> is made to enable a fast search of the most
 * important information - with detailed knowledge only one mouse click ahead.
 * In between, the <i>logic</i> fulfills all wishes of the user and to returns
 * all desired information.
 * <p>
 * <b>Features:</b><br/>
 * <ul>
 * <li>good availability of all data of a file imported to database (i.e. csv)
 * <li>import / export possibility
 * <li>strict character-by-character search or open search
 * <li>search of events in a period of time
 * </ul>
 * 
 * @author Christian, Johannes, Simon - with love!
 *
 */
public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Logik starten
		// SearchInitiator initiator = new SearchInitiatorImpl();
		// Model starten
		// Model model = new Model(initiator);
		// ViewController starten und Stage übergeben
		int exceptionCounter = 0;
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../gui/view/hylleblomstView.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle(new ViewHelper().getAppName());
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../gui/view/unicorn-icon.png")));
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent we) {
					primaryStage.close();
					System.exit(0);
				}
			});
		} catch (Exception e) {
			// TODO wenn hier eine Exception ankommt, müsste diese kritsch sein:
			// Programm beenden und neu starten
			exceptionCounter++;
			System.out.println("Excpetions in Main: " + exceptionCounter);
			e.printStackTrace();
		}
	}
}
