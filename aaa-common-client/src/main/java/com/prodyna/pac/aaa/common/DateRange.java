package com.prodyna.pac.aaa.common;

import java.io.Serializable;
import java.util.Date;

/**
 * Date range representation by start date and end date. Using the default constructor, current date is used for start
 * and end date.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class DateRange implements Serializable {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 3468585175692407739L;

	/** Start date of this date range. */
	private Date startDate;
	/** End date of this date range. */
	private Date endDate;

	/**
	 * Default constructor.
	 */
	public DateRange() {
		this(new Date(), new Date());
	}

	/**
	 * Convenient constructor setting the start and end date to given values.
	 * 
	 * @param startDate
	 *            Start date to use for this date range.
	 * @param endDate
	 *            End date to use for this date range.
	 */
	public DateRange(final Date startDate, final Date endDate) {
		this.startDate = new Date(startDate.getTime());
		this.endDate = new Date(endDate.getTime());
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return new Date(this.startDate.getTime());
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = new Date(startDate.getTime());
		;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return new Date(this.endDate.getTime());
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = new Date(endDate.getTime());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.endDate == null) ? 0 : this.endDate.hashCode());
		result = prime * result + ((this.startDate == null) ? 0 : this.startDate.hashCode());
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
		if (!(obj instanceof DateRange)) {
			return false;
		}
		final DateRange other = (DateRange) obj;
		if (this.endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!this.endDate.equals(other.endDate)) {
			return false;
		}
		if (this.startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!this.startDate.equals(other.startDate)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("DateRange [startDate=").append(this.startDate).append(", endDate=").append(this.endDate)
				.append("]");
		return builder.toString();
	}

}
