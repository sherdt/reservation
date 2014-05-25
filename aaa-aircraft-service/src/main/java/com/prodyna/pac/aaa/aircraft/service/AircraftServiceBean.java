package com.prodyna.pac.aaa.aircraft.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.aircraft.AircraftNamedQueries;
import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;

/**
 * Session Bean implementation class AircraftServiceBean for {@link AircraftService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Stateless
@Monitored
public class AircraftServiceBean implements AircraftService {

	/** Entity manger for DB access. */
	@Inject
	private EntityManager entityManager;

	/** Logger for this class. */
	@Inject
	private Logger logger;

	@Override
	public Aircraft createAircraft(final Aircraft aircraft) {
		this.logger.trace("Trying to create an aircraft.");
		this.entityManager.persist(aircraft);
		return aircraft;
	}

	@Override
	public Aircraft readAircraft(final String tailSign) throws EntityNotFoundException {
		final Aircraft aircraft = this.entityManager.find(Aircraft.class, tailSign);
		if (aircraft == null) {
			throw new EntityNotFoundException("Aircraft could not be found for given tailSign [" + tailSign + "]");
		}

		return aircraft;
	}

	@Override
	public List<Aircraft> readAircrafts() {
		this.logger.trace("Retrieving all aircrafts from DB.");
		return this.entityManager.createNamedQuery(AircraftNamedQueries.SELECT_ALL_AIRCRAFTS, Aircraft.class)
				.getResultList();
	}

	@Override
	public Aircraft updateAircraft(final Aircraft aircraft) {
		return this.entityManager.merge(aircraft);
	}

	@Override
	public void deleteAircraft(final String name) {
		final Aircraft aircraft = this.entityManager.find(Aircraft.class, name);
		this.entityManager.remove(aircraft);
	}

}