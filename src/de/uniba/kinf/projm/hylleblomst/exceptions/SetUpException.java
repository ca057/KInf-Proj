package de.uniba.kinf.projm.hylleblomst.exceptions;

public class SetUpException extends Exception {

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
