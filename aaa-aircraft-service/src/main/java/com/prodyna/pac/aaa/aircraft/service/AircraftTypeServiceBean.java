package com.prodyna.pac.aaa.aircraft.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.prodyna.pac.aaa.aircraft.AircraftNamedQueries;
import com.prodyna.pac.aaa.aircraft.AircraftType;
import com.prodyna.pac.aaa.aircraft.AircraftTypeService;
import com.prodyna.pac.aaa.common.annotation.Monitored;

/**
 * Session Bean implementation class AircraftTypeServiceBean for {@link AircraftTypeService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Stateless
@Monitored
public class AircraftTypeServiceBean implements AircraftTypeService {

	/** Entity manger for DB access. */
	@Inject
	private EntityManager entityManager;

	@Override
	public AircraftType createAircraftType(final AircraftType aircraftType) {
		this.entityManager.persist(aircraftType);
		return aircraftType;
	}

	@Override
	public AircraftType readAircraftType(final String name) {
		final TypedQuery<AircraftType> namedQuery = this.entityManager.createNamedQuery(
				AircraftNamedQueries.SELECT_AIRCRAFT_TYPE_BY_NAME, AircraftType.class);
		namedQuery.setParameter("aircraftTypeName", name);

		return namedQuery.getSingleResult();
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
