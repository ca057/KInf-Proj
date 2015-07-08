package de.uniba.kinf.projm.hylleblomst.view;

import java.util.Observable;
import java.util.Observer;

public interface ControllerInterface extends Observer {

	void update(Observable o, Object arg);

}
