package com.prodyna.pac.aaa.aircraft.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.aaa.aircraft.AircraftType;
import com.prodyna.pac.aaa.aircraft.exception.AircraftTypeInvalidException;
import com.prodyna.pac.aaa.common.annotation.Monitored;

/**
 * REST service facade for {@link AircraftType}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Path("/aircraft-type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Monitored
public interface AircraftTypeRESTService {

	/**
	 * @return List with all available aircraft types.
	 */
	@GET
	@Path("/list-aircraft-types")
	List<AircraftType> listAircraftTypes();

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
	AircraftType createAircraftType(AircraftType aircraftType);

	/**
	 * Deletes an aircraft type by given aircraft type name.
	 * 
	 * @param aircraftTypeName
	 *            The aircraft type name to delete.
	 * 
	 * @throws AircraftTypeInvalidException
	 *             If the aircraft type doesn't exist.
	 */
	@DELETE
	@Path("/{id}")
	void deleteAircraftType(@PathParam("id") final String aircraftTypeName);

}