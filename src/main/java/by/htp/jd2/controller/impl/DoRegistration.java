package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.bean.ClientsRole;
import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.controller.UsersParameter;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoRegistration implements Command {
	
	private final UserService service = ServiceProvider.getInstance().getUserService();

	private static final String ERROR_REGISTRATION_MESSAGE = "Invalid data entered";
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter(UsersParameter.JSP_NAME_PARAM);
	    String usersurname = request.getParameter(UsersParameter.JSP_SURNAME_PARAM);
	    String login = request.getParameter(UsersParameter.JSP_LOGIN_PARAM);
	    String password = request.getParameter(UsersParameter.JSP_PASSWORD_PARAM);
	    String email = request.getParameter(UsersParameter.JSP_EMAIL_PARAM);
	    String role = UsersRole.USER;    
	    HttpSession getSession = request.getSession(true);
		
	    NewUserInfo userData = new NewUserInfo (username, usersurname, login, password, email, role);
	    try {	    		 	   
	    	if (service.registration(userData)) {
				getSession.setAttribute(AttributsKey.USER, ConnectionStatus.ACTIVE);
				getSession.setAttribute(AttributsKey.REG_USER, ConnectionStatus.REGISTRED);
				getSession.setAttribute(AttributsKey.ROLE, role);
				response.sendRedirect("controller?command=go_to_news_list");			             
				
			}
			else {
				getSession.setAttribute(AttributsKey.REG_USER, ConnectionStatus.UNREGISTRED);
				request.setAttribute(AttributsKey.ERRORS_REGISTRATION_NAME, ERROR_REGISTRATION_MESSAGE);
				//request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
				//response.sendRedirect("controller?command=go_to_base_page");	
				response.sendRedirect("controller?command=go_to_news_list");	
			}		     
						
		}catch (ServiceException e) {
			//request.getRequestDispatcher().forward(request, response);
	    }
	    	
    }
	
}
		
 
