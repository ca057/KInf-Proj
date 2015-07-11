package de.uniba.kinf.projm.hylleblomst.exceptions;

/**
 * This exception is thrown if an error occurs while exporting data.
 * 
 * @author Simon
 *
 */
public class ExportException extends Exception {

	private static final long serialVersionUID = -625283672497630065L;

	public ExportException() {
	}

	public ExportException(String arg0) {
		super(arg0);
	}

	public ExportException(Throwable arg0) {
		super(arg0);
	}

	public ExportException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ExportException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
