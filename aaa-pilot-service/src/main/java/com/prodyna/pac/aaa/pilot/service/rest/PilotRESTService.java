package com.prodyna.pac.aaa.pilot.service.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.prodyna.pac.aaa.auth.AuthenticationSecured;
import com.prodyna.pac.aaa.auth.Role;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.common.exception.ResponseStatusConstants;
import com.prodyna.pac.aaa.pilot.Pilot;
import com.prodyna.pac.aaa.pilot.PilotService;
import com.prodyna.pac.aaa.pilot.exception.PilotInvalidException;

/**
 * REST Service to manage {@link Pilot}s. One can use this service to create, update and delete {@link Pilot}s.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Path("pilot")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AuthenticationSecured(role = Role.PILOT)
public class PilotRESTService {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Pilot service. */
	@Inject
	private PilotService pilotService;

	/**
	 * @return List with all available pilots.
	 */
	@GET
	@Path("list-pilots")
	public List<Pilot> listPilots() {
		return this.pilotService.readPilots();
	}

	/**
	 * Creates an pilot.
	 * 
	 * @param pilot
	 *            The pilot to create.
	 * 
	 * @return JSON representation of successfully created pilot.
	 * 
	 * @throws PilotInvalidException
	 *             If given pilot to save is invalid. Or an pilot already exists with the same user name.
	 */
	@POST
	@Path("/")
	@AuthenticationSecured(role = Role.ADMIN)
	public Pilot createPilot(final Pilot pilot) {

		// check if user name is set
		final String username = pilot.getUsername();
		if (username == null || username.equals("")) {
			throw new PilotInvalidException("Could not create the pilot without a user name.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if the pilot already exists
		try {
			this.pilotService.readPilot(username);
			throw new PilotInvalidException("Could not create the pilot '" + username + "', it does exist already.",
					ResponseStatusConstants.RESOURCE_NOT_CREATED);
		} catch (final EntityNotFoundException e) {
			this.logger.trace("Everything is fine, the pilot with the user name [{}] doesn't exist yet.", username);
		}

		// create the pilot
		final Pilot createdPilot = this.pilotService.createPilot(pilot);

		return createdPilot;
	}

	/**
	 * Deletes an pilot.
	 * 
	 * @param pilot
	 *            The pilot to delete.
	 * @return Given pilot.
	 * 
	 * @throws PilotInvalidException
	 *             If the pilot doesn't exist.
	 */
	@DELETE
	@Path("/")
	@AuthenticationSecured(role = Role.ADMIN)
	public Pilot deletePilot(final Pilot pilot) {

		// check if user name is set
		final String username = pilot.getUsername();
		if (username == null || username.equals("")) {
			throw new PilotInvalidException("Could not delete the pilot without a user name.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if the pilot really exists
		try {
			this.pilotService.readPilot(username);
		} catch (final EntityNotFoundException e) {
			throw new PilotInvalidException("Could not delete the pilot, the pilot with the user name '" + username
					+ "' doesn't exist.", ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		this.pilotService.deletePilot(username);

		return pilot;
	}
}
