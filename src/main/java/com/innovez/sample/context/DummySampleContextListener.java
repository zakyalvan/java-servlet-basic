package com.innovez.sample.context;

import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contoh implementasi dummy dari {@link ServletContextListener}, kita hanya
 * menampilkan debug log untuk seluruh event yang dihandle oleh listener ini.
 * 
 * @author zakyalvan
 */
public class DummySampleContextListener implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(DummySampleContextListener.class);
	
	/**
	 * 
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.debug("Servlet context event untuk context initialized diterima, tampilkan seluruh context init parameter");
		Enumeration<String> parameterNames = sce.getServletContext().getInitParameterNames();
		while(parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			String parameterValue = sce.getServletContext().getInitParameter(parameterName);
			LOGGER.debug("Parameter inisiasi servlet-context '{}' dengan nilai '{}'", parameterName, parameterValue);
		}
	}

	/**
	 * 
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.debug("Servlet context event untuk context destroyed diterima");
	}
}
