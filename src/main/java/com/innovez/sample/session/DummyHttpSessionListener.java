package com.innovez.sample.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyHttpSessionListener implements HttpSessionListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(DummyHttpSessionListener.class);
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
	}
}
