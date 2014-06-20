package com.prodyna.pac.aaa.aircraft.exception;

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.common.exception.AircraftAllocationAppRuntimeException;

/**
 * Use this exception if an {@link Aircraft} is not valid.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class AircraftInvalidException extends AircraftAllocationAppRuntimeException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 3060128387757989423L;

	/**
	 * Constructs a new aircraft invalid exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public AircraftInvalidException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new aircraft invalid exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public AircraftInvalidException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new aircraft invalid exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param errorCode
	 *            Numerical error code.
	 */
	public AircraftInvalidException(final String message, final int errorCode) {
		super(message, errorCode);
	}

	/**
	 * Constructs a new aircraft invalid exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param errorCode
	 *            Numerical error code.
	 */
	public AircraftInvalidException(final String message, final Throwable cause, final int errorCode) {
		super(message, cause, errorCode);
	}

}
