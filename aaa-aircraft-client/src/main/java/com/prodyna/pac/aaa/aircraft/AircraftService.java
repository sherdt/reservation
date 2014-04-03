package com.prodyna.pac.aaa.aircraft;

import java.util.List;

/**
 * CRUD service interface for the {@link Aircraft}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface AircraftService {

	/**
	 * Creates a new aircraft.
	 * 
	 * @param Aircraft
	 *            New aircraft to create.
	 * @return Created aircraft type.
	 */
	Aircraft createAircraft(Aircraft Aircraft);

	/**
	 * Retrieves a list of all aircrafts.
	 * 
	 * @return List of all available aircrafts.
	 */
	List<Aircraft> readAircrafts();

	/**
	 * Updates given aircraft.
	 * 
	 * @param Aircraft
	 *            Aircraft to update.
	 * @return Updated aircraft.
	 */
	Aircraft updateAircraft(Aircraft Aircraft);

	/**
	 * Deletes the aircraft with given tail sign (which is the ID).
	 * 
	 * @param tailSign
	 *            Tail sign of the aircraft to delete.
	 */
	void deleteAircraft(String tailSign);
}
