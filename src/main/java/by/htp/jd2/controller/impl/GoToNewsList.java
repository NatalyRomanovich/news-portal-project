package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;
import by.htp.jd2.bean.News;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToNewsList implements Command {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final String NEWS_LIST = "newsList";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<News> newsList;

		try {
			newsList = newsService.list();
			request.setAttribute(AttributsKey.NEWS, newsList);
			request.setAttribute(AttributsKey.PRESENTATION, NEWS_LIST);
			// request.setAttribute("news", null);
			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
			request.setAttribute(AttributsKey.REG_USER, null);
		} catch (ServiceNewsException e) {
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
