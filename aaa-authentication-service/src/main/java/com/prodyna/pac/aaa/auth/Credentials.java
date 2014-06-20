package com.prodyna.pac.aaa.auth;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * A request scoped inject-able bean that contains the credentials. It will be written by a JAX-RS specific Interceptor
 * on each request.
 */
/**
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Named
@RequestScoped
public class Credentials {

	/** User name part of the credentials. */
	private String username;

	/** Password as byte array as part of the credentials. */
	private byte[] password;

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
	public byte[] getPassword() {
		return this.password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(final byte[] password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return this.username + ":*******";
	}
}
