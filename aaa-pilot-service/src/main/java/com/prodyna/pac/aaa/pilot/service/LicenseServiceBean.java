/**
 * 
 */
package com.prodyna.pac.aaa.pilot.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.exception.EntityNotFoundException;
import com.prodyna.pac.aaa.pilot.License;
import com.prodyna.pac.aaa.pilot.LicenseService;
import com.prodyna.pac.aaa.pilot.PilotNamedQueries;

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
	public License readLicense(final String id) throws EntityNotFoundException {
		final License license = this.entityManager.find(License.class, id);
		if (license == null) {
			throw new EntityNotFoundException("License could not be found for given is [" + id + "]");
		}

		return license;
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
