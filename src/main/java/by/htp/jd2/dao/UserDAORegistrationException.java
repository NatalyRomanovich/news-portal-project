package by.htp.jd2.dao;

public class UserDAORegistrationException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserDAORegistrationException() {
		super();
	}

	public UserDAORegistrationException(String message) {
		super(message);
	}

	public UserDAORegistrationException(String message, Exception exception) {
		super(message, exception);
	}

	public UserDAORegistrationException(Exception exception) {
		super(exception);
	}
}
