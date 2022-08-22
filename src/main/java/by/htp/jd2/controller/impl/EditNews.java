package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.News;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.controller.NewsParameter;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class EditNews implements Command {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	public static final String COMMAND_IS_DONE = "done";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession getSession = request.getSession(true);
		String local = request.getParameter(AttributsKey.LOCAL);

		try {
			// getSession.setAttribute(AttributsKey.LOCAL, local);
			int id = Integer.parseInt(request.getParameter(NewsParameter.JSP_ID_NEWS));
			String title = request.getParameter(NewsParameter.JSP_TITLE);
			String briefNews = request.getParameter(NewsParameter.JSP_BRIEF_NEWS);
			String content = request.getParameter(NewsParameter.JSP_CONTENT);
			String newsDate = request.getParameter(NewsParameter.JSP_DATE);
			News news = new News(id, title, briefNews, content, newsDate);

			if (newsService.update(news)) {
				// getSession.setAttribute(AttributsKey.USER, ConnectionStatus.ACTIVE);
				getSession.setAttribute(AttributsKey.ROLE, UsersRole.ADMIN.getTitle());
				getSession.removeAttribute(AttributsKey.REG_USER);
				getSession.setAttribute(AttributsKey.EDIT_NEWS, COMMAND_IS_DONE);
				response.sendRedirect("controller?command=go_to_news_list&local=" + local);
				getSession.removeAttribute(AttributsKey.EDIT_NEWS);
				getSession.removeAttribute(AttributsKey.NEWS_COMMANDS_NAME);
			}
		} catch (Exception ex) {

			response.sendRedirect(JspPageName.ERROR_PAGE);
		}

	}

}
