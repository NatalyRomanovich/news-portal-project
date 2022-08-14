package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToBasePage implements Command {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final int LAST_NEWS_NUMBER = 5;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<News> latestNews;
		try {
			latestNews = newsService.latestList(LAST_NEWS_NUMBER);
			request.setAttribute(AttributsKey.USER, ConnectionStatus.NOT_ACTIVE);
			request.setAttribute(AttributsKey.NEWS, latestNews);
			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
		} catch (ServiceNewsException e) {
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
