package by.htp.jd2.util.validation;

import by.htp.jd2.util.validation.impl.UserDataValidationImpl;

public class ValidationProvider {

	private static final ValidationProvider instance = new ValidationProvider();

	private ValidationProvider() {
	}

	private final UserDataValidation userDataValidation = new UserDataValidationImpl();

	public UserDataValidation getUserDataValidation() {
		return userDataValidation;
	}

	public static ValidationProvider getInstance() {
		return instance;
	}
}
