package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.bean.News;
import by.htp.jd2.controller.Command;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.service.UserService;
import by.htp.jd2.util.AttributsKey;
import by.htp.jd2.util.ConnectionStatus;
import by.htp.jd2.util.JspPageName;
import by.htp.jd2.util.UsersParameter;
import by.htp.jd2.util.UsersRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoLogIn implements Command {
	private final UserService service = ServiceProvider.getInstance().getUserService();
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private final static Logger LOG = LogManager.getLogger(DoLogIn.class);	
	private final static String URL_GO_TO_BASE_PAGE = "controller?command=go_to_base_page";
	private final static String GO_TO_NEWS_LIST = "controller?command=go_to_news_list";
	private final static int LAST_NEWS_NUMBER = 5;
	private final static String ERROR = "error";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<News> latestNews;

		try {
			HttpSession requestGetSession = request.getSession(true);
			String login = request.getParameter(UsersParameter.LOGIN_PARAM);
			String password = request.getParameter(UsersParameter.PASSWORD_PARAM);
			String role = service.logIn(login, password);
			latestNews = newsService.latestList(LAST_NEWS_NUMBER);

			if (!role.equals(UsersRole.GUEST.getTitle())) {
				logIn(requestGetSession, response, role);
			} else {
				setErrorNessage(requestGetSession, request, response, latestNews);
			}
		} catch (ServiceException | ServiceNewsException e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}

	private void logIn(HttpSession requestGetSession, HttpServletResponse response, String role) throws IOException {
		requestGetSession.setAttribute(AttributsKey.USER, ConnectionStatus.ACTIVE);
		requestGetSession.setAttribute(AttributsKey.ROLE, role);
		requestGetSession.setAttribute(AttributsKey.URL, GO_TO_NEWS_LIST);
		response.sendRedirect(GO_TO_NEWS_LIST);
	}

	private void setErrorNessage(HttpSession requestGetSession, HttpServletRequest request,
			HttpServletResponse response, List<News> latestNews) throws IOException, ServletException {
		request.setAttribute(AttributsKey.URL, URL_GO_TO_BASE_PAGE);
		requestGetSession.setAttribute(AttributsKey.USER, ConnectionStatus.NOT_ACTIVE);
		request.setAttribute(AttributsKey.NEWS, latestNews);
		request.setAttribute(AttributsKey.ERRORS_LOGINATION_NAME, ERROR);
		request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
	}
}
