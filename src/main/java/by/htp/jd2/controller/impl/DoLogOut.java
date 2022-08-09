package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DoLogOut implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			request.getSession(true).setAttribute(UsersRole.USER.getTitle(), ConnectionStatus.NOT_ACTIVE);
			response.sendRedirect(JspPageName.INDEX_PAGE);
	}
}
