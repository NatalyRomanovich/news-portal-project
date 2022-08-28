package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

public class GoToNewsList implements Command {

	private final static Logger LOG = LogManager.getLogger(GoToNewsList.class);
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	public static final String URL_GO_TO_NEWS_LIST = "controller?command=go_to_news_list";
	private static final String NEWS_LIST = "newsList";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<News> newsList;
		try {
			HttpSession getSession = request.getSession(true);
			String local = request.getParameter(AttributsKey.LOCAL);

			getSession.setAttribute(AttributsKey.LOCAL, local);
			getSession.setAttribute(AttributsKey.URL, URL_GO_TO_NEWS_LIST);
			getSession.removeAttribute(AttributsKey.REG_USER);
			getSession.removeAttribute(AttributsKey.REG_USER);
			newsList = newsService.list();
			request.setAttribute(AttributsKey.NEWS, newsList);
			request.setAttribute(AttributsKey.PRESENTATION, NEWS_LIST);
			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
		} catch (ServiceNewsException e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
