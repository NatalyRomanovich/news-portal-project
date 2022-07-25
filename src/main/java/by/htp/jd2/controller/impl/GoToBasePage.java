package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.News;
import by.htp.jd2.controller.AttributsKeys;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToBasePage implements Command{
	
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<News> latestNews;
		try {
			latestNews = newsService.latestList(5);
			request.setAttribute(AttributsKeys.USER, ConnectionStatus.NOT_ACTIVE);
			request.setAttribute(AttributsKeys.NEWS, latestNews);
			
			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
		} catch (ServiceException e) {
			
			e.printStackTrace();
		}
		
	}

}
