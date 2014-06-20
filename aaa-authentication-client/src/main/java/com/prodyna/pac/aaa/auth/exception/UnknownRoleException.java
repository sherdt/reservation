package com.prodyna.pac.aaa.auth.exception;

import com.prodyna.pac.aaa.auth.Role;
import com.prodyna.pac.aaa.common.exception.AircraftAllocationAppRuntimeException;

/**
 * Use this exception if an {@link Role} is unknown.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class UnknownRoleException extends AircraftAllocationAppRuntimeException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -905016756824857331L;

	/**
	 * Constructs a new unknown role exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public UnknownRoleException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new unknown role exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public UnknownRoleException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new unknown role exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param errorCode
	 *            Numerical error code.
	 */
	public UnknownRoleException(final String message, final int errorCode) {
		super(message, errorCode);
	}

	/**
	 * Constructs a new unknown role exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param errorCode
	 *            Numerical error code.
	 */
	public UnknownRoleException(final String message, final Throwable cause, final int errorCode) {
		super(message, cause, errorCode);
	}

}
