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
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToViewNews implements Command {

	private final static Logger LOG = LogManager.getLogger(GoToViewNews.class);
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	public static final String URL_GO_TO_VIEW_NEWS = "controller?command=go_to_view_news&";
	private static final String VIEW_LIST = "viewNews";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		News news = null;

		try {
			HttpSession getSession = request.getSession(true);

			String local = request.getParameter(AttributsKey.LOCAL);
			String id = request.getParameter(NewsParameter.JSP_ID_NEWS);
			getSession.setAttribute(AttributsKey.LOCAL, local);
			news = newsService.findById(Integer.parseInt(id));
			String urlForRedirect = URL_GO_TO_VIEW_NEWS + NewsParameter.JSP_ID_NEWS + "=" + id;

			if (news == null) {
				response.sendRedirect(JspPageName.ERROR_PAGE);
			} else {
				request.setAttribute(AttributsKey.NEWS, news);
				request.setAttribute(AttributsKey.PRESENTATION, VIEW_LIST);
				getSession.setAttribute(AttributsKey.URL, urlForRedirect);
				request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
			}
		} catch (ServiceNewsException e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
