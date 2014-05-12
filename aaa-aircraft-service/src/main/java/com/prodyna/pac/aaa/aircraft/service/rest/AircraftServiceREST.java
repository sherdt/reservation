package com.prodyna.pac.aaa.aircraft.service.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.aircraft.AircraftService;

/**
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Path("aircraft")
public class AircraftServiceREST {

	/** Aircraft service to use for REST calls. */
	@Inject
	private AircraftService aircraftService;

	/**
	 * @return List with all available aircrafts.
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("list-aircrafts")
	public List<Aircraft> listAicrafts() {
		return this.aircraftService.readAircrafts();
	}

}
