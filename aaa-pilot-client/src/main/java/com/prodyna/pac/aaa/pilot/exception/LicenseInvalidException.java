package com.prodyna.pac.aaa.pilot.exception;

import com.prodyna.pac.aaa.common.exception.AircraftAllocationAppRuntimeException;
import com.prodyna.pac.aaa.pilot.License;

/**
 * Use this exception if an {@link License} is not valid.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class LicenseInvalidException extends AircraftAllocationAppRuntimeException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -446880544279933131L;

	/**
	 * Constructs a new license invalid exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public LicenseInvalidException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new license invalid exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public LicenseInvalidException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new license invalid exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param errorCode
	 *            Numerical error code.
	 */
	public LicenseInvalidException(final String message, final int errorCode) {
		super(message, errorCode);
	}

	/**
	 * Constructs a new license invalid exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param errorCode
	 *            Numerical error code.
	 */
	public LicenseInvalidException(final String message, final Throwable cause, final int errorCode) {
		super(message, cause, errorCode);
	}

}
