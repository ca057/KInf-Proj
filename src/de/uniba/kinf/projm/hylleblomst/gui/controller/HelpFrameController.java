package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.util.Observable;

import de.uniba.kinf.projm.hylleblomst.gui.view.HelpFrame;

public class HelpFrameController implements ControllerInterface {

	private HelpFrame helpFrame;

	public HelpFrameController() {
		helpFrame = new HelpFrame();
	}

	public void showView() {
		helpFrame.show();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
