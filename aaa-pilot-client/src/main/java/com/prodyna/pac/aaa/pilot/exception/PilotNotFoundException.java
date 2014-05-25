package com.prodyna.pac.aaa.pilot.exception;

import com.prodyna.pac.aaa.common.exception.AircraftAllocationAppRuntimeException;

/**
 * Use this exception if a pilot could not be found.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class PilotNotFoundException extends AircraftAllocationAppRuntimeException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 6266642941355493493L;

	/**
	 * Constructs a new pilot not found exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public PilotNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new pilot not found exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public PilotNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new pilot not found exception with the specified error code and detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param errorCode
	 *            Numerical error code.
	 */
	public PilotNotFoundException(final String message, final int errorCode) {
		super(message, errorCode);
	}

	/**
	 * Constructs a new pilot not found exception with the specified error code and detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param errorCode
	 *            Numerical error code.
	 */
	public PilotNotFoundException(final String message, final Throwable cause, final int errorCode) {
		super(message, cause, errorCode);
	}

}
