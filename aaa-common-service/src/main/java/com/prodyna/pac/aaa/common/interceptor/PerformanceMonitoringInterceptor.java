package com.prodyna.pac.aaa.common.interceptor;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.prodyna.pac.aaa.common.annotation.Monitored;
import com.prodyna.pac.aaa.common.mbean.PerformanceMonitoringMXBean;

/**
 * Performance monitoring interceptor.
 * 
 * @author Sergej Herdt, PRODYNA AG
 * 
 */
@Interceptor
@Monitored
@Priority(Interceptor.Priority.APPLICATION)
public class PerformanceMonitoringInterceptor {

	/** Performance monitoring MBean to save collected execution data to. */
	@Inject
	private PerformanceMonitoringMXBean performanceMonitoringMXBean;

	/**
	 * This method is called for each method annotated to use the performance monitoring interceptor. The execution time
	 * of the method in given invocation context is tracked and saved to the performance monitoring MBean.
	 * 
	 * @param ctx
	 *            {@link InvocationContext}.
	 * @return The result of the invocation of the ctx.proceed().
	 * @throws Exception
	 *             If an exception is thrown by subsequent processing.
	 */
	@AroundInvoke
	public Object monitorPerformance(final InvocationContext ctx) throws Exception {
		final long startTimeMillis = System.currentTimeMillis();
		Object result = null;
		result = ctx.proceed();
		final long endTimeMillis = System.currentTimeMillis();

		this.performanceMonitoringMXBean.addMethodExecutionDuration(ctx.getMethod().getName(), endTimeMillis
				- startTimeMillis);

		return result;
	}
}
