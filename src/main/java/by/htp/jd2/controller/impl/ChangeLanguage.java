package by.htp.jd2.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.controller.Command;
import by.htp.jd2.util.AttributsKey;
import by.htp.jd2.util.JspPageName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ChangeLanguage implements Command {

	private final static Logger LOG = LogManager.getLogger(ChangeLanguage.class);	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession requestGetSession = request.getSession(true);

		String local = request.getParameter(AttributsKey.LOCAL);
		String url = (String) requestGetSession.getAttribute(AttributsKey.URL);
		requestGetSession.setAttribute(AttributsKey.LOCAL, local);

		if (url == null || url.isEmpty()) {
			LOG.warn("Somthing is wrong");
			response.sendRedirect(JspPageName.BASE_PAGE_LAYOUT);
		}
		response.sendRedirect(url);
	}
}
