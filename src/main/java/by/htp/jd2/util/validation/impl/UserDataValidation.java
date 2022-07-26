package by.htp.jd2.util.validation.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import by.htp.jd2.dao.DaoException;
import by.htp.jd2.dao.DaoProvider;
import by.htp.jd2.dao.UserDAO;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.util.UsersParameter;
import by.htp.jd2.util.validation.ValidatorBuilder;

public class UserDataValidation {

	private final boolean authDataCorrect;
	private final Map<String, String> dataForRegistration;

	private UserDataValidation(Validator validator) {
		authDataCorrect = validator.authDataCorrect;
		dataForRegistration = validator.dataForRegistration;
	}

	public boolean isAuthDataCorrect() {
		return this.authDataCorrect;
	}

	public Map<String, String> getDataForRegistration() {
		return this.dataForRegistration;
	}

	public static class Validator implements ValidatorBuilder {

		private final UserDAO userDAO = DaoProvider.getInstance().getUserDao();
		private final static String NAME_AND_SURNAME_CHECK_PATTERN = "[a-zA-Z]{2,}";
		private final static String LOGIN_CHECK_PATTERN = "[A-Z a-z 0-9]{2,20}";
		private final static String PASSWORD_CHECK_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
		private final static String EMAIL_CHECK_PATTERN = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";
		private final static String DATA_IS_NOT_CORRECT = "not_correct";
		private final static String DATA_IS_USED = "used";
		private final static String CORRECT = "correct";

		public boolean authDataCorrect;
		private Map<String, String> dataForRegistration = new HashMap<String, String>();

		public Validator authDataCorrect(String login, String password) throws ServiceException {
			authDataCorrect = validateAuthData(login, password);
			return this;
		}

		public boolean validateAuthData(String login, String password) throws ServiceException {
			if (login.isEmpty() || password.isEmpty()) {
				return false;
			}
			return true;
		}

		public Validator validateName(String name) throws ServiceException {
			if (name.isEmpty() || !Pattern.compile(NAME_AND_SURNAME_CHECK_PATTERN).matcher(name).matches()) {
				dataForRegistration.put(UsersParameter.NAME_PARAM, DATA_IS_NOT_CORRECT);
				return this;
			}
			dataForRegistration.put(UsersParameter.NAME_PARAM, name);
			return this;
		}

		public Validator validateSurname(String surname) throws ServiceException {
			if (surname.isEmpty() || !Pattern.compile(NAME_AND_SURNAME_CHECK_PATTERN).matcher(surname).matches()) {
				dataForRegistration.put(UsersParameter.SURNAME_PARAM, DATA_IS_NOT_CORRECT);
				return this;
			}
			dataForRegistration.put(UsersParameter.SURNAME_PARAM, surname);
			return this;
		}

		public Validator validateLogin(String login) throws ServiceException {
			try {
				if (userDAO.isLoginUsed(login)) {
					dataForRegistration.put(UsersParameter.LOGIN_PARAM, DATA_IS_USED);
					return this;
				}
				if (login.isEmpty() || !Pattern.compile(LOGIN_CHECK_PATTERN).matcher(login).matches()) {
					dataForRegistration.put(UsersParameter.LOGIN_PARAM, DATA_IS_NOT_CORRECT);
					return this;
				}
				dataForRegistration.put(UsersParameter.LOGIN_PARAM, login);
				return this;
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}

		public Validator validatePassword(String password) throws ServiceException {

			if (password.isEmpty() || !Pattern.compile(PASSWORD_CHECK_PATTERN).matcher(password).matches()) {
				dataForRegistration.put(UsersParameter.PASSWORD_PARAM, DATA_IS_NOT_CORRECT);
				return this;
			}
			dataForRegistration.put(UsersParameter.PASSWORD_PARAM, CORRECT);
			return this;
		}

		public Validator validateEmail(String email) throws ServiceException {
			try {
				if (userDAO.isEmaillUsed(email)) {
					dataForRegistration.put(UsersParameter.EMAIL_PARAM, DATA_IS_USED);
					return this;
				}
				if (email.isEmpty() || !Pattern.compile(EMAIL_CHECK_PATTERN).matcher(email).matches()) {
					dataForRegistration.put(UsersParameter.EMAIL_PARAM, DATA_IS_NOT_CORRECT);
					return this;
				}
				dataForRegistration.put(UsersParameter.EMAIL_PARAM, email);
				return this;
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
		}

		public UserDataValidation build() throws ServiceException {
			return new UserDataValidation(this);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDataValidation that = (UserDataValidation) o;
		return authDataCorrect == that.authDataCorrect && Objects.equals(dataForRegistration, that.dataForRegistration);
	}

	@Override
	public int hashCode() {
		return Objects.hash(authDataCorrect, dataForRegistration);
	}

	@Override
	public String toString() {
		return "UserDataValidation{" + "authDataCorrect=" + authDataCorrect + "dataForRegistration="
				+ dataForRegistration + '}';
	}
}
