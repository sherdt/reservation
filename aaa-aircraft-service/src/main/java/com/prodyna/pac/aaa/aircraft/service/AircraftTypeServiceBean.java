package com.prodyna.pac.aaa.aircraft.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.prodyna.pac.aaa.aircraft.AircraftNamedQueries;
import com.prodyna.pac.aaa.aircraft.AircraftType;
import com.prodyna.pac.aaa.aircraft.AircraftTypeService;
import com.prodyna.pac.aaa.common.Constants;

/**
 * Session Bean implementation class AircraftTypeServiceBean for {@link AircraftTypeService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Stateless
public class AircraftTypeServiceBean implements AircraftTypeService {

	/**
	 * Entity manger for DB access.
	 */
	@PersistenceContext(unitName = Constants.PERSISTENSE_UNIT_NAME)
	private EntityManager entityManager;

	@Override
	public AircraftType createAircraftType(final AircraftType aircraftType) {
		this.entityManager.persist(aircraftType);
		return aircraftType;
	}

	@Override
	public List<AircraftType> readAircraftTypes() {
		return this.entityManager.createNamedQuery(AircraftNamedQueries.SELECT_ALL_AIRCRAFT_TYPES, AircraftType.class)
				.getResultList();
	}

	@Override
	public AircraftType updateAircraftType(final AircraftType aircraftType) {
		return this.entityManager.merge(aircraftType);
	}

	@Override
	public void deleteAircraftType(final String name) {
		final AircraftType aircraftType = this.entityManager.find(AircraftType.class, name);
		this.entityManager.remove(aircraftType);
	}

}
