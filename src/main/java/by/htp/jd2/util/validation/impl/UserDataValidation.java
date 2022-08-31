package by.htp.jd2.util.validation.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.controller.AttributsKey;
import by.htp.jd2.controller.UsersParameter;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.util.validation.ValidatorBuilder;

public class UserDataValidation {

	private final boolean authDataCorrect;
	private final boolean regDataCorrect;
	private final Map<String, String> dataForRegistration;	

	private UserDataValidation(Validator validator) {
		authDataCorrect = validator.authDataCorrect;
		regDataCorrect = validator.regDataCorrect;
		dataForRegistration = validator.dataForRegistration;
		
	}

	public boolean isAuthDataCorrect() {
		return this.authDataCorrect;
	}
	
	public boolean isRegDataCorrect() {
		return this.regDataCorrect;
	}

	public Map<String, String> getDataForRegistration() {
		return this.dataForRegistration;
	}


	public static class Validator implements ValidatorBuilder {

		private final static String NAME_AND_SURNAME_CHECK_PATTERN = "[a-zA-Z]{2,}";
		private final static String LOGIN_CHECK_PATTERN = "[A-Z a-z 0-9]{2,20}";
		private final static String PASSWORD_CHECK_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
		private final static String EMAIL_CHECK_PATTERN = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";
		private final static String DATA_IS_VALID = "valid";
		private final static String DATA_IS_NOT_VALID = "not_valid";
		private final static String DATA_IS_USED = "used";
		
		public boolean regDataCorrect;
		public boolean authDataCorrect;
		private Map<String, String> dataForRegistration = new HashMap<String, String>();
		

		public Validator authDataCorrect(String login, String password) throws ServiceException {
			authDataCorrect = validateAuthData(login, password);
			return this;
		}
		
		public Validator regDataCorrect(NewUserInfo user) throws ServiceException {
			regDataCorrect = validateRegData(user);
			return this;
		}

		public boolean validateAuthData(String login, String password) throws ServiceException {
			if (login.isEmpty() || password.isEmpty()) {
				return false;
			}
			return true;
		}

		public boolean validateRegData(NewUserInfo user) throws ServiceException {
			String name = user.getUsername();
			String surname = user.getUserSurname();
			String login = user.getLogin();
			String password = String.valueOf(user.getPassword());
			String email = user.getEmail();

			if (validateName(name) & validateSurname(surname) & validateLogin(login) & validatePassword(password)
					& validatePassword(password) & validateEmail(email)) {
				return true;
			}
			return false;
		}

		public boolean validateName(String name) throws ServiceException {
			if (name.isEmpty() || !Pattern.compile(NAME_AND_SURNAME_CHECK_PATTERN).matcher(name).matches()) {
				dataForRegistration.put(UsersParameter.NAME_PARAM,DATA_IS_NOT_VALID);
				return false;
			}
			dataForRegistration.put(UsersParameter.NAME_PARAM,DATA_IS_VALID);
			return true;
		}

		public boolean validateSurname(String surname) throws ServiceException {
			if (surname.isEmpty() || !Pattern.compile(NAME_AND_SURNAME_CHECK_PATTERN).matcher(surname).matches()) {
				dataForRegistration.put(UsersParameter.SURNAME_PARAM,DATA_IS_NOT_VALID);
				return false;
			}
			dataForRegistration.put(UsersParameter.SURNAME_PARAM,DATA_IS_VALID);
			return true;
		}

		public boolean validateLogin(String login) throws ServiceException {
			if (login.isEmpty() || !Pattern.compile(LOGIN_CHECK_PATTERN).matcher(login).matches()) {
				dataForRegistration.put(UsersParameter.LOGIN_PARAM,DATA_IS_NOT_VALID);
				return false;
			}
			dataForRegistration.put(UsersParameter.LOGIN_PARAM,DATA_IS_VALID);
			return true;
		}

		public boolean validatePassword(String password) throws ServiceException {
			if (password.isEmpty() || !Pattern.compile(PASSWORD_CHECK_PATTERN).matcher(password).matches()) {
				dataForRegistration.put(UsersParameter.PASSWORD_PARAM,DATA_IS_NOT_VALID);
				return false;
			}
			dataForRegistration.put(UsersParameter.PASSWORD_PARAM,DATA_IS_VALID);
			return true;
		}

		public boolean validateEmail(String email) throws ServiceException {
			if (email.isEmpty() || !Pattern.compile(EMAIL_CHECK_PATTERN).matcher(email).matches()) {
				dataForRegistration.put(UsersParameter.EMAIL_PARAM,DATA_IS_NOT_VALID);
				return false;				
			}
			dataForRegistration.put(UsersParameter.EMAIL_PARAM,DATA_IS_VALID);
			
			System.out.println("val dataForRegistration " + dataForRegistration);
			return true;
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
		return authDataCorrect == that.authDataCorrect
				&& Objects.equals(regDataCorrect, that.regDataCorrect)
				&& Objects.equals(dataForRegistration, that.dataForRegistration);
	}

	@Override
	public int hashCode() {
		return Objects.hash(authDataCorrect, regDataCorrect, dataForRegistration);
	}

	@Override
	public String toString() {
		return "UserDataValidation{" + "authDataCorrect=" + authDataCorrect + ", regDataCorrect="
				+ regDataCorrect + ", dataForRegistration=" + dataForRegistration + '}';
	}
}
