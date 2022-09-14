package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.bean.News;
import by.htp.jd2.controller.Command;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.util.AttributsKey;
import by.htp.jd2.util.ConnectionStatus;
import by.htp.jd2.util.JspPageName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToBasePage implements Command {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private final static Logger LOG = LogManager.getLogger(GoToBasePage.class);
	private final static String URL_GO_TO_BASE_PAGE = "controller?command=go_to_base_page&";
	private final static String LATEST_NEWS = "=latestNews";
	private static final int LAST_NEWS_NUMBER = 5;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<News> latestNews;

		try {
			HttpSession requestGetSession = request.getSession(true);
			latestNews = newsService.latestList(LAST_NEWS_NUMBER);
			request.setAttribute(AttributsKey.USER, ConnectionStatus.NOT_ACTIVE);
			request.setAttribute(AttributsKey.NEWS, latestNews);
			StringBuilder urlForRedirect = new StringBuilder();

			urlForRedirect = urlForRedirect.append(URL_GO_TO_BASE_PAGE).append(AttributsKey.USER).append("=")
					.append(ConnectionStatus.NOT_ACTIVE).append("&").append(AttributsKey.NEWS).append(LATEST_NEWS);

			requestGetSession.setAttribute(AttributsKey.URL, urlForRedirect.toString());

			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
		} catch (ServiceNewsException e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
