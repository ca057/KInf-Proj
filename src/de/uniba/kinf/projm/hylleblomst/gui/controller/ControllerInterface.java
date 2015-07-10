package de.uniba.kinf.projm.hylleblomst.gui.controller;

import java.util.Observable;
import java.util.Observer;

public interface ControllerInterface extends Observer {

	void update(Observable o, Object arg);

}
