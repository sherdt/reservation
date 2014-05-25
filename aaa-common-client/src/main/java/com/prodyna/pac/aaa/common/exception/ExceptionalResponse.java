package com.prodyna.pac.aaa.common.exception;

import java.io.Serializable;

/**
 * Wrapper for an exceptional response of a REST service call.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class ExceptionalResponse implements Serializable {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 6858252454684438119L;

	/** Numerical error code. */
	private int errorCode;

	/** The error message. */
	private String errorMessage;

	/**
	 * Default constructor.
	 */
	public ExceptionalResponse() {
	}

	/**
	 * Convenient constructor setting the error code and message.
	 * 
	 * @param errorCode
	 *            Error code to use for this instance.
	 * @param errorMessage
	 *            Error message to use for this instance.
	 */
	public ExceptionalResponse(final int errorCode, final String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
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

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.errorCode;
		result = prime * result + ((this.errorMessage == null) ? 0 : this.errorMessage.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ExceptionalResponse)) {
			return false;
		}
		final ExceptionalResponse other = (ExceptionalResponse) obj;
		if (this.errorCode != other.errorCode) {
			return false;
		}
		if (this.errorMessage == null) {
			if (other.errorMessage != null) {
				return false;
			}
		} else if (!this.errorMessage.equals(other.errorMessage)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ExceptionalResponse [errorCode=").append(this.errorCode).append(", errorMessage=")
				.append(this.errorMessage).append("]");
		return builder.toString();
	}

}
