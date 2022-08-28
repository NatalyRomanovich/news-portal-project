package by.htp.jd2.util.validation.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.controller.UsersParameter;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.util.validation.ValidatorBuilder;

public class UserDataValidation {

	private final boolean authDataIsCorrect;
	private final Map<String, String> validDataForRegistration;
	private final Map<String, String> errorMessages;

	private UserDataValidation(Validator validator) {
		authDataIsCorrect = validator.authDataIsCorrect;
		validDataForRegistration = validator.validDataForRegistration;
		errorMessages = validator.errorMessages;
	}

	public boolean authDataIsCorrect() {
		return this.authDataIsCorrect;
	}

	public Map<String, String> getValidDataForRegistration() {
		return this.validDataForRegistration;
	}

	public Map<String, String> getErrorMessages() {
		return this.errorMessages;
	}

	public static class Validator implements ValidatorBuilder {

		private final static String NAME_AND_SURNAME_CHECK = "[a-zA-Z]{2,}";
		private final static String LOGIN_CHECK = "[A-Z a-z 0-9]{2,20}";
		private final static String PASWORD_CHECK = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
		private final static String EMAIL_CHECK = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";
		private final static String IS_CORRECT = "correct";

		private Map<String, String> validDataForRegistration = new HashMap<String, String>();
		private Map<String, String> errorMessages = new HashMap<String, String>();
		public boolean authDataIsCorrect;

		public Validator authDataIsCorrect(String login, String password) throws ServiceException {
			authDataIsCorrect = validateAuthData(login, password);
			return this;
		}

		public boolean validateAuthData(String login, String password) throws ServiceException {
			if (login.isEmpty() || password.isEmpty()) {
				return false;
			}
			return true;
		}

		public Validator validateName(String name) throws ServiceException {
			if (!(name.isEmpty()) && Pattern.compile(NAME_AND_SURNAME_CHECK).matcher(name).matches()) {
				validDataForRegistration.put(UsersParameter.NAME_PARAM, name);
			} else {
				errorMessages.put(UsersParameter.NAME_PARAM,
						"The name is incorrect. Use only alphabetic characters to enter the name.");
			}

			return this;
		}

		public Validator validateSurname(String surname) throws ServiceException {
			if (!(surname.isEmpty()) && Pattern.compile(NAME_AND_SURNAME_CHECK).matcher(surname).matches()) {
				validDataForRegistration.put(UsersParameter.SURNAME_PARAM, surname);
			} else {
				errorMessages.put(UsersParameter.SURNAME_PARAM,
						"The surname is incorrect. Use only alphabetic characters to enter the surname.");
			}
			return this;
		}

		public Validator validateLogin(String login) throws ServiceException {
			if (!(login.isEmpty()) && Pattern.compile(LOGIN_CHECK).matcher(login).matches()) {
				validDataForRegistration.put(UsersParameter.LOGIN_PARAM, login);
			} else {
				errorMessages.put(UsersParameter.LOGIN_PARAM,
						"The login is incorrect. To enter a login, use the letters of the alphabet and numbers.");
			}
			return this;
		}

		public Validator validatePassword(String password) throws ServiceException {
			if (!(password.isEmpty()) && Pattern.compile(PASWORD_CHECK).matcher(password).matches()) {
				validDataForRegistration.put(UsersParameter.PASSWORD_PARAM, IS_CORRECT);
			} else {
				errorMessages.put(UsersParameter.PASSWORD_PARAM,
						"The password is incorrect. Check out the password requirements.");
			}
			return this;
		}

		public Validator validateEmail(String email) throws ServiceException {
			if (!(email.isEmpty()) && Pattern.compile(EMAIL_CHECK).matcher(email).matches()) {
				validDataForRegistration.put(UsersParameter.EMAIL_PARAM, email);
			} else {
				errorMessages.put(UsersParameter.EMAIL_PARAM, "The email is incorrect.");
			}
			return this;
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
		return authDataIsCorrect == that.authDataIsCorrect
				&& Objects.equals(validDataForRegistration, that.validDataForRegistration)
				&& Objects.equals(errorMessages, that.errorMessages);
	}

	@Override
	public int hashCode() {
		return Objects.hash(authDataIsCorrect, validDataForRegistration, errorMessages);
	}

	@Override
	public String toString() {
		return "UserDataValidation{" + "authDataIsCorrect=" + authDataIsCorrect + ", validDataForRegistration="
				+ validDataForRegistration + ", errorMessages=" + errorMessages + '}';
	}
}
