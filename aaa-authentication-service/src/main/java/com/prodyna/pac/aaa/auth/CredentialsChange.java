package com.prodyna.pac.aaa.auth;

/**
 * A request scoped inject-able bean that contains the credentials. It will be written by a JAX-RS specific Interceptor
 * on each request.
 */
/**
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class CredentialsChange {

	/** User name part of the credentials. */
	private String username;

	/** Password part of the credentials. */
	private String password;

	/** New password. */
	private String newPassword;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return this.newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return this.username + ":*******";
	}
}
