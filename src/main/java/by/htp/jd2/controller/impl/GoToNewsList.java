package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.http.Parameters;

import by.htp.jd2.bean.News;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.controller.AttributsKeys;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.controller.UsersParameters;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToNewsList implements Command {
	
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	
	private final UserService service = ServiceProvider.getInstance().getUserService();
	
	private static final String NEWS_LIST = "newsList";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
							
		List<News> newsList;
		
		try {
			
			newsList = newsService.list();
			request.setAttribute(AttributsKeys.NEWS, newsList);
			request.setAttribute(AttributsKeys.PRESENTATION, NEWS_LIST);
			
			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
			
		} catch (ServiceException e) {			
			e.printStackTrace();
		}	  	
	}

}
