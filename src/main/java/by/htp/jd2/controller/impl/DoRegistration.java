package by.htp.jd2.controller.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.controller.Command;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceProvider;
import by.htp.jd2.service.ServiceRegistrationException;
import by.htp.jd2.service.ServiceValidationException;
import by.htp.jd2.service.UserService;
import by.htp.jd2.util.AttributsKey;
import by.htp.jd2.util.ConnectionStatus;
import by.htp.jd2.util.JspPageName;
import by.htp.jd2.util.UsersParameter;
import by.htp.jd2.util.UsersRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DoRegistration implements Command {
	private final UserService service = ServiceProvider.getInstance().getUserService();
	private final static Logger LOG = LogManager.getLogger(DoRegistration.class);
	private final static String GO_TO_NEWS_LIST = "controller?command=go_to_news_list";
	private final static String DATA_IS_NOT_CORRECT = "not_correct";
	private final static String DATA_IS_USED = "used";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> usersValidatedParameters = new HashMap<String, String>();
		try {
			HttpSession requestGetSession = request.getSession(true);

			String username = request.getParameter(UsersParameter.NAME_PARAM).trim();
			String usersurname = request.getParameter(UsersParameter.SURNAME_PARAM).trim();
			String login = request.getParameter(UsersParameter.LOGIN_PARAM).trim();
			String password = request.getParameter(UsersParameter.PASSWORD_PARAM).trim();
			String email = request.getParameter(UsersParameter.EMAIL_PARAM).trim();
			String role = UsersRole.USER.getTitle();

			NewUserInfo userData = new NewUserInfo(username, usersurname, login, password.toCharArray(), email, role);

			usersValidatedParameters = service.registrationResult(userData);
			if (usersValidatedParameters.isEmpty()) {
				registration(requestGetSession, request, response, role);
			} else {
				setDataForReRegistration(request, usersValidatedParameters);
				request.setAttribute(AttributsKey.MAP, usersValidatedParameters);
				request.getRequestDispatcher(JspPageName.BASE_PAGE_LAYOUT).forward(request, response);
			}
		} catch (ServiceRegistrationException | ServiceValidationException | ServiceException e) {
			LOG.error(e);
			response.sendRedirect(JspPageName.ERROR_PAGE);
		}
	}

	private void registration(HttpSession requestGetSession, HttpServletRequest request, HttpServletResponse response,
			String role) throws IOException {

		requestGetSession.setAttribute(AttributsKey.USER, ConnectionStatus.ACTIVE);
		request.setAttribute(AttributsKey.REG_USER, ConnectionStatus.REGISTRED);
		requestGetSession.setAttribute(AttributsKey.ROLE, role);
		response.sendRedirect(GO_TO_NEWS_LIST);
	}

	private void setDataForReRegistration(HttpServletRequest request, Map<String, String> usersValidatedParameters)
			throws ServletException, IOException {
		Iterator<Map.Entry<String, String>> itr = usersValidatedParameters.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();
			String parametersName = entry.getKey();
			String parametersValue = entry.getValue();
			if (!parametersValue.equals(DATA_IS_NOT_CORRECT) && !parametersValue.equals(DATA_IS_USED)) {
				request.setAttribute(parametersName, request.getParameter(parametersName));
			}
		}
	}
}