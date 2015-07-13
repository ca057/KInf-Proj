package de.uniba.kinf.projm.hylleblomst.gui.controller;

import de.uniba.kinf.projm.hylleblomst.gui.model.Model;

/**
 * The main controller for the graphical user interface of the application.
 * Loads the view and initializes all controllers.
 * 
 * @author Christian
 *
 */
public class MainController {

	private Model model;

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
	public MainController(Model model) {
		if (model == null) {
			throw new IllegalArgumentException("Das übergebene Model hat keinen Wert/ist fehlerhaft.");
		}
		this.model = model;
	}

}
