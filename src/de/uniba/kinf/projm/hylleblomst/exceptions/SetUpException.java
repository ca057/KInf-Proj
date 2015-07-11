package de.uniba.kinf.projm.hylleblomst.exceptions;

/**
 * This exception is thrown when an error occurs during the setup of the
 * database.
 * 
 * @author Simon
 *
 */
public class SetUpException extends Exception {

	private static final long serialVersionUID = 2925012631241306505L;

	public SetUpException() {
	}

	public SetUpException(String message) {
		super(message);
	}

	public SetUpException(Throwable cause) {
		super(cause);
	}

	public SetUpException(String message, Throwable cause) {
		super(message, cause);
	}

	public SetUpException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
