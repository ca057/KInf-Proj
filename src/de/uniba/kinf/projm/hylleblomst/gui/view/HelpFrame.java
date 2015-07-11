package de.uniba.kinf.projm.hylleblomst.gui.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelpFrame extends Stage {

	private BorderPane rootPane;

	public HelpFrame() {
		rootPane = createContentPane();
		Scene scene = new Scene(rootPane, 700, 500);
		this.setScene(scene);

		setEventHandlers();
	}

	private BorderPane createContentPane() {
		BorderPane contentPane = new BorderPane();
		return contentPane;
	}

	private void setEventHandlers() {
		// TODO Auto-generated method stub

	}

}
