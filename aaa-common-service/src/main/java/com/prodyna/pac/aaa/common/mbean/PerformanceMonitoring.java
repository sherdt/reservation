package com.prodyna.pac.aaa.common.mbean;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

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
public class PerformanceMonitoring implements PerformanceMonitoringMXBean {

	/** Instance of an {@link MBeanServer}. */
	@Inject
	private MBeanServer platformMBeanServer;

	/** Object name to use to register this MBean to MBeanServer. */
	private ObjectName objectName = null;

	/** Map containing the last duration for each method ever has been added. */
	private final Map<String, Long> methodExecutionDurationMap = new HashMap<String, Long>();

	/**
	 * Registers this MBean to JMX.
	 */
	@PostConstruct
	public void registerInJMX() {

		try {
			this.objectName = new ObjectName("com.prodyna.pac.aaa.monitoring:type=" + this.getClass().getName());

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
		this.methodExecutionDurationMap.put(methodName, durationMs);
	}

	@Override
	public long getMethodExecutionDuration(final String methodName) {
		long result = -1;

		final Long duration = this.methodExecutionDurationMap.get(methodName);
		if (duration != null) {
			result = duration;
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
	public String printDiagnostics() {
		final StringBuilder diagnosticsStringBuilder = new StringBuilder();

		final Set<Entry<String, Long>> entrySet = this.methodExecutionDurationMap.entrySet();
		for (final Entry<String, Long> entry : entrySet) {
			diagnosticsStringBuilder.append(entry.getKey());
			diagnosticsStringBuilder.append(": ");
			diagnosticsStringBuilder.append(entry.getValue());
			diagnosticsStringBuilder.append("ms\n");
		}

		return diagnosticsStringBuilder.toString();
	}
}
