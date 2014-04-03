package com.prodyna.pac.aaa.aircraft.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.prodyna.pac.aaa.aircraft.Aircraft;
import com.prodyna.pac.aaa.aircraft.AircraftNamedQueries;
import com.prodyna.pac.aaa.aircraft.AircraftService;
import com.prodyna.pac.aaa.common.Constants;
import com.prodyna.pac.aaa.exceptions.AircraftDeleteException;

/**
 * Session Bean implementation class AircraftServiceBean for {@link AircraftService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Stateless
public class AircraftServiceBean implements AircraftService {

	/**
	 * Entity manger for DB access.
	 */
	@PersistenceContext(unitName = Constants.PERSISTENSE_UNIT_NAME)
	private EntityManager entityManager;

	@Override
	public Aircraft createAircraft(final Aircraft aircraft) {
		this.entityManager.persist(aircraft);
		return aircraft;
	}

	@Override
	public List<Aircraft> readAircrafts() {
		return this.entityManager.createNamedQuery(AircraftNamedQueries.SELECT_ALL_AIRCRAFTS, Aircraft.class)
				.getResultList();
	}

	@Override
	public Aircraft updateAircraft(final Aircraft aircraft) {
		return this.entityManager.merge(aircraft);
	}

	@Override
	public void deleteAircraft(final String name) throws AircraftDeleteException {
		final Aircraft aircraft = this.entityManager.find(Aircraft.class, name);
		this.entityManager.remove(aircraft);
	}

}
