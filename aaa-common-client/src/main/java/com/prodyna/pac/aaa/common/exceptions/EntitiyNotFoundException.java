package com.prodyna.pac.aaa.common.exceptions;

/**
 * This exception should be used if one tries to get entities from DB and the result could not be found by given
 * criteria.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class EntitiyNotFoundException extends AircraftAllocationAppException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -3296488672336011505L;

	/**
	 * Constructs a new entity not found exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public EntitiyNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new entity not found exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public EntitiyNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
