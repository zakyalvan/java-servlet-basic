package com.innovez.sample.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contoh filter sederhana, setiap method tidak melakukan apapun kecuali
 * tampilin log bahwa mereka dieksekusi. Perlu diingat, dua method lifecycle
 * {@link Filter#init(FilterConfig)} dan {@link Filter#destroy()} hanya akan
 * dipanggil sekali oleh servlet container.
 * 
 * @author zakyalvan
 */
public class DummySampleFilter implements Filter {
	public static final Logger LOGGER = LoggerFactory.getLogger(DummySampleFilter.class);
	
	/**
	 * Method untuk menginisiasi siklus hidup filter, method ini akan di panggil oleh servlet
	 * container pada saat servlet context di inisiasi.
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.debug("Inisiasi filter");
	}

	/**
	 * Di sinilah filter terhadap request yang diterima oleh container
	 * dilakukan. Perlu diingat, setiap method doFilter akan didispatch
	 * tergantung dari pemetaan dari filter bersangkutan, baik berdasarkan
	 * urlPattern atau servletName di {@code web.xml}
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOGGER.debug("Do filter request");
		chain.doFilter(request, response);
	}

	/**
	 * Method untuk menakhiri hidup filter, method ini akan di panggil oleh
	 * servlet container pada saat servlet context di-destroy.
	 */
	@Override
	public void destroy() {
		LOGGER.debug("Mengakhiri filter");
	}
}
