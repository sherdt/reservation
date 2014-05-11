package com.prodyna.pac.aaa.common.mbean;

/**
 * MXBean to collect performance information on each method using the PerformanceMonitoringInterceptor.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
public interface PerformanceMonitoringMXBean {

	/**
	 * Adds the execution duration for given method to performance monitoring data, which can be retrieved using
	 * {@link PerformanceMonitoringMXBean#getMethodExecutionDuration(String)}.
	 * 
	 * @param methodName
	 *            Method to save the execution duration for.
	 * @param durationMs
	 *            Execution duration for given method in milliseconds.
	 */
	void addMethodExecutionDuration(String methodName, long durationMs);

	/**
	 * Retrieves the execution duration for given method. -1 is returned if no data for given method has been tracked
	 * yet.
	 * 
	 * @param methodName
	 *            Method to retrieve execution duration for.
	 * @return Execution duration for given method.
	 */
	long getMethodExecutionDuration(String methodName);

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
	 * Formats the collected performance data for each method and returns it as text.
	 * 
	 * @return Formated performance data
	 */
	String printDiagnostics();

}
