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
	private final static String DATA_IS_VALID = "valid";
	private final static String DATA_IS_NOT_VALID = "not_valid";
	private final static String DATA_IS_USED = "used";
	private Map<String, String> usersValidatedParameters = new HashMap<String, String>();

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
			System.out.println("reg username " + username);
			System.out.println("reg usersurname " + usersurname);
			System.out.println("reg login " + login);
			System.out.println("reg password " + password);
			System.out.println("reg email " + email);

			NewUserInfo userData = new NewUserInfo(username, usersurname, login, password.toCharArray(), email, role);

			getSession.setAttribute(AttributsKey.LOCAL, local);

			if (service.registration(userData)) {
				registration(getSession, response, local, role);
			} else {
				usersValidatedParameters = service.getValidatedParameters(userData);
				checkAttributValue(getSession, request, response, usersValidatedParameters);

				request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
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

	private void checkAttributValue(HttpSession getSession, HttpServletRequest request, HttpServletResponse response,
			Map<String, String> usersValidatedParameters) throws ServletException, IOException {
		Iterator<Map.Entry<String, String>> itr = usersValidatedParameters.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();
			String parametersName = entry.getKey();
			String parametersValue = entry.getValue();
			if (usersValidatedParameters.get(parametersName).equals(DATA_IS_VALID)) {
				System.out.println("reg parameter " + parametersName + "=valid  true");
				setParameterValue(getSession, request, response, parametersName);
			} else {
				setErrorInRequest(getSession, request, parametersName, parametersValue);
			}
		}
	}

	private void setParameterValue(HttpSession getSession, HttpServletRequest request, HttpServletResponse response,
			String parameter) throws ServletException, IOException {
		getSession.setAttribute(parameter, request.getParameter(parameter));

		System.out.println("reg parameterValue " + parameter + "=" + request.getParameter(parameter));
	}

	private void setErrorInRequest(HttpSession getSession, HttpServletRequest request, String parameter,
			String parametersValue) throws ServletException, IOException {

		switch (parameter) {
		case (UsersParameter.NAME_PARAM):
			getSession.setAttribute(AttributsKey.NOT_VALID_REGISTRATION_NAME, DATA_IS_NOT_VALID);
			System.out.println("NOT_VALID_REGISTRATION_NAME " + AttributsKey.NOT_VALID_REGISTRATION_NAME + " "
					+ getSession.getAttribute(AttributsKey.NOT_VALID_REGISTRATION_NAME) + " "+parameter + " "+request.getAttribute(parameter));
			break;
		case (UsersParameter.SURNAME_PARAM):
			getSession.setAttribute(AttributsKey.NOT_VALID_REGISTRATION_SURNAME, DATA_IS_NOT_VALID);
			System.out.println("NOT_VALID_REGISTRATION_SURNAME " + AttributsKey.NOT_VALID_REGISTRATION_SURNAME + " "
					+ getSession.getAttribute(AttributsKey.NOT_VALID_REGISTRATION_SURNAME)+" "+parameter+ " "+request.getAttribute(parameter));
			break;

		case (UsersParameter.LOGIN_PARAM):
			if (parametersValue.equals(DATA_IS_NOT_VALID)) {
				getSession.setAttribute(AttributsKey.NOT_VALID_REGISTRATION_LOGIN, DATA_IS_NOT_VALID);
				System.out.println("NOT_VALID_REGISTRATION_LOGIN " + AttributsKey.NOT_VALID_REGISTRATION_LOGIN + " "
						+ request.getAttribute(AttributsKey.NOT_VALID_REGISTRATION_LOGIN)+" "+parameter+ " "+request.getAttribute(parameter));
			} else {
				request.setAttribute(AttributsKey.NOT_VALID_REGISTRATION_LOGIN, DATA_IS_USED);
				System.out.println("NOT_VALID_REGISTRATION_LOGIN " + AttributsKey.NOT_VALID_REGISTRATION_LOGIN + " "
						+ request.getAttribute(AttributsKey.NOT_VALID_REGISTRATION_LOGIN)+" "+parameter+ " "+request.getAttribute(parameter));
			}
			break;

		case (UsersParameter.PASSWORD_PARAM):
			request.setAttribute(AttributsKey.NOT_VALID_REGISTRATION_PASSWORD, DATA_IS_NOT_VALID);
			System.out.println("NOT_VALID_REGISTRATION_PASSWORD " + AttributsKey.NOT_VALID_REGISTRATION_PASSWORD + " "
					+ request.getAttribute(AttributsKey.NOT_VALID_REGISTRATION_PASSWORD)+" "+parameter+ " "+request.getAttribute(parameter));
			break;

		case (UsersParameter.EMAIL_PARAM):
			if (parametersValue.equals(DATA_IS_NOT_VALID)) {
				request.setAttribute(AttributsKey.NOT_VALID_REGISTRATION_EMAIL, DATA_IS_NOT_VALID);
				System.out.println("NOT_VALID_REGISTRATION_EMAIL " + AttributsKey.NOT_VALID_REGISTRATION_EMAIL + " "
						+ request.getAttribute(AttributsKey.NOT_VALID_REGISTRATION_EMAIL)+" "+parameter+ " "+request.getAttribute(parameter));
			} else {
				request.setAttribute(AttributsKey.NOT_VALID_REGISTRATION_EMAIL, DATA_IS_USED);
				System.out.println("NOT_VALID_REGISTRATION_EMAIL " + AttributsKey.NOT_VALID_REGISTRATION_EMAIL + " "
						+ request.getAttribute(AttributsKey.NOT_VALID_REGISTRATION_EMAIL)+" "+parameter+ " "+request.getAttribute(parameter));
			}
			break;
		}

	}

	

	/*private void setParameterValue(HttpServletRequest request, Map<String, String> parameters) {
		Iterator<Map.Entry<String, String>> itr = parameters.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();
			String parametersName = entry.getKey();
			String parametersValue = entry.getValue();
			request.setAttribute(parametersName, parametersValue);
		}
	}*/

}