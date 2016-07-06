package de.uniba.kinf.projm.hylleblomst.exceptions;

/**
 * This exception is thrown if an error occurs during the view of the program.
 * 
 * @author Simon
 *
 */
public class SearchException extends Exception {

	private static final long serialVersionUID = -6395504747881967115L;

	public SearchException() {
		super();
	}

	public SearchException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SearchException(String message, Throwable cause) {
		super(message, cause);
	}

	public SearchException(String message) {
		super(message);
	}

	public SearchException(Throwable cause) {
		super(cause);
	}

}
