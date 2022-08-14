package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.controller.UsersParameter;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToAddNewsPage implements Command {

	private final UserService service = ServiceProvider.getInstance().getUserService();
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	public static final String ADD_NEWS = "addNews";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//String login = request.getParameter(UsersParameter.JSP_LOGIN_PARAM);
		//String password = request.getParameter(UsersParameter.JSP_PASSWORD_PARAM);
		
		HttpSession getSession = request.getSession(true);		
		
		System.out.println("go to add news page");
		getSession.setAttribute(AttributsKey.USER, ConnectionStatus.ACTIVE);
		//getSession.setAttribute(AttributsKey.ROLE, UsersRole.ADMIN.getTitle());
		//getSession.removeAttribute(AttributsKey.REG_USER);
		getSession.setAttribute(AttributsKey.NEWS_COMMANDS_NAME, ADD_NEWS);
		request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
	
	}
}
