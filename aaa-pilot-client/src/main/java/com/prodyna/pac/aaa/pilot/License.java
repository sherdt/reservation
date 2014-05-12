package com.prodyna.pac.aaa.pilot;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.aaa.aircraft.AircraftType;

/**
 * Entity representing a license. A license has an expiration date and is valid for only one {@link AircraftType}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Entity
@XmlRootElement
@Table(name = "aaa_license")
@NamedQueries({ @NamedQuery(name = PilotNamedQueries.SELECT_ALL_LICENSES, query = "SELECT l FROM License l") })
public class License implements Serializable {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -7525446826633069369L;

	/** The unique id for the license. It's recommended to use a (client) generated UUID. */
	@Id
	private String id;

	/** Type of the aircraft. */
	@JoinColumn(name = "aaa_aircraft_type_name", nullable = false, referencedColumnName = "name")
	@ManyToOne
	private AircraftType aircraftType;

	/** expiration date for the license. */
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;

	/**
	 * Default constructor.
	 */
	public License() {
	}

	/**
	 * Convenient constructor setting the given id.
	 * 
	 * @param id
	 *            Id to use for new license.
	 */
	public License(final String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the aircraftType
	 */
	public AircraftType getAircraftType() {
		return this.aircraftType;
	}

	/**
	 * @param aircraftType
	 *            the aircraftType to set
	 */
	public void setAircraftType(final AircraftType aircraftType) {
		this.aircraftType = aircraftType;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	/**
	 * @param expirationDate
	 *            the expirationDate to set
	 */
	public void setExpirationDate(final Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof License)) {
			return false;
		}
		final License other = (License) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("License [aircraftType=").append(this.aircraftType).append(", expirationDate=")
				.append(this.expirationDate).append("]");
		return builder.toString();
	}

}
