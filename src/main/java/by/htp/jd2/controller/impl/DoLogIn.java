package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.controller.AttributsKeys;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.controller.UsersParameters;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.service.UserService;
import by.htp.jd2.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoLogIn implements Command {

	private final UserService service = ServiceProvider.getInstance().getUserService();
	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";
	private static final String ERROR_LOGINATION_MESSAGE = "wrong login or password";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(JSP_LOGIN_PARAM);
		String password = request.getParameter(JSP_PASSWORD_PARAM);
		HttpSession getSession = request.getSession(true);
		
		// small validation

		try {

			String role = service.logIn(login, password);

			if (!role.equals(UsersRole.GUEST)) {
				getSession.setAttribute(AttributsKeys.USER, ConnectionStatus.ACTIVE);
				getSession.setAttribute(AttributsKeys.ROLE, role);
				getSession.setAttribute(AttributsKeys.REG_USER, ConnectionStatus.REGISTRED);
				response.sendRedirect("controller?command=go_to_news_list");
			} else {
				getSession.setAttribute(AttributsKeys.USER, ConnectionStatus.NOT_ACTIVE);
				request.setAttribute(AttributsKeys.ERRORS_LOGINATION_NAME, ERROR_LOGINATION_MESSAGE);
				request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
			}
			
		} catch (ServiceException e) {
			// logging e
			// go-to error page

		}

		// response.getWriter().print("do logination");

	}

}
