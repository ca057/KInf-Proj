package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.io.IOException;

import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The main controller for the graphical user interface of the application.
 * Loads the view and initializes all controllers.
 * 
 * @author Christian
 *
 */
public class StartController {

	private Model model;

	private Stage mainStage;

	private MainController mainController;

	/**
	 * Constructor for a new MainController. Needs a {@link Model} as parameter
	 * for setting all needed controllers and passing them the {@link Model} as
	 * argument.
	 * 
	 * <p>
	 * <b>Precondition</b>
	 * <ul>
	 * <li>the {@link Model} must not be {@code null}</li>
	 * </ul>
	 * </p>
	 * 
	 * @param model
	 *            the {@link Model} needed for accessing the logic of the
	 *            application
	 */
	public StartController(Model model) {
		if (model == null) {
			throw new IllegalArgumentException("Das übergebene Model hat keinen Wert/ist fehlerhaft.");
		}
		this.model = model;
		setUpView();
	}

	/*
	 * Makes all setups for the view. The {@link FXMLLoader} loads in the main
	 * fxml-document, the {@link Stage} and {@link Scene} is set and the {@link
	 * MainController} is set. After loading in all resources, the {@link Model}
	 * is set to the {@link MainController} and all of its 'sub'-controllers.
	 */
	private void setUpView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/hylleblomstView.fxml"));
			BorderPane rootPane = (BorderPane) loader.load();

			Scene scene = new Scene(rootPane);
			mainStage = new Stage();
			mainStage.setScene(scene);

			mainController = loader.getController();
			mainController.setModel(model);
			mainController.setStartController(this);

			mainStage.setTitle(new ViewHelper().getAppName());
			mainStage.getIcons().add(new Image(getClass().getResourceAsStream("../view/unicorn-icon.png")));
			mainStage.show();
			mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent we) {
					mainStage.close();
					System.exit(0);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for starting the {@link Stage}.
	 * 
	 * <p>
	 * <b>Precondition</b>
	 * <ul>
	 * <li>stage to show must not be {@code null}</li>
	 * </ul>
	 * </b>
	 */
	public void showView() {
		if (mainStage != null) {
			mainStage.show();
		}
	}

	/**
	 * Method for closing the {@link Stage}.
	 * 
	 * <p>
	 * <b>Precondition</b>
	 * <ul>
	 * <li>stage to close must not be {@code null}</li>
	 * </ul>
	 * </b>
	 */
	public void closeView() {
		mainStage.close();
	}
}
