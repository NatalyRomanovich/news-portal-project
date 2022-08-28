package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.bean.ConnectionStatus;
import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.ComandForRegUser;
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

	private final static Logger LOG = LogManager.getLogger(DoRegistration.class);
	private final UserService service = ServiceProvider.getInstance().getUserService();

	public static final String GO_TO_NEWS_LIST_AND_LOCAL_IS = "controller?command=go_to_news_list&local=";
	private Map<String, String> usersParameters = new HashMap<String, String>();
	private Map<String, String> errorMessages = new HashMap<String, String>();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			HttpSession getSession = request.getSession(true);

			String username = request.getParameter(UsersParameter.NAME_PARAM);
			String usersurname = request.getParameter(UsersParameter.SURNAME_PARAM);
			String login = request.getParameter(UsersParameter.LOGIN_PARAM);
			String password = request.getParameter(UsersParameter.PASSWORD_PARAM);
			String email = request.getParameter(UsersParameter.EMAIL_PARAM);
			String role = UsersRole.USER.getTitle();
			String local = request.getParameter(AttributsKey.LOCAL);

			NewUserInfo userData = new NewUserInfo(username, usersurname, login, password.toCharArray(), email, role);

			getSession.setAttribute(AttributsKey.LOCAL, local);
			getSession.removeAttribute(AttributsKey.ERRORS_MESSAGE_REGISTRATION_NAME);
			getSession.removeAttribute(AttributsKey.ERRORS_MESSAGE_REGISTRATION_SURNAME);
			getSession.removeAttribute(AttributsKey.ERRORS_MESSAGE_REGISTRATION_PASSWORD);
			getSession.removeAttribute(AttributsKey.ERRORS_MESSAGE_REGISTRATION_EMAIL);

			if (service.registration(userData)) {
				registration(getSession, response, local, role);
			} else {

				usersParameters = service.getUsersParametersValueAndErrorMessage(userData,
						ComandForRegUser.GET_USERS_INVALID_PARAMETERS_MAP);
				setParameterValue(request, usersParameters);

				errorMessages = service.getUsersParametersValueAndErrorMessage(userData,
						ComandForRegUser.GET_USERS_ERROR_MESSAGES_MAP);
				System.out.println("usersParameters " + usersParameters);
				System.out.println("errorMessages " + errorMessages);
				setError(getSession, request, response, errorMessages);
			}
		} catch (ServiceException e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}

	}

	private void registration(HttpSession getSession, HttpServletResponse response, String local, String role)
			throws IOException {

		getSession.setAttribute(AttributsKey.USER, ConnectionStatus.ACTIVE);
		getSession.setAttribute(AttributsKey.REG_USER, ConnectionStatus.REGISTRED);
		getSession.setAttribute(AttributsKey.ROLE, role);
		response.sendRedirect(GO_TO_NEWS_LIST_AND_LOCAL_IS + local);
	}

	private void setError(HttpSession getSession, HttpServletRequest request, HttpServletResponse response,
			Map<String, String> errorMessages) throws IOException, ServletException {
		String errorMessageIncorrectName = errorMessages.get(UsersParameter.NAME_PARAM);
		String errorMessageIncorrectSurname = errorMessages.get(UsersParameter.SURNAME_PARAM);
		String errorMessageIncorrectLogin = errorMessages.get(UsersParameter.LOGIN_PARAM);
		String errorMessageIncorrectPassword = errorMessages.get(UsersParameter.PASSWORD_PARAM);
		String errorMessageIncorrectEmail = errorMessages.get(UsersParameter.EMAIL_PARAM);

		getSession.setAttribute(AttributsKey.REG_USER, ConnectionStatus.UNREGISTRED);
		getSession.setAttribute(AttributsKey.ERRORS_MESSAGE_REGISTRATION_NAME, errorMessageIncorrectName);
		getSession.setAttribute(AttributsKey.ERRORS_MESSAGE_REGISTRATION_SURNAME, errorMessageIncorrectSurname);
		getSession.setAttribute(AttributsKey.ERRORS_MESSAGE_REGISTRATION_LOGIN, errorMessageIncorrectLogin);
		getSession.setAttribute(AttributsKey.ERRORS_MESSAGE_REGISTRATION_PASSWORD, errorMessageIncorrectPassword);
		getSession.setAttribute(AttributsKey.ERRORS_MESSAGE_REGISTRATION_EMAIL, errorMessageIncorrectEmail);

		request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
	}

	private void setParameterValue(HttpServletRequest request, Map<String, String> parameters) {
		Iterator<Map.Entry<String, String>> itr = parameters.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();
			String parametersName = entry.getKey();
			String parametersValue = entry.getValue();
			request.setAttribute(parametersName, parametersValue);
		}
	}
}