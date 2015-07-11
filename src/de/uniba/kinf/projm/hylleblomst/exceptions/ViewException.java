package de.uniba.kinf.projm.hylleblomst.exceptions;

/**
 * This exception is thrown if an error occurs during the view of the program.
 * 
 * @author Simon
 *
 */
public class ViewException extends Exception {

	private static final long serialVersionUID = -6395504747881967115L;

	public ViewException() {
		super();
	}

	public ViewException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ViewException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViewException(String message) {
		super(message);
	}

	public ViewException(Throwable cause) {
		super(cause);
	}

}
