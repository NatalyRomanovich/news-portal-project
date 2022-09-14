package by.htp.jd2.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.bean.News;
import by.htp.jd2.controller.Command;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.util.AttributsKey;
import by.htp.jd2.util.JspPageName;
import by.htp.jd2.util.NewsParameter;
import by.htp.jd2.util.UsersRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToDoAdminsAction implements Command {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private final static Logger LOG = LogManager.getLogger(AddNews.class);
	private final static String ADD_NEWS = "add";
	private final static String EDIT_NEWS = "edit";
	private final static String GO_TO_DO_ACTION = "controller?command=go_to_do_action&";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession requestGetSession = request.getSession(false);
		StringBuilder urlForRedirect = new StringBuilder();

		if (requestGetSession == null) {
			response.sendRedirect(JspPageName.INDEX_PAGE);
		} else {

			String role = (String) requestGetSession.getAttribute(AttributsKey.ROLE);
			if (!role.equals(UsersRole.ADMIN.getTitle())) {
				response.sendRedirect(JspPageName.ERROR_PAGE);
			} else {

				String action = request.getParameter(AttributsKey.NEWS_COMMANDS_NAME);

				urlForRedirect = urlForRedirect.append(GO_TO_DO_ACTION).append(AttributsKey.NEWS_COMMANDS_NAME)
						.append("=").append(action);

				requestGetSession.setAttribute(AttributsKey.URL, urlForRedirect.toString());

				if (action.equals(ADD_NEWS)) {
					addNews(requestGetSession, request, response, action);
				} else if (action.equals(EDIT_NEWS)) {
					try {
						News news = null;
						String id = request.getParameter(NewsParameter.JSP_ID_NEWS);

						urlForRedirect = urlForRedirect.append("&").append(NewsParameter.JSP_ID_NEWS).append("=")
								.append(id);

						requestGetSession.setAttribute(AttributsKey.URL, urlForRedirect.toString());
						news = newsService.findById(Integer.parseInt(id));
						editNews(requestGetSession, request, response, news, action);
					} catch (ServiceNewsException e) {
						LOG.error(e);
						response.sendRedirect(JspPageName.ERROR_PAGE);
					}
				} else {
					LOG.log(Level.INFO, "Command unknown");
					response.sendRedirect(JspPageName.ERROR_PAGE);
				}
			}
		}
	}

	private void addNews(HttpSession requestGetSession, HttpServletRequest request, HttpServletResponse response,
			String action) throws IOException, ServletException {
		request.setAttribute(AttributsKey.NEWS_COMMANDS_NAME, action);
		request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
		requestGetSession.removeAttribute(AttributsKey.NEWS_COMMANDS_NAME);
	}

	private void editNews(HttpSession requestGetSession, HttpServletRequest request, HttpServletResponse response,
			News news, String action) throws IOException, ServletException {

		if (news == null) {
			response.sendRedirect(JspPageName.ERROR_PAGE);
		} else {
			request.setAttribute(AttributsKey.NEWS, news);
			request.setAttribute(AttributsKey.NEWS_COMMANDS_NAME, action);
			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
		}
	}
}
