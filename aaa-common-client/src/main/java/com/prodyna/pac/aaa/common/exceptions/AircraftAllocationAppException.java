package com.prodyna.pac.aaa.common.exceptions;

/**
 * All exceptions for aircraft allocation app should use this exception as their base.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public abstract class AircraftAllocationAppException extends Exception {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -3385985550462379312L;

	/**
	 * Constructs a new aircraft allocation app exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public AircraftAllocationAppException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new aircraft allocation app exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public AircraftAllocationAppException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
