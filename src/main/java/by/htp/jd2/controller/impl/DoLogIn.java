package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.News;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoLogIn implements Command {

	private final UserService service = ServiceProvider.getInstance().getUserService();
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	
	private static final int lastNewsNumber = 5;	
	private static final String JSP_LOGIN_PARAM = "login";
	private static final String JSP_PASSWORD_PARAM = "password";
	private static final String ERROR_LOGINATION_MESSAGE = "wrong login or password";
	 

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<News> latestNews;
		String login = request.getParameter(JSP_LOGIN_PARAM);
		String password = request.getParameter(JSP_PASSWORD_PARAM);
		HttpSession getSession = request.getSession(true);		

		try {

			String role = service.logIn(login, password);
			latestNews = newsService.latestList(lastNewsNumber);

			if (!role.equals(UsersRole.GUEST.getTitle())) {
				getSession.setAttribute(AttributsKey.USER, ConnectionStatus.ACTIVE);
				getSession.setAttribute(AttributsKey.ROLE, role);				
				response.sendRedirect("controller?command=go_to_news_list");
				
			} else {
				getSession.setAttribute(AttributsKey.USER, ConnectionStatus.NOT_ACTIVE);				
				request.setAttribute(AttributsKey.NEWS, latestNews);
				request.setAttribute(AttributsKey.ERRORS_LOGINATION_NAME, ERROR_LOGINATION_MESSAGE);	
				request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
				//response.sendRedirect("controller?command=go_to_base_page");
			}			
		} catch (ServiceException e) {
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}