/**
 * 
 */
package com.prodyna.pac.aaa.pilot.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.aaa.auth.AuthenticationSecured;
import com.prodyna.pac.aaa.auth.Role;
import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.pilot.Pilot;
import com.prodyna.pac.aaa.pilot.PilotNamedQueries;
import com.prodyna.pac.aaa.pilot.PilotService;

/**
 * 
 * Session Bean implementation class PilotServiceBean for {@link PilotService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Stateless
@Monitored
public class PilotServiceBean implements PilotService {

	/** Entity manger for DB access. */
	@Inject
	private EntityManager entityManager;

	@Override
	@AuthenticationSecured(role = Role.ADMIN)
	public Pilot createPilot(final Pilot Pilot) {
		this.entityManager.persist(Pilot);
		return Pilot;
	}

	@Override
	public Pilot readPilot(final String username) throws EntityNotFoundException {
		final Pilot pilot = this.entityManager.find(Pilot.class, username);
		if (pilot == null) {
			throw new EntityNotFoundException("Pilot could not be found for given username [" + username + "]");
		}

		return pilot;
	}

	@Override
	public List<Pilot> readPilots() {
		return this.entityManager.createNamedQuery(PilotNamedQueries.SELECT_ALL_PILOTS, Pilot.class).getResultList();
	}

	@Override
	@AuthenticationSecured(role = Role.ADMIN)
	public Pilot updatePilot(final Pilot Pilot) {
		return this.entityManager.merge(Pilot);
	}

	@Override
	@AuthenticationSecured(role = Role.ADMIN)
	public void deletePilot(final String username) {
		final Pilot pilot = this.entityManager.find(Pilot.class, username);
		this.entityManager.remove(pilot);
	}

}