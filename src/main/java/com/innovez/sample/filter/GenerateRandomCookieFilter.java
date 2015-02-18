package com.innovez.sample.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author zakyalvan
 */
public class GenerateRandomCookieFilter implements Filter {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateRandomCookieFilter.class);
	
	private static final String ENABLING_KEY = "enableFilter";
	private static final String DEFAULT_RANDOM_COOKIE_NAME = "RANDOM";
	
	private boolean enable = true;
	private String cookieName = DEFAULT_RANDOM_COOKIE_NAME;
	private String cookiePath = "/";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.debug("Inisiasi filter, cek apakah filter di-enable atau tidak");
		
		String enabaleFilterString = filterConfig.getInitParameter(ENABLING_KEY);
		if(enabaleFilterString != null && enabaleFilterString.equalsIgnoreCase(Boolean.FALSE.toString())) {
			LOGGER.debug("Filter ini di disable.");
			enable = false;
		}
		
		cookiePath = filterConfig.getServletContext().getContextPath();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(enable) {
			LOGGER.debug("Random cookie filter di enable, pertama check cookies dari request");
			Cookie randomCookie = null;
			
			// We get null value when no cookies sent from browser, for example in first time browsing.
			Cookie[] cookies = ((HttpServletRequest) request).getCookies();
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie.getName().equals(cookieName)) {
						randomCookie = cookie;
						LOGGER.debug("Yes, random cookies ditemukan dari request");
						break;
					}
				}
			}
			
			if(randomCookie == null) {
				LOGGER.debug("Random cookie tidak di temukan dalam request, buat yang baru");
				randomCookie = new Cookie(cookieName, UUID.randomUUID().toString());
				randomCookie.setPath(cookiePath);
				randomCookie.setComment("Just a random generated cookie");
				randomCookie.setMaxAge(10);
				((HttpServletResponse) response).addCookie(randomCookie);
			}
		}
		// Don't forget to call this, except you wont proceed the request processing.
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}
}
