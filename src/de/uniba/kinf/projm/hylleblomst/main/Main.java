package de.uniba.kinf.projm.hylleblomst.main;

import de.uniba.kinf.projm.hylleblomst.gui.model.Model;
import de.uniba.kinf.projm.hylleblomst.logic.SearchInitiator;
import de.uniba.kinf.projm.hylleblomst.logicImpl.SearchInitiatorImpl;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Logik starten
		SearchInitiator initiator = new SearchInitiatorImpl();
		// Model starten
		Model model = new Model(initiator);
		// View starten
	}
}
