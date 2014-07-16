package com.prodyna.pac.aaa.pilot.service.rest;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.prodyna.pac.aaa.aircraft.AircraftType;
import com.prodyna.pac.aaa.auth.AuthenticationSecured;
import com.prodyna.pac.aaa.auth.Role;
import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.common.exception.ResponseStatusConstants;
import com.prodyna.pac.aaa.pilot.License;
import com.prodyna.pac.aaa.pilot.LicenseService;
import com.prodyna.pac.aaa.pilot.exception.LicenseInvalidException;
import com.prodyna.pac.aaa.pilot.exception.PilotInvalidException;

/**
 * REST Service to manage {@link License}s. One can use this service to create, update and delete {@link License}s.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Path("license")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Monitored
public class LicenseRESTService {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** License service. */
	@Inject
	private LicenseService licenseService;

	/**
	 * Creates a license.
	 * 
	 * @param license
	 *            The license to create.
	 * 
	 * @return JSON representation of successfully created license.
	 * 
	 * @throws LicenseInvalidException
	 *             If given license to create is invalid or a license already exists with the same id.
	 */
	@POST
	@Path("/")
	@AuthenticationSecured(role = Role.ADMIN)
	public License createLicense(final License license) {

		// check the id and set it if the client missed to set it
		if (license.getId() == null) {
			license.setId(UUID.randomUUID().toString());
		}

		// check if aircraft type is set
		final AircraftType aircraftType = license.getAircraftType();
		if (aircraftType == null) {
			throw new LicenseInvalidException("Could not create the license without an aircraft type.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if the license already exists
		try {
			this.licenseService.readLicense(license.getId());
			throw new LicenseInvalidException("Could not create the license with id '" + license.getId()
					+ "', it does exist already.", ResponseStatusConstants.RESOURCE_NOT_CREATED);
		} catch (final EntityNotFoundException e) {
			this.logger.trace("Everything is fine, the license with the id [{}] doesn't exist yet.", license.getId());
		}

		// create the license
		final License createdlicense = this.licenseService.createLicense(license);

		return createdlicense;
	}

	/**
	 * Deletes a license.
	 * 
	 * @param license
	 *            The license to delete.
	 * @return Given license.
	 * 
	 * @throws LicenseInvalidException
	 *             If the license doesn't exist.
	 */
	@DELETE
	@Path("/{id}")
	@AuthenticationSecured(role = Role.ADMIN)
	public License deleteLicense(final License license) {

		// check if id is set
		final String id = license.getId();
		if (id == null || "".equals(id)) {
			throw new LicenseInvalidException("Could not delete the license without an id.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if the license really exists
		try {
			this.licenseService.readLicense(id);
		} catch (final EntityNotFoundException e) {
			throw new PilotInvalidException("Could not delete the license, the license with the id '" + id
					+ "' doesn't exist.", ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		this.licenseService.deleteLicense(id);

		return license;
	}
}
