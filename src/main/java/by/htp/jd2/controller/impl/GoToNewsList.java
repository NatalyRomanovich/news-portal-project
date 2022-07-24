package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.http.Parameters;

import by.htp.jd2.bean.News;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.controller.AttributsKeys;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.controller.UsersParameters;
import by.htp.jd2.service.NewsService;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToNewsList implements Command {
	
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	
	private final UserService service = ServiceProvider.getInstance().getUserService();
	
	private static final String NEWS_LIST = "newsList";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//String login = request.getParameter(UsersParameters.JSP_LOGIN_PARAM);
		//String password = request.getParameter(UsersParameters.JSP_PASSWORD_PARAM);
					
		List<News> newsList;
		
		try {
			//if (isAuthorizedUser(login, password)) {
			newsList = newsService.list();
			request.setAttribute(AttributsKeys.NEWS, newsList);
			request.setAttribute(AttributsKeys.PRESENTATION, NEWS_LIST);
			//request.setAttribute("news", null);

			request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
			//}
		} catch (ServiceException e) {
			
			e.printStackTrace();
		}
	  
		
	}
	
	/*public boolean isAuthorizedUser (String login, String password) throws ServiceException {
		String role = service.logIn(login, password);
		System.out.println (role);
		boolean result = false;
		if (role.equals("admin") | role.equals(UsersRole.USER)) {
			result = true;
		}
		
		System.out.println (result);
		return result;
	} */

}
