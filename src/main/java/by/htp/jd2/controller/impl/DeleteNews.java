package by.htp.jd2.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.controller.Command;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.util.AttributsKey;
import by.htp.jd2.util.JspPageName;
import by.htp.jd2.util.NewsParameter;
import by.htp.jd2.util.UsersRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DeleteNews implements Command {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private final static Logger LOG = LogManager.getLogger(DeleteNews.class);
	private final static String GO_TO_NEWS_LIST = "controller?command=go_to_news_list";
	private final static String COMMAND_IS_DONE = "done";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			HttpSession requestGetSession = request.getSession(false);
			if (requestGetSession == null) {
				response.sendRedirect(JspPageName.INDEX_PAGE);
			} else {
				String role = (String) requestGetSession.getAttribute(AttributsKey.ROLE);
				if (!role.equals(UsersRole.ADMIN.getTitle())) {
					response.sendRedirect(JspPageName.ERROR_PAGE);
				} else {
					String[] idNewses = request.getParameterValues(NewsParameter.JSP_ID_NEWS);

					if (newsService.deleteNews(idNewses)) {
						requestGetSession.removeAttribute(AttributsKey.REG_USER);
						requestGetSession.setAttribute(AttributsKey.DELETE_NEWS, COMMAND_IS_DONE);
						response.sendRedirect(GO_TO_NEWS_LIST);
					}
					else {
						response.sendRedirect(JspPageName.ERROR_PAGE);
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
