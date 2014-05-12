package com.prodyna.pac.aaa.aircraft;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing an aircraft with its tail sign and the {@link AircraftType}. The tail sign has to be unique.<br>
 * Two aircrafts are assumed to be identical, if their tail sign is identical.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Entity
@XmlRootElement
@Table(name = "aaa_aircraft")
@NamedQueries({ @NamedQuery(name = AircraftNamedQueries.SELECT_ALL_AIRCRAFTS, query = "SELECT a FROM Aircraft a") })
public class Aircraft implements Serializable {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 6568192466570616625L;

	/** Type of the aircraft. */
	@JoinColumn(name = "aaa_aircraft_type_name", nullable = false, referencedColumnName = "name")
	@ManyToOne
	private AircraftType aircraftType;

	/** Unique aircraft tail sign, also used as id. */
	@Id
	private String tailSign;

	/**
	 * Default constructor.
	 */
	public Aircraft() {
	}

	/**
	 * Constructor setting the tail sign.
	 * 
	 * @param tailSign
	 *            Tail sing to use for this aircraft.
	 */
	public Aircraft(final String tailSign) {
		this.tailSign = tailSign;
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
	 * @return the tailSign
	 */
	public String getTailSign() {
		return this.tailSign;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.tailSign == null) ? 0 : this.tailSign.hashCode());
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
		if (!(obj instanceof Aircraft)) {
			return false;
		}
		final Aircraft other = (Aircraft) obj;
		if (this.tailSign == null) {
			if (other.tailSign != null) {
				return false;
			}
		} else if (!this.tailSign.equals(other.tailSign)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Aircraft [tailSign=").append(this.tailSign).append(", aircraftType=").append(this.aircraftType)
				.append("]");
		return builder.toString();
	}

}
