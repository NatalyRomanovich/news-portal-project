package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.bean.News;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.controller.NewsParameter;
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

public class AddNews implements Command {
		private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	public static final String COMMAND_IS_DONE = "done";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		String title = request.getParameter(NewsParameter.JSP_TITLE);
		String briefNews = request.getParameter(NewsParameter.JSP_BRIEF_NEWS);
		String content = request.getParameter(NewsParameter.JSP_CONTENT);
		String newsData = request.getParameter(NewsParameter.JSP_DATA);

		HttpSession getSession = request.getSession(true);

		News news = new News(title, briefNews, content, newsData);
		System.out.println("news " +news);
		try {
			System.out.println("newsService.save(news) " + newsService.save(news));
			if (newsService.save(news)) {
			
				// getSession.setAttribute(AttributsKey.USER, ConnectionStatus.ACTIVE);
				// getSession.setAttribute(AttributsKey.ROLE, role);
				getSession.removeAttribute(AttributsKey.REG_USER);
				getSession.setAttribute(AttributsKey.ADD_NEWS, COMMAND_IS_DONE);
				response.sendRedirect("controller?command=go_to_news_list");
				getSession.removeAttribute(AttributsKey.ADD_NEWS);

			} else {
				response.sendRedirect(JspPageName.ERROR_PAGE);
			}
		} catch (ServiceNewsException e) {
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
