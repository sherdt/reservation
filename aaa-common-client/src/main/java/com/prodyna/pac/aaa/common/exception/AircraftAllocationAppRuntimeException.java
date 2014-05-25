package com.prodyna.pac.aaa.common.exception;

/**
 * All runtime exceptions for aircraft allocation app should use this exception as their base.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public abstract class AircraftAllocationAppRuntimeException extends RuntimeException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -4733052187045276257L;

	/** Numerical error code. */
	private int errorCode;

	/**
	 * Constructs a new aircraft allocation app runtime exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public AircraftAllocationAppRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new aircraft allocation app runtime exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public AircraftAllocationAppRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new aircraft allocation app runtime exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param errorCode
	 *            Numerical error code.
	 */
	public AircraftAllocationAppRuntimeException(final String message, final int errorCode) {
		this(message);
		this.errorCode = errorCode;
	}

	/**
	 * Constructs a new aircraft allocation app runtime exception with the specified error code and the detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param errorCode
	 *            Numerical error code.
	 */
	public AircraftAllocationAppRuntimeException(final String message, final Throwable cause, final int errorCode) {
		this(message, cause);
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return this.errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(final int errorCode) {
		this.errorCode = errorCode;
	}

}
