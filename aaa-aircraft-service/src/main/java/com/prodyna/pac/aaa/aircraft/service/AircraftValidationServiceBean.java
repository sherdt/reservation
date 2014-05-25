package com.prodyna.pac.aaa.aircraft.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.aircraft.AircraftType;
import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.common.exception.ResourceInvalidException;

/**
 * Session Bean implementation class AircraftValidationServiceBean for {@link AircraftValidationService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Stateless
@Monitored
public class AircraftValidationServiceBean implements AircraftValidationService {

	/** Aircraft service to retrieve the data from. */
	@Inject
	private AircraftService aircraftService;

	@Override
	public void validate(final Aircraft aircraft) throws ResourceInvalidException {
		if (aircraft == null) {
			throw new ResourceInvalidException("Given aircraft is invalid.");
		}

		Aircraft readAircraft;
		try {
			readAircraft = this.aircraftService.readAircraft(aircraft.getTailSign());
		} catch (final EntityNotFoundException e) {
			throw new ResourceInvalidException(
					"Given aircraft is invalid. The aircraft could not be found by its tail sign.", e);
		}

		if (aircraft.getAircraftType() == null) {
			throw new ResourceInvalidException("Given aircraft is invalid. The aircraft type is not specified.");
		}

		final AircraftType aircraftType = readAircraft.getAircraftType();
		if (!aircraftType.equals(aircraft.getAircraftType())) {
			throw new ResourceInvalidException("Given aircraft is invalid. The aircraft type is does not match.");
		}
	}

}