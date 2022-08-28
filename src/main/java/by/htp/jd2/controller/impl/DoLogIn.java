package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.News;
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

public class DoLogIn implements Command {
	private final static Logger LOG = LogManager.getLogger(DoLogIn.class);
	private final UserService service = ServiceProvider.getInstance().getUserService();
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	public static final String URL_GO_TO_NEWS_LIST = "controller?command=go_to_news_list";
	public static final String URL_GO_TO_BASE_PAGE = "controller?command=go_to_base_page";
	public static final String GO_TO_NEWS_LIST_AND_LOCAL_IS = "controller?command=go_to_news_list&local=";
	private static final int LAST_NEWS_NUMBER = 5;
	private static final String ERROR_LOGINATION_MESSAGE = "Wrong login or password";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<News> latestNews;

		try {
			HttpSession getSession = request.getSession(true);
			String login = request.getParameter(UsersParameter.LOGIN_PARAM);
			String password = request.getParameter(UsersParameter.PASSWORD_PARAM);
			String local = request.getParameter(AttributsKey.LOCAL);
			String role = service.logIn(login, password);
			latestNews = newsService.latestList(LAST_NEWS_NUMBER);
			getSession.setAttribute(AttributsKey.LOCAL, local);

			if (!role.equals(UsersRole.GUEST.getTitle())) {
				logIn(getSession, response, local, role);
			} else {
				setErrorNessage(getSession, request, response, local, latestNews);
			}
		} catch (ServiceException | ServiceNewsException e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}

	private void logIn(HttpSession getSession, HttpServletResponse response, String local, String role)
			throws IOException {
		getSession.setAttribute(AttributsKey.USER, ConnectionStatus.ACTIVE);
		getSession.setAttribute(AttributsKey.ROLE, role);
		getSession.setAttribute(AttributsKey.URL, URL_GO_TO_NEWS_LIST);
		response.sendRedirect(GO_TO_NEWS_LIST_AND_LOCAL_IS + local);
	}

	private void setErrorNessage(HttpSession getSession, HttpServletRequest request, HttpServletResponse response,
			String local, List<News> latestNews) throws IOException, ServletException {
		getSession.setAttribute(AttributsKey.URL, URL_GO_TO_BASE_PAGE);
		getSession.setAttribute(AttributsKey.USER, ConnectionStatus.NOT_ACTIVE);
		request.setAttribute(AttributsKey.NEWS, latestNews);
		getSession.setAttribute(AttributsKey.LOCAL, local);
		request.setAttribute(AttributsKey.ERRORS_LOGINATION_NAME, ERROR_LOGINATION_MESSAGE);
		request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
	}
}
