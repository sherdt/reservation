package com.prodyna.pac.aaa.aircraft;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity representing the type of an aircraft. The name is used as unique an identifier (e.g. <code>A380</code>,
 * <code>737</code>, ...)
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Entity
@Table(name = "aircraft_type")
public class AircraftType implements Serializable {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 5812602489312169141L;

	/** Name of the aircraft type. */
	@Id
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
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
		final AircraftType other = (AircraftType) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("AircraftType [name=").append(this.name).append("]");
		return builder.toString();
	}

}
