package com.prodyna.pac.aaa.exception;

import com.prodyna.pac.aaa.common.exception.DeleteException;

/**
 * This exception should be used if one tries to delete an referenced aircraft from the database.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class AircraftDeleteException extends DeleteException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -2608521973009285098L;

	/**
	 * Constructs a new aircraft delete exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public AircraftDeleteException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new aircraft delete exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public AircraftDeleteException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
