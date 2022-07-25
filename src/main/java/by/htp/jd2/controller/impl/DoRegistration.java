package by.htp.jd2.controller.impl;

import java.io.IOException;

import by.htp.jd2.bean.ClientsRole;
import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.controller.AttributsKeys;
import by.htp.jd2.controller.Command;
import by.htp.jd2.controller.JspPageName;
import by.htp.jd2.controller.UsersParameters;
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
		String username = request.getParameter(UsersParameters.JSP_NAME_PARAM);
	    String usersurname = request.getParameter(UsersParameters.JSP_SURNAME_PARAM);
	    String login = request.getParameter(UsersParameters.JSP_LOGIN_PARAM);
	    String password = request.getParameter(UsersParameters.JSP_PASSWORD_PARAM);
	    String email = request.getParameter(UsersParameters.JSP_EMAIL_PARAM);
	    String role = UsersRole.USER;    
	    HttpSession getSession = request.getSession(true);
		
	    NewUserInfo userData = new NewUserInfo (username, usersurname, login, password, email, role);
	    try {	    		 	   
			if (service.registration(userData)) {
				getSession.setAttribute(AttributsKeys.USER, ConnectionStatus.ACTIVE);
				getSession.setAttribute(AttributsKeys.REG_USER, ConnectionStatus.REGISTRED);
				getSession.setAttribute(AttributsKeys.ROLE, role);
				response.sendRedirect("controller?command=go_to_news_list");			             
				
			}
			else {
				getSession.setAttribute(AttributsKeys.REG_USER, ConnectionStatus.UNREGISTRED);
				request.setAttribute(AttributsKeys.ERRORS_REGISTRATION_NAME, ERROR_REGISTRATION_MESSAGE);
				request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
								
			}		     
						
		}catch (ServiceException e) {
			e.printStackTrace();
	    }
	    	
    }
	
}
		
 
