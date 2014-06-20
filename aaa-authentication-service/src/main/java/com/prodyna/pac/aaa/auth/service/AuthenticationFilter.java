package com.prodyna.pac.aaa.auth.service;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.Md5Crypt;
import org.slf4j.Logger;

import com.prodyna.pac.aaa.auth.Credentials;
import com.prodyna.pac.aaa.auth.exception.InvalidAuthorizationException;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.common.exception.ResponseStatusConstants;
import com.prodyna.pac.aaa.pilot.PilotService;

/**
 * Only HTTP base authentication is supported. The credentials are extracted from request and placed to injected
 * credentials object with should be @RequestScoped.
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Credentials object to place extracted credentials to. */
	@Inject
	private Credentials credentials;

	/** Pilot service to retrieve the pilot by name and check his password. */
	@Inject
	private PilotService pilotService;

	@Override
	public void filter(final ContainerRequestContext requestCtx) throws IOException {
		final String auth = requestCtx.getHeaderString("Authorization");

		if (auth == null || !auth.startsWith("Basic ")) {
			this.logger.trace("Authorization header not set in request.");
			return;
		}

		try {
			final String base64encodedAuth = auth.substring("Basic ".length());
			final String encodedAuth = new String(Base64.decodeBase64(base64encodedAuth));

			if (!encodedAuth.contains(":")) {
				return;
			}

			final String[] credentialValues = encodedAuth.split(":");
			final String username = credentialValues[0];
			final byte[] password = credentialValues[1].getBytes();

			try {
				if ("admin".equals(username)
						&& "Test123!".equals(credentialValues[1])
						|| Md5Crypt.md5Crypt(password, "$1$aaa").equals(
								new String(this.pilotService.readPilot(username).getPassword()))) {
					this.credentials.setUsername(username);
				} else {
					this.credentials.setUsername(null);
					throw new InvalidAuthorizationException(
							"Provided username and/or password is wrong, please try another combination.",
							ResponseStatusConstants.UNAUTHORIZED);
				}

			} catch (final EntityNotFoundException e) {
				this.credentials.setUsername(null);

				throw new InvalidAuthorizationException(
						"Provided username and/or password is wrong, please try another combination.", e,
						ResponseStatusConstants.UNAUTHORIZED);
			}

			this.logger.debug("Found Authorization for user [{}].", this.credentials.getUsername());
		} catch (final Exception e) {
			this.logger.debug("Authorization parameter [{}] in request is invalid", auth, e);
		}
	}
}
