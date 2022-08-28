package by.htp.jd2.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.bean.News;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.controller.NewsParameter;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class EditNews implements Command {
	private final static Logger LOG = LogManager.getLogger(EditNews.class);
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	public static final String COMMAND_IS_DONE = "done";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			HttpSession getSession = request.getSession(true);
			String local = request.getParameter(AttributsKey.LOCAL);
			int id = Integer.parseInt(request.getParameter(NewsParameter.JSP_ID_NEWS));
			String title = request.getParameter(NewsParameter.JSP_TITLE);
			String briefNews = request.getParameter(NewsParameter.JSP_BRIEF_NEWS);
			String content = request.getParameter(NewsParameter.JSP_CONTENT);
			String newsDate = request.getParameter(NewsParameter.JSP_DATE);
			News news = new News(id, title, briefNews, content, newsDate);

			if (newsService.update(news)) {
				getSession.setAttribute(AttributsKey.EDIT_NEWS, COMMAND_IS_DONE);
				response.sendRedirect("controller?command=go_to_news_list&local=" + local);
			}
		} catch (Exception e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
