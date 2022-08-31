package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.News;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToBasePage implements Command {
	private final static Logger LOG = LogManager.getLogger(GoToBasePage.class);

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final int LAST_NEWS_NUMBER = 5;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<News> latestNews;

		try {
			HttpSession getSession = request.getSession(true);
			String local = request.getParameter(AttributsKey.LOCAL);
			latestNews = newsService.latestList(LAST_NEWS_NUMBER);
			request.getSession(true).setAttribute(AttributsKey.LOCAL, local);			
			request.setAttribute(AttributsKey.USER, ConnectionStatus.NOT_ACTIVE);
			request.setAttribute(AttributsKey.NEWS, latestNews);

			String urlForRedirect = "controller?command=go_to_base_page&" + AttributsKey.USER + "="
					+ ConnectionStatus.NOT_ACTIVE + "&" + AttributsKey.NEWS + "=latestNews";

			getSession.setAttribute(AttributsKey.URL, urlForRedirect);

			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
		} catch (ServiceNewsException e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
