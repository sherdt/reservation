package com.prodyna.pac.aaa.common.monitoring;

/**
 * 
 * Entry containing performance information for a specific method.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public class PerformanceEntry {

	/** The name of the method this performance information is for. */
	private final String methodName;

	/** Amount of invocations for the method. */
	private long invocationCount = 0;

	/** Time spent to execute the method in total since last reset/restart. */
	private long totalDuration = 0;

	/**
	 * Minimal execution duration for the method. Initialize with max value, it will be lowered to first duration in
	 * constructor.
	 */
	private long minimumDuration = Long.MAX_VALUE;

	/** Maximal execution duration for the method. */
	private long maximumDuration = 0;

	/**
	 * Constructor for {@link PerformanceEntry}.
	 * 
	 * @param methodName
	 *            Name of the method this performance entry is for.
	 * @param duration
	 *            First duration of the method invocation.
	 * 
	 */
	public PerformanceEntry(final String methodName, final long duration) {
		this.methodName = methodName;
		this.add(duration);
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return this.methodName;
	}

	/**
	 * @return the invocationCount
	 */
	public long getInvocationCount() {
		return this.invocationCount;
	}

	/**
	 * @return the totalDuration
	 */
	public long getTotalDuration() {
		return this.totalDuration;
	}

	/**
	 * @return the minimumDuration
	 */
	public long getMinimumDuration() {
		return this.minimumDuration;
	}

	/**
	 * @return the maximumDuration
	 */
	public long getMaximumDuration() {
		return this.maximumDuration;
	}

	/**
	 * @return the averageDuration
	 */
	public long getAverageDuration() {
		// invocationCount is never 0 since the counter is incremented during instance creation.
		return (long) ((double) this.totalDuration / (float) this.invocationCount);
	}

	/**
	 * Adds new execution duration.
	 * 
	 * @param duration
	 *            New duration for this method.
	 */
	public final void add(final long duration) {

		this.minimumDuration = Math.min(duration, this.minimumDuration);

		this.maximumDuration = Math.max(duration, this.maximumDuration);

		this.totalDuration += duration;

		this.invocationCount++;
	}
}
