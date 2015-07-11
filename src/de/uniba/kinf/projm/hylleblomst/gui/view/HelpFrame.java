package de.uniba.kinf.projm.hylleblomst.gui.view;

import java.util.InputMismatchException;

import de.uniba.kinf.projm.hylleblomst.gui.controller.HelpFrameController;
import de.uniba.kinf.projm.hylleblomst.gui.controller.ViewHelper;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

	private DoubleProperty width = new SimpleDoubleProperty(700);
	private DoubleProperty height = new SimpleDoubleProperty(300);

	private Font titleFont;

	private BorderPane rootPane;

	public HelpFrame(HelpFrameController helpFrameController) {
		if (helpFrameController == null) {
			throw new InputMismatchException("Der übergebene Controller ist ungültig.");
		}
		this.helpFrameController = helpFrameController;
		viewHelper = new ViewHelper();

		titleFont = Font.font(Font.getDefault().toString(), FontWeight.LIGHT, 20);

		rootPane = createContentPane();
		Scene scene = new Scene(rootPane, width.get(), height.get());
		this.setScene(scene);
		this.getIcons().add(new Image(getClass().getResourceAsStream("unicorn-icon.png")));
		this.setTitle(viewHelper.getAppName() + " - Hilfe");

		setEventHandlers();
		setWindowChangeListener();
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

	private VBox createSearchTabContent() {
		VBox searchBox = new VBox();
		searchBox.setPadding(new Insets(5.0));
		Text title = new Text("Suche");
		title.setFont(titleFont);
		Text mainText = new Text("Hier steht der Hilfetext für die Suche.");
		searchBox.getChildren().addAll(title, mainText);
		return searchBox;
	}

	private VBox createDatabaseTabContent() {
		VBox databaseBox = new VBox();
		databaseBox.setPadding(new Insets(5.0));
		Text title = new Text("Datenbank");
		title.setFont(titleFont);
		Text mainText = new Text("Hier steht der Hilfetext für die Datenbank.");
		databaseBox.getChildren().addAll(title, mainText);
		return databaseBox;
	}

	private VBox createExportTabContent() {
		VBox exportBox = new VBox();
		exportBox.setPadding(new Insets(5.0));
		Text title = new Text("Export");
		title.setFont(titleFont);
		Text mainText = new Text("Fehlt im Ausgangsdatensatz Einschreibedatumsfeldern ein Tag oder Monat, "
				+ "werden diese beim Import in die Datenbank mit zufälligen Werten ersetzt. "
				+ "Die entsprechende Veränderung wird in der Spalte DATUMSFELDERGESETZT gespeichert, "
				+ "die sich bei einer Suche nach dem Einschreibedatum im Export-Ergebnis wiederfindet.\n"
				+ "Der dreistellige Code kann wie folgt gelesen werden:\n"
				+ "- eine 0 steht für eine unveränderte Stelle, eine 1 für eine veränderte Stelle\n"
				+ "- die erste der drei Stellen steht für das Jahr, die zweite für den Monat, die dritte für den Tag"
				+ "- Beispiel: 011 bedeutet, dass das Jahr unverändert blieb und Monat und Tag für den Import angepasst wurden");
		mainText.wrappingWidthProperty().bind(width);
		exportBox.getChildren().addAll(title, mainText);
		return exportBox;
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

	private void setWindowChangeListener() {
		rootPane.getScene().widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				width.setValue(newValue);
			}
		});
		rootPane.getScene().heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				height.setValue(newValue);
			}
		});
	}

}
