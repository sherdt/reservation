package com.prodyna.pac.aaa.auth.service;

import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.Md5Crypt;
import org.slf4j.Logger;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.prodyna.pac.aaa.auth.Credentials;
import com.prodyna.pac.aaa.auth.CredentialsChange;
import com.prodyna.pac.aaa.auth.Role;
import com.prodyna.pac.aaa.auth.exception.InvalidAuthorizationException;
import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.common.exception.ResponseStatusConstants;
import com.prodyna.pac.aaa.pilot.Pilot;
import com.prodyna.pac.aaa.pilot.PilotService;

/**
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Path("/authentication")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Monitored
public class AuthenticationRESTService {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Pilot service to retrieve the pilot by name and check his password. */
	@Inject
	private PilotService pilotService;

	/** Credentials object to place extracted credentials to. */
	@Inject
	private Credentials credentials;

	/**
	 * Use this method to authenticate the client. Given credentials are checked and if they are valid a JWT token is
	 * created and returned. The client has to provide the token in further requests as
	 * <code>Authorization: Bearer <i>TOKEN</i></code> to access secured methods.
	 * 
	 * @param loginCredentials
	 *            Credentials containing the user name and the password.
	 * @return JWT Token for further request to 'secured' methods.
	 * @throws JOSEException
	 *             If signing the payload inside token did not work.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public String login(final Credentials loginCredentials) throws JOSEException {

		final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		objectBuilder.add("username", loginCredentials.getUsername());

		this.credentials.setUsername(loginCredentials.getUsername());
		try {
			if ("admin".equals(loginCredentials.getUsername()) && "Test123!".equals(loginCredentials.getPassword())) {
				objectBuilder.add("name", "Admin");
				objectBuilder.add("role", Role.ADMIN.toString());

			} else if (Md5Crypt.md5Crypt(loginCredentials.getPassword().getBytes(Charset.forName("UTF-8")), "$1$aaa")
					.equals(new String(this.pilotService.readPilot(loginCredentials.getUsername()).getPassword()))) {
				final Pilot pilot = this.pilotService.readPilot(loginCredentials.getUsername());
				objectBuilder.add("name", pilot.getName());
				objectBuilder.add("mail", pilot.getEmail());
				objectBuilder.add("role", Role.PILOT.toString());
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

		// reaching the following code means user is authenticated
		this.logger.trace("User [{}] has provided correct credentials.", loginCredentials.getUsername());

		// Create JWS header with HS256 algorithm
		final JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
		header.setContentType("application/json");

		// Create HMAC signer
		final String sharedKey = "this-is-the-very-private-shared-key";
		final JWSSigner signer = new MACSigner(sharedKey.getBytes());

		// Create JWS payload
		final Payload payload = new Payload(objectBuilder.build().toString());

		// Create JWS object
		final JWSObject jwsObject = new JWSObject(header, payload);
		jwsObject.sign(signer);

		return jwsObject.serialize();
	}

	/**
	 * Use this method to change the password for a user.
	 * 
	 * @param credentialsChange
	 *            CredentialsChange containing the user name, the old and the new password.
	 * @return Message for the client if everything works as expected.
	 * 
	 * @throws InvalidAuthorizationException
	 *             If the old password is incorrect for the user.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/changePassword")
	public String changePassword(final CredentialsChange credentialsChange) {

		final String resultMessage;

		try {
			if ("admin".equals(credentialsChange.getUsername())) {
				throw new InvalidAuthorizationException("You are not allowed to modify the password for the admin.",
						ResponseStatusConstants.UNAUTHORIZED);
			} else if (Md5Crypt.md5Crypt(credentialsChange.getPassword().getBytes(Charset.forName("UTF-8")), "$1$aaa")
					.equals(new String(this.pilotService.readPilot(credentialsChange.getUsername()).getPassword()))) {

				final Pilot pilot = this.pilotService.readPilot(credentialsChange.getUsername());

				final String newPassword = Md5Crypt.md5Crypt(
						credentialsChange.getNewPassword().getBytes(Charset.forName("UTF-8")), "$1$aaa");
				pilot.setPassword(newPassword);
				this.pilotService.updatePilot(pilot);
				resultMessage = "The password has been changed successfully.";
			} else {

				throw new InvalidAuthorizationException(
						"Provided username and/or password is wrong, please try another combination.",
						ResponseStatusConstants.UNAUTHORIZED);
			}

		} catch (final EntityNotFoundException e) {
			throw new InvalidAuthorizationException(
					"Provided username and/or password is wrong, please try another combination.", e,
					ResponseStatusConstants.UNAUTHORIZED);
		}

		return resultMessage;
	}
}
