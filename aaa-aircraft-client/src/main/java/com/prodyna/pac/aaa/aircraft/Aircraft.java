package com.prodyna.pac.aaa.aircraft;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity representing an aircraft with its tail sign and the {@link AircraftType}. The tail sign has to be unique.<br>
 * Two aircrafts are assumed to be identical, if their tail sign is identical.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Entity
@Table(name = "aircraft")
public class Aircraft implements Serializable {

	/** Generated serial version UID. */
	private static final long serialVersionUID = -5404182914981856065L;

	/** Type of the aircraft. */
	private AircraftType aircraftType;

	/** Unique aircraft tail sign, also used as id. */
	@Id
	private String tailSign;

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

	/**
	 * @param tailSign
	 *            the tailSign to set
	 */
	public void setTailSign(final String tailSign) {
		this.tailSign = tailSign;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.tailSign == null) ? 0 : this.tailSign.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
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
