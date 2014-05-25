package com.prodyna.pac.aaa.pilot.exception;

import com.prodyna.pac.aaa.common.exception.AircraftAllocationAppRuntimeException;
import com.prodyna.pac.aaa.pilot.Pilot;

/**
 * Use this exception if an {@link Pilot} is not valid.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class PilotInvalidException extends AircraftAllocationAppRuntimeException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 4747774189529822417L;

	/**
	 * Constructs a new pilot invalid exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public PilotInvalidException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new pilot invalid exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public PilotInvalidException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new pilot invalid exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param errorCode
	 *            Numerical error code.
	 */
	public PilotInvalidException(final String message, final int errorCode) {
		super(message, errorCode);
	}

	/**
	 * Constructs a new pilot invalid exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param errorCode
	 *            Numerical error code.
	 */
	public PilotInvalidException(final String message, final Throwable cause, final int errorCode) {
		super(message, cause, errorCode);
	}

}
