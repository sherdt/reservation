package com.prodyna.pac.aaa.auth.exception;

import com.prodyna.pac.aaa.common.exception.AircraftAllocationAppRuntimeException;

/**
 * Use this exception if the authorization is invalid.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class InvalidAuthorizationException extends AircraftAllocationAppRuntimeException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -1695849897273975684L;

	/**
	 * Constructs a new invalid authorization exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public InvalidAuthorizationException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new invalid authorization exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public InvalidAuthorizationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new invalid authorization exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param errorCode
	 *            Numerical error code.
	 */
	public InvalidAuthorizationException(final String message, final int errorCode) {
		super(message, errorCode);
	}

	/**
	 * Constructs a new invalid authorization exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param errorCode
	 *            Numerical error code.
	 */
	public InvalidAuthorizationException(final String message, final Throwable cause, final int errorCode) {
		super(message, cause, errorCode);
	}

}
