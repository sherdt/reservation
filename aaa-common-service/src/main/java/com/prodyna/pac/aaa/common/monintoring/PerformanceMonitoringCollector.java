package com.prodyna.pac.aaa.common.monintoring;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.prodyna.pac.aaa.common.monitoring.PerformanceEntry;
import com.prodyna.pac.aaa.common.monitoring.PerformanceMonitoringMXBean;

/**
 * MXBean implementation for {@link PerformanceMonitoringMXBean}.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 * @see PerformanceMonitoringMXBean
 * 
 */
@Singleton
@Startup
public class PerformanceMonitoringCollector implements PerformanceMonitoringMXBean {

	/** Instance of an {@link MBeanServer}. */
	@Inject
	private MBeanServer platformMBeanServer;

	/** Object name to use to register this MBean to MBeanServer. */
	private ObjectName objectName = null;

	/** Map containing the collected performance values for each method. */
	private final Map<String, PerformanceEntry> methodExecutionDurationMap = new HashMap<String, PerformanceEntry>();

	/**
	 * Registers this MBean to JMX.
	 */
	@PostConstruct
	public void registerInJMX() {

		try {
			this.objectName = new ObjectName("com.prodyna.pac.aaa.monitoring:type=PerformanceMonitoring");

			this.platformMBeanServer.registerMBean(this, this.objectName);
		} catch (final JMException e) {
			throw new IllegalStateException("Problem during registration of performance monitoring into JMX", e);
		}
	}

	/**
	 * Unregisters this MBean from JMX.
	 */
	@PreDestroy
	public void unregisterFromJMX() {
		try {
			this.platformMBeanServer.unregisterMBean(this.objectName);
		} catch (final JMException e) {
			throw new IllegalStateException("Problem during unregistration of performance monitoring from JMX", e);
		}
	}

	@Override
	public void addMethodExecutionDuration(final String methodName, final long durationMs) {
		final String cleanedMethodName = methodName.replace("$Proxy$_$$_WeldSubclass", "");

		PerformanceEntry performanceEntry = this.methodExecutionDurationMap.get(cleanedMethodName);
		if (performanceEntry == null) {
			performanceEntry = new PerformanceEntry(cleanedMethodName, durationMs);
			this.methodExecutionDurationMap.put(cleanedMethodName, performanceEntry);
		} else {
			performanceEntry.add(durationMs);
		}
	}

	@Override
	public long getMethodExecutionDurationMinimum(final String methodName) {
		long result = 0;

		final PerformanceEntry performanceEntry = this.methodExecutionDurationMap.get(methodName);
		if (performanceEntry != null) {
			result = performanceEntry.getMinimumDuration();
		}

		return result;
	}

	@Override
	public long getMethodExecutionDurationAverage(final String methodName) {
		long result = 0;

		final PerformanceEntry performanceEntry = this.methodExecutionDurationMap.get(methodName);
		if (performanceEntry != null) {
			result = performanceEntry.getAverageDuration();
		}

		return result;
	}

	@Override
	public long getMethodExecutionDurationMaximum(final String methodName) {
		long result = 0;

		final PerformanceEntry performanceEntry = this.methodExecutionDurationMap.get(methodName);
		if (performanceEntry != null) {
			result = performanceEntry.getMaximumDuration();
		}

		return result;
	}

	@Override
	public void clearPerfomanceMonitoringData(final String methodName) {
		this.methodExecutionDurationMap.remove(methodName);
	}

	@Override
	public void clearAllPerfomanceMonitoringData() {
		this.methodExecutionDurationMap.clear();
	}

	@Override
	public long getMonitoredMethodsCount() {
		return this.methodExecutionDurationMap.size();
	}

	@Override
	public List<PerformanceEntry> getStatistics() {
		final List<PerformanceEntry> resultList = new LinkedList<PerformanceEntry>();
		resultList.addAll(this.methodExecutionDurationMap.values());
		return resultList;
	}

	@Override
	public PerformanceEntry getWorstByTotalDuration() {
		long worstTotal = 0;
		PerformanceEntry worstPerformanceEntry = null;
		for (final PerformanceEntry performanceEntry : this.methodExecutionDurationMap.values()) {
			if (performanceEntry.getTotalDuration() > worstTotal) {
				worstTotal = performanceEntry.getTotalDuration();
				worstPerformanceEntry = performanceEntry;
			}
		}

		return worstPerformanceEntry;
	}

	@Override
	public PerformanceEntry getWorstByAverage() {
		long worstAverage = 0;
		PerformanceEntry worstPerformanceEntry = null;
		for (final PerformanceEntry performanceEntry : this.methodExecutionDurationMap.values()) {
			if (performanceEntry.getAverageDuration() > worstAverage) {
				worstAverage = performanceEntry.getAverageDuration();
				worstPerformanceEntry = performanceEntry;
			}
		}

		return worstPerformanceEntry;
	}

	@Override
	public PerformanceEntry getWorstByCount() {
		long worstInvocationCount = 0;
		PerformanceEntry worstPerformanceEntry = null;
		for (final PerformanceEntry performanceEntry : this.methodExecutionDurationMap.values()) {
			if (performanceEntry.getInvocationCount() > worstInvocationCount) {
				worstInvocationCount = performanceEntry.getInvocationCount();
				worstPerformanceEntry = performanceEntry;
			}
		}

		return worstPerformanceEntry;
	}

	@Override
	public PerformanceEntry performanceForMethod(final String methodName) {
		return this.methodExecutionDurationMap.get(methodName);
	}

}
