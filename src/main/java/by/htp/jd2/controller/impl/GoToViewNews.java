package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import by.htp.jd2.bean.News;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.controller.NewsParameter;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToViewNews implements Command {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final String VIEW_LIST = "viewNews";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		News news = null;

		String id;

		id = request.getParameter(NewsParameter.JSP_ID_NEWS);

		try {
			request.getSession(true).setAttribute(AttributsKey.LOCAL, request.getParameter(AttributsKey.LOCAL));
			news = newsService.findById(Integer.parseInt(id));
			if (news == null) {
				response.sendRedirect(JspPageName.ERROR_PAGE);
			} else {
				request.setAttribute(AttributsKey.NEWS, news);
				request.setAttribute(AttributsKey.PRESENTATION, VIEW_LIST);
				request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
			}
		} catch (ServiceNewsException e) {
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
