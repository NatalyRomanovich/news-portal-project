package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.bean.News;
import by.htp.jd2.controller.Command;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceNewsException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.util.AttributsKey;
import by.htp.jd2.util.JspPageName;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToNewsList implements Command {
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private final static Logger LOG = LogManager.getLogger(GoToNewsList.class);
	private final static String URL_GO_TO_NEWS_LIST = "controller?command=go_to_news_list";
	private final static String NEWS_LIST = "newsList";
	private final static int NEWS_ON_ONE_PAGE = 5;
	private final static int FIRST_PAGE = 1;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<News> newsList;
		int pageNumber;
		int newsListSize;
		List<Integer> pages;

		try {
			HttpSession requestGetSession = request.getSession(true);
			requestGetSession.setAttribute(AttributsKey.URL, URL_GO_TO_NEWS_LIST);
			requestGetSession.removeAttribute(AttributsKey.REG_USER);
			pageNumber = getPageNumber(request.getParameter(AttributsKey.PAGE_NUMBER));
			newsListSize = newsService.getNewsListSize();
			pages = getPagesNumbersList(newsListSize);

			newsList = newsService.list(pageNumber, NEWS_ON_ONE_PAGE);
			request.setAttribute(AttributsKey.PAGES, pages);
			request.setAttribute(AttributsKey.NEWS, newsList);
			request.setAttribute(AttributsKey.PRESENTATION, NEWS_LIST);
			requestGetSession.setAttribute(AttributsKey.PAGE_NUMBER, pageNumber);
			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);

			requestGetSession.removeAttribute(AttributsKey.DELETE_NEWS);
			requestGetSession.removeAttribute(AttributsKey.ADD_NEWS);
			requestGetSession.removeAttribute(AttributsKey.EDIT_NEWS);
		} catch (ServiceNewsException e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}

	private List<Integer> getPagesNumbersList(int newsListSize) throws ServiceNewsException {

		List<Integer> pages = new ArrayList();
		if (newsListSize == 0) {
			pages.add(++newsListSize);
			return pages;
		}
		int remainderOfDivision = newsListSize % NEWS_ON_ONE_PAGE;
		int pagesAmount = newsListSize / NEWS_ON_ONE_PAGE;
		if (remainderOfDivision != 0) {
			pagesAmount++;
		}
		for (int i = 0; i < pagesAmount; i++) {
			pages.add(i + 1);
		}
		return pages;
	}

	private int getPageNumber(String pageNumString) throws ServiceNewsException {
		int pageNumber;
		if (pageNumString == null) {
			return FIRST_PAGE;
		}
		pageNumber = Integer.valueOf(pageNumString);
		return pageNumber;
	}
}
