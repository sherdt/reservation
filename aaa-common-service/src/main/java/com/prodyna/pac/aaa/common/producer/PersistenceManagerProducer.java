package com.prodyna.pac.aaa.common.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.prodyna.pac.aaa.common.Constants;

/**
 * Producer for {@link EntityManager} for persistence unit <code>aaaPU</code>.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@ApplicationScoped
public class PersistenceManagerProducer {

	/**
	 * Entity manger for DB access.
	 */
	@PersistenceContext(unitName = Constants.PERSISTENSE_UNIT_NAME)
	private EntityManager entityManager;

	/**
	 * Produces the entity manager for AAA app.
	 * 
	 * @return Persistence manager for aaaPU
	 */
	@Produces
	public EntityManager produceEntityManager() {
		return this.entityManager;
	}

}
