package com.prodyna.pac.aaa.common.exception;


/**
 * All exceptions extending this {@link DeleteException} should indicate a problem during deletion of a resource from
 * the database.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public abstract class DeleteException extends AircraftAllocationAppException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -1799314746447512093L;

	/**
	 * Constructs a new delete exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public DeleteException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new delete exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public DeleteException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
