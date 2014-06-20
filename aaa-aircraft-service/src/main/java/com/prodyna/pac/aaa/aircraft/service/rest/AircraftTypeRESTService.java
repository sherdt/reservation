package com.prodyna.pac.aaa.aircraft.service.rest;

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

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.aircraft.AircraftType;
import com.prodyna.pac.aaa.aircraft.exception.AircraftTypeInvalidException;
import com.prodyna.pac.aaa.aircraft.service.AircraftService;
import com.prodyna.pac.aaa.aircraft.service.AircraftTypeService;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.common.exception.ResponseStatusConstants;
import com.prodyna.pac.aaa.pilot.License;
import com.prodyna.pac.aaa.pilot.LicenseService;

/**
 * REST Service to manage {@link AircraftType}s. One can use this service to create and delete {@link AircraftType}s.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Path("aircraft-type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AircraftTypeRESTService {

	/** Logger for this class. */
	@Inject
	private Logger logger;

	/** Aircraft service. */
	@Inject
	private AircraftService aircraftService;

	/** Aircraft type service. */
	@Inject
	private AircraftTypeService aircraftTypeService;

	/** License service. */
	@Inject
	private LicenseService licenseService;

	/**
	 * @return List with all available aircraft types.
	 */
	@GET
	@Path("list-aircraft-types")
	public List<AircraftType> listAircraftTypes() {
		return this.aircraftTypeService.readAircraftTypes();
	}

	/**
	 * Creates an aircraft type.
	 * 
	 * @param aircraftType
	 *            The aircraft type to create.
	 * 
	 * @return JSON representation of successfully created aircraft type.
	 * 
	 * @throws AircraftTypeInvalidException
	 *             If an aircraft type already exist with the same name.
	 */
	@POST
	@Path("/")
	public AircraftType createAircraftType(final AircraftType aircraftType) {

		// check if aircraft type is set
		final String aircraftTypeName = aircraftType.getName();
		if (aircraftTypeName == null) {
			throw new AircraftTypeInvalidException("Could not create the aircraft type without the name.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if the aircraft type already exists
		try {
			this.aircraftTypeService.readAircraftType(aircraftTypeName);
			throw new AircraftTypeInvalidException("Could not create the aircraft type '" + aircraftTypeName
					+ "', it does exist already.", ResponseStatusConstants.RESOURCE_NOT_CREATED);
		} catch (final EntityNotFoundException e) {
			this.logger.trace("Everything is fine, the aircraft type with the name [{}] doesn't exist yet.",
					aircraftTypeName);
		}

		return this.aircraftTypeService.createAircraftType(aircraftType);
	}

	/**
	 * Deletes an aircraft type.
	 * 
	 * @param aircraftType
	 *            The aircraft type to delete.
	 * @return Given aircraft type.
	 * 
	 * @throws AircraftTypeInvalidException
	 *             If the aircraft type doesn't exist.
	 */
	@DELETE
	@Path("/")
	public AircraftType deleteAircraftType(final AircraftType aircraftType) {

		// check if aircraft type is set
		final String aircraftTypeName = aircraftType.getName();
		if (aircraftTypeName == null || aircraftTypeName.equals("")) {
			throw new AircraftTypeInvalidException("Could not delete the aircraft type without a name.",
					ResponseStatusConstants.RESOURCE_INVALID);
		}

		// check if the aircraft type really exists
		try {
			this.aircraftTypeService.readAircraftType(aircraftTypeName);
		} catch (final EntityNotFoundException e) {
			throw new AircraftTypeInvalidException("Could not delete the aircraft type '" + aircraftTypeName
					+ "', it doesn't exist.", ResponseStatusConstants.RESOURCE_NOT_FOUND);
		}

		// check if aircraft type is used for aircrafts
		final List<Aircraft> aircrafts = this.aircraftService.readAircrafts();
		for (final Aircraft aircraft : aircrafts) {
			if (aircraft.getAircraftType().equals(aircraftType)) {
				throw new AircraftTypeInvalidException(
						"Could not delete the aircraft type, it's used for the aircraft with the tail sign '"
								+ aircraft.getTailSign() + "'.", ResponseStatusConstants.RESOURCE_INVALID);
			}
		}

		// check if aircraft type is used for a license
		final List<License> licenses = this.licenseService.readLicenses();
		for (final License license : licenses) {
			if (license.getAircraftType().equals(aircraftType)) {
				throw new AircraftTypeInvalidException("Could not delete the aircraft type, it '" + aircraftTypeName
						+ "''s used for a license.", ResponseStatusConstants.RESOURCE_INVALID);
			}

		}

		// delete the aircraft type
		this.aircraftTypeService.deleteAircraftType(aircraftTypeName);

		return aircraftType;
	}
}
