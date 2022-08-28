package by.htp.jd2.service.impl;

import by.htp.jd2.bean.UsersRole;
import by.htp.jd2.controller.ComandForRegUser;
import by.htp.jd2.controller.UsersParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.dao.DaoException;
import by.htp.jd2.dao.DaoProvider;
import by.htp.jd2.dao.UserDAO;

import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.UserService;
import by.htp.jd2.util.validation.impl.UserDataValidation;

public class UserServiceImpl implements UserService {

	private final UserDAO userDAO = DaoProvider.getInstance().getUserDao();
	// private final UserDataValidationSpare userDataValidation =
	// ValidationProvider.getInstance().getUserDataValidation();
	private UserDataValidation.Validator validation = new UserDataValidation.Validator();
	private static final Integer MAX_USERS_PARAMETERS = 5;
	// private static final String GET_INVALID_PARAMETERS_LIST = "invaid
	// parameters";
	// private static final String GET_ERROR_MESSAGES_LIST = "error messages";

	@Override
	public String logIn(String login, String password) throws ServiceException {
		try {
			UserDataValidation userDataValidation = validation.authDataIsCorrect(login, password).build();

			if (!userDataValidation.authDataIsCorrect() || !userDAO.logination(login, password)) {
				return UsersRole.GUEST.getTitle();
			}
			return userDAO.getRole(login, password);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public boolean registration(NewUserInfo user) throws ServiceException {
		String name = user.getUsername();
		String surname = user.getUserSurname();
		String login = user.getLogin();
		String password = String.valueOf(user.getPassword());
		String email = user.getEmail();

		try {

			UserDataValidation userDataValidation = validation.validateName(name).validateSurname(surname)
					.validateLogin(login).validatePassword(password).validateEmail(email).build();

			if (userDataValidation.getValidDataForRegistration().size() != MAX_USERS_PARAMETERS) {
				return false;
			}

			return userDAO.registration(user);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Map<String, String> getUsersParametersValueAndErrorMessage(NewUserInfo user, ComandForRegUser command)
			throws ServiceException {
		String name = user.getUsername();
		String surname = user.getUserSurname();
		String login = user.getLogin();
		String password = String.valueOf(user.getPassword());
		String email = user.getEmail();
		Map<String, String> generalParametersMap = new HashMap<String, String>();
		Map<String, String> generalErrorMessagesMap = new HashMap<String, String>();

		try {
			List<String> parametersFromDB = userDAO.getUsersNotCorrectParameters(user);

			UserDataValidation userDataValidation = validation.validateName(name).validateSurname(surname)
					.validateLogin(login).validatePassword(password).validateEmail(email).build();

			generalParametersMap = userDataValidation.getValidDataForRegistration();
			generalErrorMessagesMap = userDataValidation.getErrorMessages();

			if (parametersFromDB.contains(UsersParameter.EMAIL_PARAM)) {
				generalParametersMap.remove(UsersParameter.EMAIL_PARAM);
				generalErrorMessagesMap.put(UsersParameter.EMAIL_PARAM, "This email is already in use");
			}

			if (parametersFromDB.contains(UsersParameter.LOGIN_PARAM)) {
				generalParametersMap.remove(UsersParameter.LOGIN_PARAM);
				generalErrorMessagesMap.put(UsersParameter.LOGIN_PARAM, "This login is already in use");
			}

			if (command.equals(ComandForRegUser.GET_USERS_INVALID_PARAMETERS_MAP)) {
				return generalParametersMap;
			}

			return generalErrorMessagesMap;
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
}
