package by.htp.jd2.util.validation.impl;

import java.util.regex.Pattern;
import by.htp.jd2.bean.NewUserInfo;
import by.htp.jd2.service.ServiceException;
import by.htp.jd2.util.validation.UserDataValidation;

public class UserDataValidationImpl implements UserDataValidation {

	private final String NAME_AND_SURNAME_CHECK = "[A-Z a-z]+";
	private final String LOGIN_AND_PASWORD_CHECK = "[A-Z a-z 0-9]+";
	private final String EMAIL_CHECK = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";

	@Override
	public boolean checkAuthData(String login, String password) {
		boolean result = false;

		if (password.isEmpty() || login.isEmpty()) {
			result = false;
		}
		if (Pattern.matches(LOGIN_AND_PASWORD_CHECK, password) && Pattern.matches(LOGIN_AND_PASWORD_CHECK, login)) {
			result = true;
		}

		return result;
	}

	@Override
	public boolean checkRegData(NewUserInfo user) throws ServiceException {

		if (!(nameIsCorrect(user) && surNameIsCorrect(user) && loginIsCorrect(user) && passwordIsCorrect(user)
				&& emailIsCorrect(user))) {
			return false;
		}
		return true;
	}

	public boolean nameIsNormal(NewUserInfo user) {

		return Pattern.matches(NAME_AND_SURNAME_CHECK, user.getUsername());
	}

	public boolean nameIsCorrect(NewUserInfo user) {

		if (user.getUsername().isEmpty() || !nameIsNormal(user)) {
			return false;
		}
		return true;
	}

	public boolean surNameIsNormal(NewUserInfo user) {

		return Pattern.matches(NAME_AND_SURNAME_CHECK, user.getUserSurname());
	}

	public boolean surNameIsCorrect(NewUserInfo user) {

		if (user.getUserSurname().isEmpty() || !surNameIsNormal(user)) {
			return false;
		}
		return true;
	}

	public boolean loginIsNormal(NewUserInfo user) {

		return Pattern.matches(LOGIN_AND_PASWORD_CHECK, user.getLogin());
	}

	public boolean loginIsCorrect(NewUserInfo user) {

		if (user.getLogin().isEmpty() || !loginIsNormal(user)) {
			return false;
		}
		return true;
	}

	public boolean passwordIsNormal(NewUserInfo user) {

		return (Pattern.matches(LOGIN_AND_PASWORD_CHECK, user.getPassword()));
	}

	public boolean passwordIsCorrect(NewUserInfo user) {

		if (user.getPassword().isEmpty() || !passwordIsNormal(user)) {
			return false;
		}
		return true;
	}

	public boolean emailIsNormal(NewUserInfo user) {

		return (Pattern.compile(EMAIL_CHECK).matcher(user.getEmail()).matches());
	}

	public boolean emailIsCorrect(NewUserInfo user) {

		if (user.getEmail().isEmpty() || !emailIsNormal(user)) {
			return false;
		}
		return true;
	}
}
