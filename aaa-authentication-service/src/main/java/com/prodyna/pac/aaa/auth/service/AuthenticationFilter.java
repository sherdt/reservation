package com.prodyna.pac.aaa.auth.service;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.prodyna.pac.aaa.auth.Credentials;
import com.prodyna.pac.aaa.auth.exception.InvalidAuthorizationException;
import com.prodyna.pac.aaa.common.exception.ResponseStatusConstants;

/**
 * Only OAuth authentication is supported. The credentials are extracted from request and placed to injected credentials
 * object witch should be @RequestScoped.
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Credentials object to place extracted credentials to. */
	@Inject
	private Credentials credentials;

	@Override
	public void filter(final ContainerRequestContext requestCtx) throws IOException {
		final String auth = requestCtx.getHeaderString("Authorization");

		if (auth == null || !auth.startsWith("Bearer ")) {
			this.logger.trace("Authorization header not set in request.");
			return;
		}

		boolean verifiedSignature = false;
		String usernameInToken = null;
		try {
			final String authToken = auth.substring("Bearer ".length());

			// Parse the OAuth token and check signature
			final JWSObject jwsObject = JWSObject.parse(authToken);
			final String sharedKey = "this-is-the-very-private-shared-key";
			final JWSVerifier verifier = new MACVerifier(sharedKey.getBytes());
			verifiedSignature = jwsObject.verify(verifier);
			usernameInToken = jwsObject.getPayload().toJSONObject().get("username").toString();
		} catch (final Exception e) {
			this.logger.debug("Authorization parameter [{}] in request is invalid", auth, e);
		}

		if (verifiedSignature) {
			this.logger.trace("Found valid auth token in request for user [{}].", usernameInToken);
			this.credentials.setUsername(usernameInToken);
		} else {
			throw new InvalidAuthorizationException(
					"Provided token is invalid, plase use login method to get a valid token.",
					ResponseStatusConstants.UNAUTHORIZED);
		}
	}
	
}
