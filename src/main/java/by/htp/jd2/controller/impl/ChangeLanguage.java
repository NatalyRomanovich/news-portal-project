package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.Enumeration;

import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangeLanguage implements Command {
	private static final String COMMAND = "command";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession(true).setAttribute(AttributsKey.LOCAL, request.getParameter(AttributsKey.LOCAL));
		request.getSession(true).setAttribute(AttributsKey.PAGE_URL, request.getRequestURL());
		String command = request.getParameter(COMMAND);
		StringBuffer url = request.getRequestURL();

		// String str = request.getParameterValues(AttributsKey.USER).toString();
		// System.out.println(request.getAttribute(AttributsKey.PAGE_URL));
		// System.out.println(commandsName);
		System.out.println(url + "?command=" + command);
	}
}
