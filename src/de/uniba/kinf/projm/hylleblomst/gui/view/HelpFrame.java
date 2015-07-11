package de.uniba.kinf.projm.hylleblomst.gui.view;

import java.util.InputMismatchException;

import de.uniba.kinf.projm.hylleblomst.gui.controller.HelpFrameController;
import de.uniba.kinf.projm.hylleblomst.gui.controller.ViewHelper;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelpFrame extends Stage {

	private ViewHelper viewHelper;
	private HelpFrameController helpFrameController;

	private Font titleFont;

	private BorderPane rootPane;

	public HelpFrame(HelpFrameController helpFrameController) {
		if (helpFrameController == null) {
			throw new InputMismatchException("Der übergebene Controller ist ungültig.");
		}
		this.helpFrameController = helpFrameController;
		viewHelper = new ViewHelper();

		titleFont = Font.font(Font.getDefault().toString(), FontWeight.BOLD, 20);

		rootPane = createContentPane();
		Scene scene = new Scene(rootPane, 700, 300);
		this.setScene(scene);
		this.getIcons().add(new Image(getClass().getResourceAsStream("unicorn-icon.png")));
		this.setTitle(viewHelper.getAppName() + " - Hilfe");

		setEventHandlers();
	}

	private BorderPane createContentPane() {
		BorderPane contentPane = new BorderPane();
		contentPane.setCenter(createTabPane());
		return contentPane;
	}

	private TabPane createTabPane() {
		TabPane helpTabs = new TabPane();
		Tab search = new Tab("Suche");
		search.setContent(createSearchTabContent());
		Tab database = new Tab("Datenbank");
		database.setContent(createDatabaseTabContent());
		Tab export = new Tab("Export");
		export.setContent(createExportTabContent());
		helpTabs.getTabs().addAll(search, database, export);
		helpTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		return helpTabs;
	}

	private Node createSearchTabContent() {
		VBox searchBox = new VBox();
		searchBox.setPadding(new Insets(5.0));
		Text title = new Text("Suche");
		title.setFont(titleFont);

		Text secondText = new Text("Hier steht der Hilfetext für die Suche.");

		searchBox.getChildren().addAll(title, secondText);

		return searchBox;
	}

	private Node createDatabaseTabContent() {
		// TODO Auto-generated method stub
		return null;
	}

	private Node createExportTabContent() {
		// TODO Auto-generated method stub
		return null;
	}

	private void setEventHandlers() {
		rootPane.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE) {
					helpFrameController.closeView();
				}
				event.consume();
			}
		});
	}

}
