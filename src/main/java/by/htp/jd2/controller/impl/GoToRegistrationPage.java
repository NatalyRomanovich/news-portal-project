package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.controller.Command;
import by.htp.jd2.util.AttributsKey;
import by.htp.jd2.util.ConnectionStatus;
import by.htp.jd2.util.JspPageName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToRegistrationPage implements Command {
	private final static String URL_GO_TO_REGISTRATION_PAGE = "controller?command=go_to_registration_page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession requestGetSession = request.getSession(true);
		requestGetSession.setAttribute(AttributsKey.REG_USER, ConnectionStatus.UNREGISTRED);
		request.setAttribute(AttributsKey.URL, URL_GO_TO_REGISTRATION_PAGE);
		request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
	}
}
