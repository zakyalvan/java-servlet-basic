package com.innovez.sample.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contoh servlet sederhana.
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public class DummySampleServlet extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger(DummySampleServlet.class);

	@Override
	public void init() throws ServletException {
		LOGGER.debug("Inisiasi servlet, tampilkan seluruh parameter inisiasi servlet");
		Enumeration<String> initParamNames = getServletConfig().getInitParameterNames();
		while(initParamNames.hasMoreElements()) {
			String initParamName = initParamNames.nextElement();
			String initParamValue = getServletConfig().getInitParameter(initParamName);
			LOGGER.debug("Parameter inisiasi servlet '{}' bernilai '{}'", initParamName, initParamValue);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.debug("Handle get request for URI {}", req.getRequestURI());
	}
	
	@Override
	public void destroy() {
		LOGGER.debug("Destroy servlet");
	}
}
