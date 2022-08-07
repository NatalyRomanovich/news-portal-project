package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.controller.UsersParameter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToRegistrationPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession getSession = request.getSession(true);
		
		    getSession.setAttribute(AttributsKey.REG_USER, ConnectionStatus.UNREGISTRED);
			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
		
	}

}
