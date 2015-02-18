package com.innovez.sample.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ini adalah contoh servlet untuk menampilkan response dalam format html
 * menggunakan jsp. Jsp merupakan teknologi yang berbeda dari servlet, maksudnya
 * dua teknologi ini dispesifikasikan dalam dua JSR yang berbeda. Akan tetapi,
 * dua teknologi ini kerap digunakan bersamaan yang saling melengkapi, servlet
 * untuk untuk memproses request sementara jsp untuk menampilkan response.
 * 
 * @author zakyalvan
 */
@SuppressWarnings("serial")
public class RenderViewUsingJspServlet extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger(RenderViewUsingJspServlet.class);
	
	private static final String REGISTRATION_FORM_PARAM_NAME = "registrationForm";
	private static final String CONFIRMATION_PAGE_PARAM_NAME = "confirmationPage";
	
	private String registrationForm;
	private String confirmationPage;
	
	@Override
	public void init() throws ServletException {
		LOGGER.debug("Inisiasi servlet registrasi, cek konfigurasi form dan halaman konfirmasi dari parameter inisiasi");
		
		ServletConfig config = getServletConfig();
		
		registrationForm = config.getInitParameter(REGISTRATION_FORM_PARAM_NAME);
		if(registrationForm == null || registrationForm.isEmpty()) {
			LOGGER.error("Inisiasi parameter untuk form registrasi belum diberikan");
			throw new ServletException(String.format("Paramter inisiasi servlet dengan nama '%s' harus diberikan", REGISTRATION_FORM_PARAM_NAME));
		}
		
		confirmationPage = config.getInitParameter(CONFIRMATION_PAGE_PARAM_NAME);
		if(confirmationPage == null || confirmationPage.isEmpty()) {
			LOGGER.error("Inisiasi parameter untuk halaman konfirmasi belum diberikan");
			throw new ServletException(String.format("Paramter inisiasi servlet dengan nama '%s' harus diberikan", CONFIRMATION_PAGE_PARAM_NAME));
		}
	}

	/**
	 * Tampilkan form.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.debug("Tampilkan formulir registrasi");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(registrationForm);
		dispatcher.forward(req, resp);
	}

	/**
	 * Proses form submission.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.debug("Proses form registrasi yang dikirim user");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(confirmationPage);
		dispatcher.forward(req, resp);
	}
}
