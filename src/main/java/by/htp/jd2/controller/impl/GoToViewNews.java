package by.htp.jd2.controller.impl;

import java.io.IOException;

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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToViewNews implements Command {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private final static Logger LOG = LogManager.getLogger(GoToViewNews.class);
	private final static String URL_GO_TO_VIEW_NEWS = "controller?command=go_to_view_news";
	private final static String VIEW_LIST = "viewNews";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		News news = null;

		try {
			HttpSession requestGetSession = request.getSession(false);
			if (requestGetSession == null) {
				response.sendRedirect(JspPageName.INDEX_PAGE);
			} else {
				String id = request.getParameter(NewsParameter.JSP_ID_NEWS);
				news = newsService.findById(Integer.parseInt(id));
				StringBuilder urlForRedirect = new StringBuilder();
				urlForRedirect = urlForRedirect.append(URL_GO_TO_VIEW_NEWS).append("&")
						.append(NewsParameter.JSP_ID_NEWS).append("=").append(id);

				if (news == null) {
					response.sendRedirect(JspPageName.ERROR_PAGE);
				} else {
					request.setAttribute(AttributsKey.NEWS, news);
					request.setAttribute(AttributsKey.PRESENTATION, VIEW_LIST);
					requestGetSession.setAttribute(AttributsKey.URL, urlForRedirect.toString());
					request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
				}
			}
		} catch (ServiceNewsException e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}
}
