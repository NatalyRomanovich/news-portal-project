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
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToDoAction implements Command {
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	public static final String ADD_NEWS = "add";
	public static final String EDIT_NEWS = "edit";	
	public static final String COMMAND_IS_DONE = "done";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession getSession = request.getSession(true);
		getSession.setAttribute(AttributsKey.LOCAL, request.getParameter(AttributsKey.LOCAL));

		String action = request.getParameter(AttributsKey.NEWS_COMMANDS_NAME);

		switch (action) {
		case (ADD_NEWS):
			addNews(getSession, request, response, action);
			break;
		case (EDIT_NEWS):
			try {
				News news = null;
				String id = request.getParameter(NewsParameter.JSP_ID_NEWS);
				news = newsService.findById(Integer.parseInt(id));
				editNews(getSession, request, response, news);
			} catch (ServiceNewsException e) {
				response.sendRedirect(JspPageName.ERROR_PAGE);
			}
			break;
		}
	}

	private void addNews(HttpSession getSession, HttpServletRequest request, HttpServletResponse response,
			String action) throws IOException, ServletException {
		getSession.setAttribute(AttributsKey.USER, ConnectionStatus.ACTIVE);
		getSession.setAttribute(AttributsKey.NEWS_COMMANDS_NAME, action);
		request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
		getSession.removeAttribute(AttributsKey.NEWS_COMMANDS_NAME);
	}

	private void editNews(HttpSession getSession, HttpServletRequest request, HttpServletResponse response, News news)
			throws IOException, ServletException {

		if (news == null) {
			response.sendRedirect(JspPageName.ERROR_PAGE);
		} else {
			request.setAttribute(AttributsKey.NEWS, news);
			getSession.setAttribute(AttributsKey.USER, ConnectionStatus.ACTIVE);
			getSession.setAttribute(AttributsKey.NEWS_COMMANDS_NAME, EDIT_NEWS);
			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
			getSession.removeAttribute(AttributsKey.NEWS_COMMANDS_NAME);
		}
	}
}
