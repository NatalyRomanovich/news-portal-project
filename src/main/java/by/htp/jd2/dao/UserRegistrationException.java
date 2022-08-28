package by.htp.jd2.dao;

public class UserRegistrationException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserRegistrationException() {
		super();
	}

	public UserRegistrationException(String message) {
		super(message);
	}

	public UserRegistrationException(String message, Exception exception) {
		super(message, exception);
	}

	public UserRegistrationException(Exception exception) {
		super(exception);
	}
}
