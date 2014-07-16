package com.prodyna.pac.aaa.pilot.service.rest;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.prodyna.pac.aaa.auth.AuthenticationSecured;
import com.prodyna.pac.aaa.auth.Role;
import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.common.exception.ResponseStatusConstants;
import com.prodyna.pac.aaa.pilot.License;
import com.prodyna.pac.aaa.pilot.LicenseService;
import com.prodyna.pac.aaa.pilot.Pilot;
import com.prodyna.pac.aaa.pilot.PilotService;
import com.prodyna.pac.aaa.pilot.exception.LicenseInvalidException;
import com.prodyna.pac.aaa.pilot.exception.PilotInvalidException;

/**
 * REST Service to manage {@link Pilot}s. One can use this service to create, update and delete {@link Pilot}s.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Path("/pilot")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Monitored
public class PilotRESTService {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Pilot service. */
	@Inject
	private PilotService pilotService;

	/** License service. */
	@Inject
	private LicenseService licenseService;

	/**
	 * @return List with all available pilots.
	 */
	@GET
	@Path("/list-pilots")
	public List<Pilot> listPilots() {
		return this.pilotService.readPilots();
	}

	/**
	 * Creates a pilot.
	 * 
	 * @param pilot
	 *            The pilot to create.
	 * 
	 * @return JSON representation of successfully created pilot.
	 * 
	 * @throws PilotInvalidException
	 *             If given pilot to save is invalid. Or a pilot already exists with the same user name.
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
	 * Deletes a pilot.
	 * 
	 * @param username
	 *            The user name of the pilot to delete.
	 * 
	 * @throws PilotInvalidException
	 *             If the pilot doesn't exist.
	 */
	@DELETE
	@Path("/{username}")
	@AuthenticationSecured(role = Role.ADMIN)
	public void deletePilot(@PathParam("username") final String username) {

		// check if user name is set
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
	}

	/**
	 * Adds a license to a pilot.
	 * 
	 * @param username
	 *            User name of the pilot to add the license to.
	 * @param license
	 *            The license to add.
	 * 
	 * @return JSON representation of updated pilot.
	 * 
	 * @throws PilotInvalidException
	 *             If pilot doesn't exists with given user name.
	 */
	@PUT
	@Path("/{username}/add-license")
	@AuthenticationSecured(role = Role.ADMIN)
	public Pilot addPilotLicense(@PathParam("username") final String username, final License license) {

		// check if the pilot already exists
		final Pilot pilot;
		try {
			pilot = this.pilotService.readPilot(username);
		} catch (final EntityNotFoundException e) {
			throw new PilotInvalidException("Could not update the pilots license, the pilot with the user name '"
					+ username + "' doesn't exist.", ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		Set<License> licenses = pilot.getLicenses();
		if (licenses == null) {
			licenses = new TreeSet<License>();
		}
		licenses.add(license);

		return this.pilotService.updatePilot(pilot);
	}

	/**
	 * Deletes a license from the pilot.
	 * 
	 * @param username
	 *            User name of the pilot to delete the license from.
	 * @param licenseId
	 *            The id of the license to remove.
	 * 
	 * @return JSON representation of updated pilot.
	 * 
	 * @throws PilotInvalidException
	 *             If pilot doesn't exists with given user name.
	 */
	@DELETE
	@Path("/{username}/delete-license/{licenseId}")
	@AuthenticationSecured(role = Role.ADMIN)
	public Pilot deletePilotLicense(@PathParam("username") final String username,
			@PathParam("licenseId") final String licenseId) {

		// check if the pilot already exists
		final Pilot pilot;
		try {
			pilot = this.pilotService.readPilot(username);
		} catch (final EntityNotFoundException e) {
			throw new PilotInvalidException("Could not update the pilots license, the pilot with the user name '"
					+ username + "' doesn't exist.", ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		Set<License> licenses = pilot.getLicenses();
		if (licenses == null) {
			licenses = new TreeSet<License>();
		}

		License license;
		try {
			license = this.licenseService.readLicense(licenseId);
		} catch (final EntityNotFoundException e) {
			throw new LicenseInvalidException("Could not update the pilots license, the license doesn't exist.",
					ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		licenses.remove(license);

		return this.pilotService.updatePilot(pilot);
	}
}
