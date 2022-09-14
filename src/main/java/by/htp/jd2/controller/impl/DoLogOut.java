package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.controller.Command;
import by.htp.jd2.util.AttributsKey;
import by.htp.jd2.util.ConnectionStatus;
import by.htp.jd2.util.JspPageName;
import by.htp.jd2.util.UsersRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoLogOut implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession requestGetSession = request.getSession(true);
		requestGetSession.setAttribute(AttributsKey.USER, ConnectionStatus.NOT_ACTIVE);
		requestGetSession.removeAttribute(AttributsKey.ROLE);
		response.sendRedirect(JspPageName.INDEX_PAGE);
	}
}
