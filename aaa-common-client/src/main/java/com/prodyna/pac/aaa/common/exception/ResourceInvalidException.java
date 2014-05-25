package com.prodyna.pac.aaa.common.exception;

/**
 * Use this exception if a resource is not valid or inconsistent to the data stored in the database.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class ResourceInvalidException extends AircraftAllocationAppException {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 6903415823883082471L;

	/**
	 * Constructs a new resource invalid exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public ResourceInvalidException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new resource invalid exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public ResourceInvalidException(final String message, final Throwable cause) {
		super(message, cause);
	}

	// /**
	// * Constructs a new resource invalid exception with the specified error code and the detail message.
	// *
	// * @param message
	// * the detail message.
	// * @param errorCode
	// * Numerical error code.
	// */
	// public ResourceInvalidException(final String message, final int errorCode) {
	// super(message, errorCode);
	// }
	//
	// /**
	// * Constructs a new resource invalid exception with the specified error code and the detail message.
	// *
	// * @param message
	// * the detail message.
	// * @param cause
	// * the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
	// * value is permitted, and indicates that the cause is nonexistent or unknown.)
	// * @param errorCode
	// * Numerical error code.
	// */
	// public ResourceInvalidException(final String message, final Throwable cause, final int errorCode) {
	// super(message, cause, errorCode);
	// }

}
