package by.htp.jd2.service.impl;

import by.htp.jd2.controller.ComandForRegUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.dao.DaoException;
import by.htp.jd2.dao.DaoProvider;
import by.htp.jd2.dao.UserDAO;

import by.htp.jd2.service.ServiceException;
import by.htp.jd2.service.ServiceRegistrationException;
import by.htp.jd2.service.ServiceValidationException;
import by.htp.jd2.service.UserService;
import by.htp.jd2.util.AttributsKey;
import by.htp.jd2.util.UsersParameter;
import by.htp.jd2.util.UsersRole;
import by.htp.jd2.util.validation.impl.UserDataValidation;

public class UserServiceImpl implements UserService {

	private final UserDAO userDAO = DaoProvider.getInstance().getUserDao();
	private UserDataValidation.Validator validation = new UserDataValidation.Validator();
	private final static String DATA_IS_NOT_CORRECT = "not_correct";
	private final static String DATA_IS_USED = "used";
	private final static Integer MAX_PARAMETERS_NUMBER = 5;

	@Override
	public String logIn(String login, String password) throws ServiceException {
		try {
			UserDataValidation userDataValidation = validation.authDataCorrect(login, password).build();

			if (!userDataValidation.isAuthDataCorrect() || !userDAO.logination(login, password)) {
				return UsersRole.GUEST.getTitle();
			}
			return userDAO.getRole(login, password);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Map<String, String> registrationResult(NewUserInfo user) throws ServiceException {
		String name = user.getUsername();
		String surname = user.getUserSurname();
		String login = user.getLogin();
		String password = String.valueOf(user.getPassword());
		String email = user.getEmail();

		try {
			UserDataValidation userDataValidation = validation.validateName(name).validateSurname(surname)
					.validateLogin(login).validatePassword(password).validateEmail(email).build();
			Map<String, String> validatedParameters = userDataValidation.getDataForRegistration();

			if (validatedParameters.size() != MAX_PARAMETERS_NUMBER) {
				throw new ServiceValidationException("Data cannot be validated");
			}
			if (validatedParameters.containsValue(DATA_IS_NOT_CORRECT)
					|| validatedParameters.containsValue(DATA_IS_USED)) {
				return validatedParameters;
			}
			if (!userDAO.registration(user)) {
				throw new ServiceRegistrationException("User cannot be registred");
			}
			validatedParameters.clear();
			return validatedParameters;
		} catch (ServiceValidationException e) {
			throw new ServiceException(e);
		} catch (ServiceRegistrationException e) {
			throw new ServiceException(e);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}

	}

	/*
	 * @Override public Map<String, String> getValidatedParameters(NewUserInfo user)
	 * throws ServiceException { String name = user.getUsername(); String surname =
	 * user.getUserSurname(); String login = user.getLogin(); String password =
	 * String.valueOf(user.getPassword()); String email = user.getEmail();
	 * 
	 * Map<String, String> validatedParameters = new HashMap<String, String>();
	 * 
	 * UserDataValidation userDataValidation =
	 * validation.validateName(name).validateSurname(surname)
	 * .validateLogin(login).validatePassword(password).validateEmail(email).build()
	 * ; validatedParameters = userDataValidation.getDataForRegistration();
	 * 
	 * return validatedParameters; }
	 */
}
