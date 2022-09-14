package by.htp.jd2.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.bean.News;
import by.htp.jd2.controller.Command;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.service.ServiceValidationNewsException;
import by.htp.jd2.util.AttributsKey;
import by.htp.jd2.util.JspPageName;
import by.htp.jd2.util.NewsParameter;
import by.htp.jd2.util.UsersRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class EditNews implements Command {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private final static Logger LOG = LogManager.getLogger(EditNews.class);
	private final static String GO_TO_NEWS_LIST = "controller?command=go_to_news_list";
	private final static String GO_TO_DO_ACTION = "controller?command=go_to_do_action&";
	private final static String COMMAND_IS_DONE = "done";
	private final static String EDIT_NEWS = "edit";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			HttpSession requestGetSession = request.getSession(false);

			if (requestGetSession == null) {
				response.sendRedirect(JspPageName.INDEX_PAGE);
			} else {

				String role = (String) requestGetSession.getAttribute(AttributsKey.ROLE);
				int id = Integer.parseInt(request.getParameter(NewsParameter.JSP_ID_NEWS));
				String title = request.getParameter(NewsParameter.JSP_TITLE);
				String content = request.getParameter(NewsParameter.JSP_CONTENT);
				String briefNews = null;
				String newsDate = request.getParameter(NewsParameter.JSP_DATE);

				News news = new News(id, title, briefNews, content, newsDate);

				if (!role.equals(UsersRole.ADMIN.getTitle())) {
					response.sendRedirect(JspPageName.ERROR_PAGE);
				} else {
					if (newsService.update(news)) {
						requestGetSession.setAttribute(AttributsKey.EDIT_NEWS, COMMAND_IS_DONE);
						response.sendRedirect(GO_TO_NEWS_LIST);
					} else {
						response.sendRedirect(JspPageName.ERROR_PAGE);
					}
				}
			}
		} catch (ServiceValidationNewsException e) {
			int id = Integer.parseInt(request.getParameter(NewsParameter.JSP_ID_NEWS));
			StringBuilder urlForErrorRedirect = new StringBuilder();
			urlForErrorRedirect = urlForErrorRedirect.append(GO_TO_DO_ACTION).append(AttributsKey.NEWS_COMMANDS_NAME)
					.append("=").append(EDIT_NEWS).append("&").append(NewsParameter.JSP_ID_NEWS).append("=").append(id);
			response.sendRedirect(urlForErrorRedirect.toString());
		} catch (ServiceNewsException e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
