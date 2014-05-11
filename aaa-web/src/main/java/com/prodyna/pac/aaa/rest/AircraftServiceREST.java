package com.prodyna.pac.aaa.rest;

import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
	@Produces("application/json")
	@GET
	@Path("list-aircrafts")
	public JsonArray listCharter() {
		final List<Aircraft> allAircrafts = this.aircraftService.readAircrafts();

		final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		if (allAircrafts != null) {
			for (final Aircraft aircraft : allAircrafts) {
				final JsonObject jsonCharterObject = Json.createObjectBuilder().add("tailSign", aircraft.getTailSign())
						.add("aircraftType", aircraft.getAircraftType().getName()).build();
				jsonArrayBuilder.add(jsonCharterObject);
			}
		}

		return jsonArrayBuilder.build();
	}

}
