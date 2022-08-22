package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoLogOut implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession getSession = request.getSession(true);
		getSession.setAttribute(AttributsKey.LOCAL, request.getParameter(AttributsKey.LOCAL));
		getSession.setAttribute(UsersRole.USER.getTitle(), ConnectionStatus.NOT_ACTIVE);
		response.sendRedirect(JspPageName.INDEX_PAGE);
	}
}
