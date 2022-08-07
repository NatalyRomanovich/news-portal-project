package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import by.htp.jd2.bean.News;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToViewNews implements Command {
	
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final String VIEW_LIST = "viewNews";
	private static final String NEWS_PARAMETER_ID = "id";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		News news;
		
		String id;

		id = request.getParameter(NEWS_PARAMETER_ID);
		
		try {
			news  = newsService.findById(Integer.parseInt(id));
			request.setAttribute(AttributsKey.NEWS, news);
			request.setAttribute(AttributsKey.PRESENTATION, VIEW_LIST);

			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
