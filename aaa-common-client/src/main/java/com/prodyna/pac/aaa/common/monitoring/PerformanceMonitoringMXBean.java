package com.prodyna.pac.aaa.common.monitoring;

import java.util.List;

/**
 * MXBean to collect performance information on each method using the PerformanceMonitoringInterceptor.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface PerformanceMonitoringMXBean {

	/**
	 * @return Retrieves the total amount of monitored methods.
	 */
	long getMonitoredMethodsCount();

	/**
	 * @return All collected performance entries.
	 */
	List<PerformanceEntry> getStatistics();

	/**
	 * @return The performance entry for the method with highest total execution duration.
	 */
	PerformanceEntry getWorstByTotalDuration();

	/**
	 * @return The performance entry for the method with highest average execution duration.
	 */
	PerformanceEntry getWorstByAverage();

	/**
	 * @return The performance entry for the method with highest execution count.
	 */
	PerformanceEntry getWorstByCount();

	/**
	 * @param methodName
	 *            The name of the method to get the performance entry for.
	 * 
	 * @return The performance entry for the given method.
	 */
	PerformanceEntry performanceForMethod(String methodName);

	/**
	 * Retrieves the minimal execution duration for given method.
	 * 
	 * @param methodName
	 *            Method to retrieve the minimal execution duration for.
	 * @return Minimal execution duration for given method.
	 */
	long getMethodExecutionDurationMinimum(String methodName);

	/**
	 * Retrieves the average execution duration for given method.
	 * 
	 * @param methodName
	 *            Method to retrieve the average execution duration for.
	 * @return Average execution duration for given method.
	 */
	long getMethodExecutionDurationAverage(String methodName);

	/**
	 * Retrieves the maximal execution duration for given method.
	 * 
	 * @param methodName
	 *            Method to retrieve the maximal execution duration for.
	 * @return Maximal execution duration for given method.
	 */
	long getMethodExecutionDurationMaximum(String methodName);

	/**
	 * Clears the tracked performance monitoring data for given method.
	 * 
	 * @param methodName
	 *            Method to clear the monitoring data for.
	 */
	void clearPerfomanceMonitoringData(String methodName);

	/**
	 * Clears all tracked performance monitoring data.
	 */
	void clearAllPerfomanceMonitoringData();

	/**
	 * Adds the execution duration for given method to performance monitoring data, which can be retrieved using three
	 * different methods:
	 * 
	 * <pre>
	 * Maximum: {@link PerformanceMonitoringMXBean#getMethodExecutionDurationMinimum(String)}
	 * Average: {@link PerformanceMonitoringMXBean#getMethodExecutionDurationAverage(String)}
	 * Minimum: {@link PerformanceMonitoringMXBean#getMethodExecutionDurationMaximum(String)}
	 * </pre>
	 * 
	 * @param methodName
	 *            Method to save the execution duration for.
	 * @param duration
	 *            Execution duration for given method in milliseconds.
	 */
	void addMethodExecutionDuration(String methodName, long duration);

}
