/**
 * 
 */
package com.prodyna.pac.aaa.pilot;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.prodyna.pac.aaa.common.annotation.Monitored;

/**
 * 
 * Session Bean implementation class LicenseServiceBean for {@link LicenseService}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Stateless
@Monitored
public class LicenseServiceBean implements LicenseService {

	/** Entity manger for DB access. */
	@Inject
	private EntityManager entityManager;

	@Override
	public License createLicense(final License license) {
		this.entityManager.persist(license);
		return license;
	}

	@Override
	public License readLicense(final String id) {
		final TypedQuery<License> namedQuery = this.entityManager.createNamedQuery(
				PilotNamedQueries.SELECT_LICENSE_BY_ID, License.class);
		namedQuery.setParameter("id", id);

		return namedQuery.getSingleResult();
	}

	@Override
	public List<License> readLicenses() {
		return this.entityManager.createNamedQuery(PilotNamedQueries.SELECT_ALL_LICENSES, License.class)
				.getResultList();
	}

	@Override
	public License updateLicense(final License license) {
		return this.entityManager.merge(license);
	}

	@Override
	public void deleteLicense(final String id) {
		final License license = this.entityManager.find(License.class, id);
		this.entityManager.remove(license);
	}

}
