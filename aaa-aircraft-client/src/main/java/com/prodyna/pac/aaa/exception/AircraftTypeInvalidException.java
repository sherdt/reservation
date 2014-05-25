package com.prodyna.pac.aaa.exception;

import com.prodyna.pac.aaa.aircraft.AircraftType;
import com.prodyna.pac.aaa.common.exception.AircraftAllocationAppRuntimeException;

/**
 * Use this exception if an {@link AircraftType} is not valid.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class AircraftTypeInvalidException extends AircraftAllocationAppRuntimeException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 5379587202333739702L;

	/**
	 * Constructs a new aircraft type invalid exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public AircraftTypeInvalidException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new aircraft type invalid exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public AircraftTypeInvalidException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new aircraft type invalid exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param errorCode
	 *            Numerical error code.
	 */
	public AircraftTypeInvalidException(final String message, final int errorCode) {
		super(message, errorCode);
	}

	/**
	 * Constructs a new aircraft type invalid exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param errorCode
	 *            Numerical error code.
	 */
	public AircraftTypeInvalidException(final String message, final Throwable cause, final int errorCode) {
		super(message, cause, errorCode);
	}

}
