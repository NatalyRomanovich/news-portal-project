package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ChangeLanguage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession getSession = request.getSession(true);

		String local = request.getParameter(AttributsKey.LOCAL);
		String url = (String) getSession.getAttribute(AttributsKey.URL);
		getSession.setAttribute(AttributsKey.LOCAL, local);

		request.getSession(true).setAttribute(AttributsKey.LOCAL, local);
		response.sendRedirect(url + "&local=" + local);
	}
}
