package de.uniba.kinf.projm.hylleblomst.exceptions;

public class ImportException extends Exception {

	private static final long serialVersionUID = -3036177163236365232L;

	public ImportException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImportException(String message) {
		super(message);
	}

	public ImportException() {
		super();
	}

	public ImportException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ImportException(Throwable arg0) {
		super(arg0);
	}
}
